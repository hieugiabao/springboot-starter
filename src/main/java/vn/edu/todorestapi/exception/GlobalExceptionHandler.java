package vn.edu.todorestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import vn.edu.todorestapi.payload.Response;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler({ IllegalStateException.class })
  public ResponseEntity<Response> handleNotFoundTodo(Exception e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(400, false, "invalid request", null));
  }
}
