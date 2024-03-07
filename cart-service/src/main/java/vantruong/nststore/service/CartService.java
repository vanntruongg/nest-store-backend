package vantruong.nststore.service;

import vantruong.nststore.dto.CartItemDto;
import vantruong.nststore.dto.UpdateQuantityRequest;

public interface CartService {
  Boolean addToCart(CartItemDto cartItemDto);

  Boolean removeFromCart(String emailUser, int productId);

  Object getItems(String emailUser);

  Boolean updateQuantity(UpdateQuantityRequest request);
}
