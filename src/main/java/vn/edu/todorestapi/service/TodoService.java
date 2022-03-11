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

  public long getCountTodo(String query) {
    return Optional.ofNullable(query)
        .map(value -> todoRepository.countByTitleLikeOrContentLike("%" + query.trim() + "%", "%" + query.trim() + "%"))
        .orElseGet(
            () -> todoRepository.count());
  }

  public List<Todo> getTodos(Integer page, Integer pageSize, String query) {
    return Optional.ofNullable(query)
        .map(value -> todoRepository.findAllByTitleLikeOrContentLike("%" + value.trim() + "%", "%" + value.trim() + "%",
            PageRequest.of(page, pageSize)))
        .orElseGet(() -> todoRepository.findAll(PageRequest.of(page, pageSize)).getContent());
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
  public Todo updateTodo(Todo todo) {
    if (!todoRepository.existsById(todo.getId()))
      throw new IllegalStateException("User width id " + todo.getId() + " does not exist");
    return todoRepository.save(todo);
  }

  public void deleteTodo(Long id) {
    if (!todoRepository.existsById(id))
      throw new IllegalStateException("job with id " + id + " does not exist");
    todoRepository.deleteById(id);
  }
}
