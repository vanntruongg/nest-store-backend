package vantruong.nststore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vantruong.nststore.dto.CartItemDto;
import vantruong.nststore.dto.UpdateQuantityRequest;
import vantruong.nststore.entity.CartItem;
import vantruong.nststore.entity.Item;
import vantruong.nststore.service.CartService;
import vantruong.nststore.service.RedisService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
  public static final String CART_KEY_PREFIX = "cart:";
  private final RedisService redisService;

  @Override
  @Transactional
  public Boolean addToCart(CartItemDto cartItemDto) {
    String cartKey = getKey(cartItemDto.getEmail());
    CartItem cartItem = redisService.get(cartKey);
    if (cartItem == null) {
      cartItem = new CartItem();
      cartItem.setEmail(cartItemDto.getEmail());
      cartItem.setItems(new ArrayList<>());
    }

    Item item = Item.builder()
            .itemId(cartItemDto.getItemDto().getItemId())
            .quantity(cartItemDto.getItemDto().getQuantity())
            .itemPrice(cartItemDto.getItemDto().getItemPrice())
            .itemName(cartItemDto.getItemDto().getItemName())
            .itemCategory(cartItemDto.getItemDto().getItemCategory())
            .itemImageUrl(cartItemDto.getItemDto().getItemImageUrl())
            .build();

    cartItem.getItems().add(item);
    cartItem.setTotalPrice(cartItem.getTotalPrice() + (item.getItemPrice() * item.getQuantity()));

    redisService.set(cartKey, cartItem);
    return true;
  }

  @Override
  @Transactional
  public Boolean removeFromCart(String emailUser, int itemId) {
    String cartKey = getKey(emailUser);
    CartItem cartItem = redisService.get(cartKey);

    if (cartItem != null) {
      List<Item> items = cartItem.getItems();
      items.removeIf(item -> item.getItemId() == itemId);

      double totalPrice = items.stream()
              .mapToDouble(item -> item.getQuantity() * item.getItemPrice())
              .sum();

      cartItem.setTotalPrice(totalPrice);
      redisService.set(cartKey, cartItem);
      return true;
    }
    return false;
  }

  @Override
  public CartItem getItems(String emailUser) {
    String cartKey = getKey(emailUser);
    return redisService.get(cartKey);
  }

  @Override
  public Boolean updateQuantity(UpdateQuantityRequest request) {
    CartItem cartItem = getItems(request.getEmail());
    for (Item item : cartItem.getItems()) {
      if (item.getItemId() == request.getItemId()) {
        item.setQuantity(request.getQuantity());
        return true;
      }
    }
    return false;
  }

  private String getKey(String emailUser) {
    return CART_KEY_PREFIX + emailUser;
  }
}
