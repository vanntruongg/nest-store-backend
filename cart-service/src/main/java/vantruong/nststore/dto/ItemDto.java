package vantruong.nststore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class ItemDto {
  @NotEmpty
  private int itemId;
  @Min(value = 0, message = "Quantity must be non-negative")
  private int quantity;
  @Min(value = 0, message = "Item price must be non-negative")
  private double itemPrice;
  @NotEmpty
  private String itemName;
  @NotEmpty
  private String itemCategory;
  @NotEmpty
  private String itemImageUrl;
}
