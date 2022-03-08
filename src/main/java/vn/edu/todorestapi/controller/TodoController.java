package vn.edu.todorestapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.todorestapi.domain.Todo;
import vn.edu.todorestapi.payload.Response;
import vn.edu.todorestapi.service.TodoService;

@RestController
@RequestMapping(path = "api/v1/todos")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class TodoController {
  private TodoService todoService;

  @Autowired
  public TodoController(TodoService todoService) {
    this.todoService = todoService;
  }

  @GetMapping
  @ResponseBody
  public List<Todo> getTodos(@RequestParam(name = "limit", required = false) Integer limit) {
    return todoService.getTodos(limit);
  }

  @GetMapping(path = "{id}")
  public @ResponseBody Todo getTodo(@PathVariable("id") Long id) {
    return todoService.getTodo(id);
  }

  @PostMapping
  public @ResponseBody Response registerJob(@Valid @RequestBody Todo todo, BindingResult bindingResult) {
    if (!bindingResult.hasErrors()) {
      Todo newTodo = todoService.addNewTodo(todo);
      return new Response(200, true, "add new job successfully", newTodo);
    } else
      return new Response(400, false, "invalid input", null);
  }

  @PutMapping(path = "{id}")
  public @ResponseBody Response updateJob(@PathVariable("id") Long id, @Valid @RequestBody Todo todo,
      BindingResult bindingResult) {
    if (!bindingResult.hasErrors()) {
      Todo newTodo = todoService.updateTodo(id, todo);
      return new Response(200, true, "update job successfully", newTodo);
    } else
      return new Response(400, false, "invalid input", null);
  }

  @DeleteMapping(path = "{id}")
  public @ResponseBody Response deleteJob(@PathVariable(name = "id") Long id) {
    System.out.println("id" + id);
    todoService.deleteTodo(id);
    return new Response(200, true, "delete job successfully", null);
  }
}
