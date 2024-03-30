package vantruong.cartservice.service;

import vantruong.cartservice.dto.CartItemDto;
import vantruong.cartservice.dto.RequestOrder;
import vantruong.cartservice.dto.UpdateQuantityRequest;
import vantruong.cartservice.entity.CartItem;

import java.util.List;

public interface CartService {
  CartItem findById(String email);
  Boolean addToCart(CartItemDto cartItemDto);

  Boolean removeFromCart(String emailUser, int productId);

  Boolean updateQuantity(UpdateQuantityRequest request);

  Boolean requestOrder(RequestOrder requestOrder);
}
