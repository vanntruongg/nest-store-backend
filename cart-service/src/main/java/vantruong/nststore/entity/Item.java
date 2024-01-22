package vantruong.nststore.entity;

import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {
  private int itemId;
  @Min(value = 0, message = "Quantity must be non-negative")
  private int quantity;
  @Min(value = 0, message = "Item price must be non-negative")
  private double itemPrice;
  private String itemName;
  private String itemCategory;
  private String itemImageUrl;


}
