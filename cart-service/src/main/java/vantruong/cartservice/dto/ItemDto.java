package vantruong.cartservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class ItemDto {
  @NotEmpty
  private int id;
  @Min(value = 0, message = "Quantity must be non-negative")
  private int quantity;
  @Min(value = 0, message = "Item price must be non-negative")
  private double price;
  @NotEmpty
  private String name;
  @NotEmpty
  private String category;
  @NotEmpty
  private String imageUrl;
}
