package vn.edu.todorestapi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Entity
@Table(name = "todos")
@Data
public class Todo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false)
  private Long id;

  @Column(name = "title", nullable = false)
  @NotBlank()
  private String title;

  @Column(name = "content")
  @NotBlank()
  private String content;
}
