package authservice.exception;

public class DuplicationException extends RuntimeException {
  private final transient ErrorDetail errorDetail;
  public DuplicationException(int errorCode, String message) {
    super(message);
    this.errorDetail = ErrorDetail.builder()
            .errorCode(errorCode)
            .errorMessage(message)
            .build();
  }

  public DuplicationException(int errorCode, String message, Throwable cause) {
    super(message, cause);
    this.errorDetail = ErrorDetail.builder()
            .errorCode(errorCode)
            .errorMessage(message)
            .build();
  }
}
