package vn.edu.todorestapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.edu.todorestapi.domain.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

}
