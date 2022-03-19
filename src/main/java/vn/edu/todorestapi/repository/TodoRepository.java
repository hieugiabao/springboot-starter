package vn.edu.todorestapi.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.edu.todorestapi.domain.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>, JpaSpecificationExecutor<Todo> {
  List<Todo> findAllByTitleLikeOrContentLike(String title, String content, Pageable pageable);

  long countByTitleLikeOrContentLike(String title, String content);
}
