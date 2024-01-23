package authservice.common;

import authservice.entity.Token;
import authservice.enums.TokenType;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

public class Utils {

  public static Token generateTokenVerify() {
    String randomToken = RandomStringUtils.random(32);
    LocalDateTime expiredDate = LocalDateTime.now().plusDays(1);
    return Token.builder()
            .tokenType(TokenType.VERIFICATION.getTokenType())
            .tokenValue(randomToken)
            .expiredDate(expiredDate)
            .build();
  }
}
