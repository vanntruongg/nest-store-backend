package vantruong.cartservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CartItemDto {
  @NotEmpty
  private String email;
  @NotNull
  private ItemDto itemDto;
}
