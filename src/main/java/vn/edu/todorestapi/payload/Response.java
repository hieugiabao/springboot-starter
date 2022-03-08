package vn.edu.todorestapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.todorestapi.domain.Todo;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
  private int code;
  private boolean success;
  private String message;
  private Todo todo;
}
