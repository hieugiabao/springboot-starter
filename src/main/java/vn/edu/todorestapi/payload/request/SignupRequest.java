package vn.edu.todorestapi.payload.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupRequest {
  @NotBlank
  @Size(min = 6, max = 50)
  private String username;

  @NotBlank
  @Size(max = 120)
  private String password;

  @Email
  @NotBlank
  private String email;

  private Set<String> roles;
}
