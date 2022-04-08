package vn.edu.todorestapi.payload.findParam;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TodoFindParam {
  private String title;
  private String content;
}
