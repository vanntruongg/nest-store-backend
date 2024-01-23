package authservice.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
  private String tokenType;
  private String tokenValue;
  private LocalDateTime expiredDate;
}
