package vn.edu.todorestapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.todorestapi.domain.Todo;
import vn.edu.todorestapi.repo.TodoRepository;

@Service
public class TodoService {
  @Autowired
  private TodoRepository todoRepository;

  public List<Todo> getTodos(Integer limit) {
    return Optional.ofNullable(limit).map(value -> todoRepository.findAll(PageRequest.of(value / 5, 5)).getContent())
        .orElseGet(() -> todoRepository.findAll());
  }

  public Todo getTodo(Long id) {
    Todo todo = todoRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("job with id " + id + " does not exist"));
    return todo;
  }

  public Todo addNewTodo(Todo todo) {
    return todoRepository.save(todo);
  }

  @Transactional
  public Todo updateTodo(Long id, Todo todo) {
    Todo existingTodo = todoRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("job with id " + id + " does not exist"));
    existingTodo.setTitle(todo.getTitle());
    existingTodo.setContent(todo.getContent());
    return todoRepository.save(existingTodo);
  }

  public void deleteTodo(Long id) {
    if (!todoRepository.existsById(id))
      throw new IllegalStateException("job with id " + id + " does not exist");
    todoRepository.deleteById(id);
  }
}
