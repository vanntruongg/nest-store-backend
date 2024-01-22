package vantruong.nststore.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {
  private String email;
  private List<Item> items;
  private double totalPrice;
}
