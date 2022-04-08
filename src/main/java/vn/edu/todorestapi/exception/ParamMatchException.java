package vn.edu.todorestapi.exception;

public class ParamMatchException extends Exception {
  private static final long serialVersionUID = 1L;
  private String field;

  public ParamMatchException() {
    super();
  }

  public ParamMatchException(String field, String message) {
    super(message);
    this.field = field;
  }

  public ParamMatchException(String message, Throwable cause) {
    super(message, cause);
  }

  public ParamMatchException(Throwable cause) {
    super(cause);
  }

  protected ParamMatchException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }
}
