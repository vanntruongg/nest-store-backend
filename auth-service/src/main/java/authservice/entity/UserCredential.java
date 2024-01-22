package authservice.entity;

import authservice.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "UserCredential")
public class UserCredential {
  @Id
  private int id;
  private String email;
  private String password;
  private List<Role> roles;
  private boolean isVerify;
  private AccountStatus status;

  @CreatedDate
  @JsonIgnore
  private LocalDateTime createdDate = LocalDateTime.now();

  @LastModifiedDate
  @JsonIgnore
  private LocalDateTime modifiedDate = LocalDateTime.now();
}
