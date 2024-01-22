package nststore.userservice.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
  @Id
  @Column(name = "email", nullable = false, unique = true, updatable = false, length = 100)
  private String email;

  @Column(name = "first_name", nullable = false, length = 20)
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 20)
  private String lastName;

  @Column(name = "phone", length = 11)
  private String phone;

  private String address;

  @Column(name = "image_url")
  private String imageUrl;
}
