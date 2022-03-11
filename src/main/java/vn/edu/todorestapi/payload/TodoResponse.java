package vn.edu.todorestapi.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import vn.edu.todorestapi.domain.Todo;

@Data
@AllArgsConstructor
public class TodoResponse {
  private int totalCount;
  private int currentPage;
  private List<Todo> todos;
}
