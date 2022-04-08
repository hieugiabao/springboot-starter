package vn.edu.todorestapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.todorestapi.exception.ParamMatchException;
import vn.edu.todorestapi.payload.request.LoginRequest;
import vn.edu.todorestapi.payload.request.SignupRequest;
import vn.edu.todorestapi.payload.response.JwtResponse;
import vn.edu.todorestapi.payload.response.MessageResponse;
import vn.edu.todorestapi.security.jwt.JwtUtils;
import vn.edu.todorestapi.security.service.UserDetailsImpl;
import vn.edu.todorestapi.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
  private static final Logger log = LoggerFactory.getLogger(AuthController.class);

  @Autowired
  private AuthService authService;

  @Autowired
  private JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    UserDetailsImpl userDetails = this.authService.signin(loginRequest);

    String jwt = jwtUtils.generateJwtToken(userDetails.getUsername());
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());
    return ResponseEntity.ok(new JwtResponse(jwt,
        userDetails.getId(),
        userDetails.getUsername(),
        userDetails.getEmail(),
        roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
    try {
      this.authService.signup(signupRequest);
    } catch (ParamMatchException e) {
      if (e.getField().equals("username")) {
        return ResponseEntity.badRequest().body(new MessageResponse("Username is already taken!"));
      }
      if (e.getField().equals("email"))
        return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use!"));
    } catch (Exception e) {
      log.error("{}", e.getMessage());
      return ResponseEntity.badRequest().body(new MessageResponse("Error in creating user!"));
    }
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}
