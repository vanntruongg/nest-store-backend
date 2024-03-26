package authservice.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CommonResponse<T> {
  private boolean isSuccess;
  private String message;
  private String field;
  private T data;
  private String errorDetails;
}
