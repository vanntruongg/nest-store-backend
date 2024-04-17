package authservice.exception.handler;

import authservice.exception.*;
import authservice.common.CommonResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;

import java.io.IOException;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

  @ExceptionHandler(value = {NotFoundException.class, IOException.class, MultipartException.class})
  public ResponseEntity<CommonResponse<Object>> handleApplicationException(Exception exception, WebRequest request) {
    return ResponseEntity.internalServerError().body(CommonResponse.builder()
            .isSuccess(false)
            .message(exception.getMessage())
            .errorDetails(exception.getCause() != null ? exception.getCause().getMessage() : StringUtils.EMPTY)
            .build());
  }

  @ExceptionHandler(value = {DuplicationException.class})
  public ResponseEntity<CommonResponse<Object>> duplicateException(Exception ex, WebRequest request) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(CommonResponse.builder()
            .isSuccess(false)
            .message(ex.getMessage())
            .errorDetails(ex.getCause() != null ? ex.getCause().getMessage() : StringUtils.EMPTY)
            .build());
  }

  @ExceptionHandler(value = {UnVerifiedAccountException.class})
  public ResponseEntity<CommonResponse<Object>> handleUnverifiedAccount(Exception ex, WebRequest request) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(CommonResponse.builder()
            .isSuccess(false)
            .message(ex.getMessage())
            .errorDetails(ex.getCause() != null ? ex.getCause().getMessage() : StringUtils.EMPTY)
            .build());
  }

  @ExceptionHandler(value = {AccountUnAvailableException.class})
  public ResponseEntity<CommonResponse<Object>> handleAccountUnAvailable(Exception ex, WebRequest request) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(CommonResponse.builder()
            .isSuccess(false)
            .message(ex.getMessage())
            .errorDetails(ex.getCause() != null ? ex.getCause().getMessage() : StringUtils.EMPTY)
            .build());
  }


  @ExceptionHandler(value = {OldPasswordNotMatches.class})
  public ResponseEntity<CommonResponse<Object>> handleOldPasswordNotMatches(Exception ex, WebRequest request) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonResponse.builder()
            .isSuccess(false)
            .message(ex.getMessage())
            .errorDetails(ex.getCause() != null ? ex.getCause().getMessage() : StringUtils.EMPTY)
            .build());
  }

  @ExceptionHandler(value = {FormException.class})
  public ResponseEntity<CommonResponse<Object>> handleFormException(Exception ex, WebRequest request) {
    return ResponseEntity.status(ErrorCode.FORM_ERROR).body(CommonResponse.builder()
            .isSuccess(false)
            .message(ex.getMessage())
            .errorDetails(ex.getCause() != null ? ex.getCause().getMessage() : StringUtils.EMPTY)
            .build());
  }
}
