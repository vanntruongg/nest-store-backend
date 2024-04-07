package vantruong.cartservice.exception;

public class InsufficientProductQuantityException extends RuntimeException {
  private final transient ErrorDetail errorDetail;

  public InsufficientProductQuantityException(int errorCode, String message) {
    super(message);
    this.errorDetail = ErrorDetail.builder()
            .errorCode(errorCode)
            .message(message)
            .build();
  }

  public InsufficientProductQuantityException(int errorCode, String message, Throwable cause) {
    super(message, cause);
    this.errorDetail = ErrorDetail.builder()
            .errorCode(errorCode)
            .message(message)
            .build();
  }
}
