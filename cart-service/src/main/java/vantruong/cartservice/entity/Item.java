package vantruong.cartservice.entity;

import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {
  private int id;
  @Min(value = 0, message = "Quantity must be non-negative")
  private int quantity;
  @Min(value = 0, message = "Item price must be non-negative")
  private double price;
  private String name;
  private String category;
  private String imageUrl;

}
