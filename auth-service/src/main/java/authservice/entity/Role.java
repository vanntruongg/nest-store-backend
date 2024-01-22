package authservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Role")
public class Role {
  @Id
  private int id;
  private String roleName;

  public Role(String name) {
    this.roleName = name;
  }
}
