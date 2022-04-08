package vn.edu.todorestapi.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.todorestapi.domain.Todo;
import vn.edu.todorestapi.payload.findParam.TodoFindParam;
import vn.edu.todorestapi.repository.TodoRepository;

@Service
public class TodoService {
  @Autowired
  private TodoRepository todoRepository;

  public Page<Todo> getTodosPagging(Pageable pageable, TodoFindParam todoFindParam) {
    return todoRepository.findAll(new Specification<Todo>() {

      @Override
      public Predicate toPredicate(Root<Todo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        // TODO Auto-generated method stub
        List<Predicate> predicates = new ArrayList<>();
        if (todoFindParam != null) {
          if (todoFindParam.getContent() != null)
            predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("content")),
                "%" + todoFindParam.getContent().trim().toUpperCase() + "%")));
          if (todoFindParam.getTitle() != null)
            predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("title")),
                "%" + todoFindParam.getTitle().trim().toUpperCase() + "%")));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
      }

    }, pageable);
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
