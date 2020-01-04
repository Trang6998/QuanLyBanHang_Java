package nhom18.com.restful.service;

public class ServiceResult {
	
  private Status status = Status.SUCCESS;
  private String message;
  private Object data;
  public enum Status {
    SUCCESS, FAILED;
  }
  // getter - setter
public Status getStatus() {
	return status;
}
public void setStatus(Status status) {
	this.status = status;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public Object getData() {
	return data;
}
public void setData(Object data) {
	this.data = data;
}
  
}