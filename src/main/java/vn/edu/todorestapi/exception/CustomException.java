package vn.edu.todorestapi.exception;

public class CustomException extends Exception {

  private String type;
  private String errCode;
  private String errDesc;

  public CustomException(String errCode) {
    super();
    this.errCode = errCode;
  }

  public CustomException() {
    super();
  }

  public CustomException(String errCode, String errDesc) {
    super();
    this.errCode = errCode;
    this.errDesc = errDesc;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getErrCode() {
    return errCode;
  }

  public void setErrCode(String errCode) {
    this.errCode = errCode;
  }

  public String getErrDesc() {
    return errDesc;
  }

  public void setErrDesc(String errDesc) {
    this.errDesc = errDesc;
  }

}
