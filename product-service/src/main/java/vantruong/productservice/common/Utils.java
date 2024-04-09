package vantruong.productservice.common;

import java.util.List;

public class Utils {
  public static String formatErrorMessage(List<String> errorMessages) {
    StringBuilder errorMessage = new StringBuilder();
    for (String message : errorMessages) {
      errorMessage.append(message).append("\n");
    }
    return errorMessage.toString();
  }
}
