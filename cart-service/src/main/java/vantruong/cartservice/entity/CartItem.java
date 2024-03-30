package vantruong.cartservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash("Cart")
public class CartItem {
  @Id
  private String email;
  private List<Item> items;
  private double totalPrice;
}
