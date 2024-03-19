package authservice.exception;

import org.springframework.security.core.AuthenticationException;

public class PasswordIncorrectException extends AuthenticationException {
  public PasswordIncorrectException(String msg) {
    super(msg);
  }

  public PasswordIncorrectException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
