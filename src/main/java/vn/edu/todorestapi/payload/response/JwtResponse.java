package vn.edu.todorestapi.payload.response;

import java.util.Collection;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

  private String jwt;
  private String type;
  private Long id;
  private String username;
  private String email;
  private Collection<String> roles;

  public JwtResponse(String jwt, Long id, String username, String email, List<String> roles) {
    this.jwt = jwt;
    this.id = id;
    this.username = username;
    this.email = email;
    this.roles = roles;
  }
}
