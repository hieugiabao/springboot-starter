package vn.edu.todorestapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.todorestapi.domain.Todo;
import vn.edu.todorestapi.repository.TodoRepository;

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

  public List<Todo> getTodos(int page, int pageSize, String query) {
    Page<Todo> _page = todoRepository.findAll(
        new Specification<Todo>() {
          @Override
          public Predicate toPredicate(Root<Todo> root, CriteriaQuery<?> criteriaQuery,
              CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<Predicate>();
            if (query != null) {
              predicates.add(
                  criteriaBuilder.or(
                      criteriaBuilder.like(root.get("title"), "%" + query.trim() + "%"),
                      criteriaBuilder.like(root.get("content"), "%" + query.trim() + "%")));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
          }
        },
        PageRequest.of(page, pageSize));
    return _page.getContent();
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
