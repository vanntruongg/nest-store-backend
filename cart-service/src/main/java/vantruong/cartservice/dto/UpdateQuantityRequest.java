package vantruong.cartservice.dto;

import lombok.Getter;

@Getter
public class UpdateQuantityRequest {
  private String email;
  private int itemId;
  private int quantity;
}
