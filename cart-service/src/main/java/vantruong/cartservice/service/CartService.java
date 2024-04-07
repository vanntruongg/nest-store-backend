package vantruong.cartservice.service;

import vantruong.cartservice.dto.CartItemDto;
import vantruong.cartservice.dto.UpdateQuantityRequest;
import vantruong.cartservice.entity.CartItem;

import java.util.List;

public interface CartService {
  CartItem findById(String email);
  CartItem findByIdOrCreate(String email);

  Boolean addToCart(CartItemDto cartItemDto);

  Boolean removeFromCart(String emailUser, int productId);

  Boolean updateQuantity(UpdateQuantityRequest request);

  Boolean removeItemsFromCart(String email, List<Integer> itemIds);
}
