package authservice.entity;

import authservice.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "User")
public class User {
  @Id
  private ObjectId id;
  @Indexed(unique = true)
  private String email;
  private String firstName;
  private String lastName;
  private String phone;
  private String address;
  private String password;
  private boolean isVerify;
  private AccountStatus status;
  private List<String> roles;
  private List<Token> tokens;

  @Builder.Default
  @CreatedDate
  @JsonIgnore
  private LocalDateTime createdDate = LocalDateTime.now();

  @Builder.Default
  @LastModifiedDate
  @JsonIgnore
  private LocalDateTime modifiedDate = LocalDateTime.now();

}
