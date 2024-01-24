package authservice.exception;

public class UnVerifiedAccountException extends RuntimeException {
  private final transient ErrorDetail errorDetail;

  public UnVerifiedAccountException(int errorCode, String message) {
    super(message);
    this.errorDetail = ErrorDetail.builder()
            .errorCode(errorCode)
            .errorMessage(message)
            .build();
  }

  public UnVerifiedAccountException(int errorCode, String message, Throwable cause) {
    super(message, cause);
    this.errorDetail = ErrorDetail.builder()
            .errorCode(errorCode)
            .errorMessage(message)
            .build();
  }
}
