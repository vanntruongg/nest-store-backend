package authservice.common;

import authservice.entity.Token;
import authservice.enums.TokenType;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

public class Utils {

  private static String generateRandomString() {
    int length = 24;
    boolean useLetters = true;
    boolean useNumbers = true;
    return RandomStringUtils.random(length, useLetters, useNumbers);
  }

  public static Token generateTokenVerify() {
    String randomToken = generateRandomString();
    LocalDateTime expiredDate = LocalDateTime.now().plusDays(1);
    return Token.builder()
            .tokenType(TokenType.VERIFICATION.getTokenType())
            .tokenValue(randomToken)
            .expiredDate(expiredDate)
            .build();
  }
}
