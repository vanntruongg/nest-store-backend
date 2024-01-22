package nststore.userservice.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
  private final transient ErrorDetail errorDetail;

  public NotFoundException(int errorCode, String message) {
    super(message);
    this.errorDetail = ErrorDetail.builder()
            .errorCode(errorCode)
            .message(message)
            .build();
  }

  public NotFoundException(int errorCode, String message, Throwable cause) {
    super(message, cause);
    this.errorDetail = ErrorDetail.builder()
            .errorCode(errorCode)
            .message(message)
            .build();
  }
}
