package vn.edu.todorestapi.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.edu.todorestapi.domain.ERole;
import vn.edu.todorestapi.domain.Role;
import vn.edu.todorestapi.domain.User;
import vn.edu.todorestapi.exception.ParamMatchException;
import vn.edu.todorestapi.payload.request.LoginRequest;
import vn.edu.todorestapi.payload.request.SignupRequest;
import vn.edu.todorestapi.repository.RoleRepository;
import vn.edu.todorestapi.repository.UserRepository;
import vn.edu.todorestapi.security.jwt.JwtUtils;
import vn.edu.todorestapi.security.service.UserDetailsImpl;

@Service
public class AuthService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtils jwtUtils;

  public UserDetailsImpl signin(LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    return userDetails;
  }

  public User signup(SignupRequest signupRequest) throws ParamMatchException {
    if (userRepository.existsByUsername(signupRequest.getUsername())) {
      throw new ParamMatchException("username", "Username " + signupRequest.getUsername() + " is already taken!");
    }
    if (userRepository.existsByEmail(signupRequest.getEmail())) {
      throw new ParamMatchException("email", "Email address " + signupRequest.getEmail() + " is already in use!");
    }
    User user = new User(signupRequest.getUsername(),
        signupRequest.getEmail(),
        passwordEncoder.encode(signupRequest.getPassword()));
    Set<String> strRoles = signupRequest.getRoles();
    Set<Role> roles = new HashSet<>();
    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "admin":
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
            break;
          case "mod":
            Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(modRole);
            break;
          default:
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    return userRepository.save(user);
  }

  public User getUserByHeader() {
    String username = jwtUtils.getUsernameByHeader();
    if (username != null) {
      return userRepository.findByUsername(username)
          .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }
    return null;
  }
}
