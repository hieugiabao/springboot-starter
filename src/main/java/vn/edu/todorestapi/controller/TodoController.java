package vn.edu.todorestapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.todorestapi.domain.Todo;
import vn.edu.todorestapi.domain.User;
import vn.edu.todorestapi.payload.findParam.TodoFindParam;
import vn.edu.todorestapi.payload.response.MessageResponse;
import vn.edu.todorestapi.service.AuthService;
import vn.edu.todorestapi.service.TodoService;

@RestController
@RequestMapping(path = "api/todos")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class TodoController {
  @Autowired
  private TodoService todoService;

  @Autowired
  private AuthService authService;

  @GetMapping("/search")
  public Page<Todo> getTodos(Pageable pageable, TodoFindParam todoFindParam) {
    return todoService.getTodosPagging(pageable, todoFindParam);
  }

  @GetMapping(path = "/get/{id}")
  public @ResponseBody Todo getTodo(@PathVariable("id") Long id) {
    return todoService.getTodo(id);
  }

  @PostMapping("/create")
  public @ResponseBody Todo registerJob(@Valid @RequestBody Todo todo) {
    User user = authService.getUserByHeader();
    todo.setUser(user);
    return todoService.addNewTodo(todo);
  }

  @PostMapping("/update")
  public @ResponseBody Todo updateJob(@Valid @RequestBody Todo todo) {
    User user = authService.getUserByHeader();
    todo.setUser(user);
    return todoService.updateTodo(todo);
  }

  @PostMapping(path = "/delete/{id}")
  public ResponseEntity<?> deleteJob(@PathVariable(name = "id") Long id) {
    User user = authService.getUserByHeader();
    Todo todo = todoService.getTodo(id);
    if (user.getId() != todo.getUser().getId()) {
      return ResponseEntity.badRequest().body(new MessageResponse("permissions action"));
    }
    todoService.deleteTodo(id);
    return ResponseEntity.ok().build();
  }
}
