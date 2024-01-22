package authservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountStatus {
  PENDING_VERIFICATION("Pending verification"),
  ACTIVE("Active"),
  LOCKED("Locked"),
  DELETED("Deleted");

  private final String status;

}