package vn.edu.todorestapi.payload;

import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import lombok.Data;

@Data
public class TodoRequest {
  private String query;

  @NotNull
  private Integer page;

  @Nullable
  private Integer pageSize;
}
