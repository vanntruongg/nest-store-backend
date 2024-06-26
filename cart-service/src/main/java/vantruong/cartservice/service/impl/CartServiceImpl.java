package vantruong.cartservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vantruong.cartservice.client.RestClient;
import vantruong.cartservice.constant.MessageConstant;
import vantruong.cartservice.dto.CartItemDto;
import vantruong.cartservice.dto.ItemDto;
import vantruong.cartservice.dto.UpdateQuantityRequest;
import vantruong.cartservice.entity.CartItem;
import vantruong.cartservice.entity.Item;
import vantruong.cartservice.exception.ErrorCode;
import vantruong.cartservice.exception.InsufficientProductQuantityException;
import vantruong.cartservice.exception.NotFoundException;
import vantruong.cartservice.repository.CartRepository;
import vantruong.cartservice.service.CartService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

  private final CartRepository cartRepository;
  private final RestClient restClient;

  @Override
  public CartItem findById(String email) {
    return cartRepository.findById(email).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.CART_NOT_FOUND));
  }

  @Override
  public CartItem findByIdOrCreate(String email) {
    return cartRepository.findById(email).orElseGet(() -> new CartItem(email, new ArrayList<>(), 0.0));
  }

  @Override
  @Transactional
  public Boolean addToCart(CartItemDto cartItemDto) {
    // find the cart by email
    CartItem cartItem = findByIdOrCreate(cartItemDto.getEmail());

    // check and initialize the items list if it doesn't exist
    List<Item> items = cartItem.getItems();
    if (items == null) {
      items = new ArrayList<>();
      cartItem.setItems(items);
    }

    Item existedItem = isItemAlreadyInCart(items, cartItemDto.getItemDto().getId());
    if (existedItem != null) {
      // if item already, update quantity
      int newQuantity = existedItem.getQuantity() + cartItemDto.getItemDto().getQuantity();
      existedItem.setQuantity(newQuantity);
    } else {
      // if item doesn't exist, add it to cart
      Item item = toItem(cartItemDto.getItemDto());
      items.add(item);
    }

    // calculate and update the total price of the cart
    cartItem.setTotalPrice(calculatorTotalPrice(cartItem.getItems()));
    cartRepository.save(cartItem);
    return true;
  }

  // check if an item already exists in the cart
  private Item isItemAlreadyInCart(List<Item> items, int idNewItem) {
    return items.stream()
            .filter(item -> item.getId() == idNewItem)
            .findFirst()
            .orElse(null);
  }

  // calculate the total price of the cart
  private double calculatorTotalPrice(List<Item> items) {
    return items.stream()
            .mapToDouble(item -> item.getPrice() * item.getQuantity())
            .sum();
  }

  // map dto to entity
  private Item toItem(ItemDto itemDto) {
    return Item.builder()
            .id(itemDto.getId())
            .quantity(itemDto.getQuantity())
            .price(itemDto.getPrice())
            .name(itemDto.getName())
            .category(itemDto.getCategory())
            .imageUrl(itemDto.getImageUrl())
            .build();
  }

  @Override
  @Transactional
  public Boolean removeFromCart(String emailUser, int itemId) {
    CartItem cartItem = findById(emailUser);

    if (cartItem != null) {
      List<Item> items = cartItem.getItems();
      items.removeIf(item -> item.getId() == itemId);

      double totalPrice = calculatorTotalPrice(cartItem.getItems());
      cartItem.setTotalPrice(totalPrice);
      cartRepository.save(cartItem);
      return true;
    }
    return false;
  }

  @Override
  public Boolean updateQuantity(UpdateQuantityRequest request) {
    CartItem cartItem = findById(request.getEmail());

    Optional<Item> foundItem = cartItem.getItems().stream()
            .filter(item -> item.getId() == request.getItemId()).findFirst();

    if (foundItem.isPresent()) {
      Object stock = restClient.getStockProductById(foundItem.get().getId());
      if ((int) stock < request.getQuantity()) {
        throw new InsufficientProductQuantityException(ErrorCode.UNPROCESSABLE_ENTITY, MessageConstant.INSUFFICIENT_PRODUCT_QUANTITY);
      } else {
        foundItem.get().setQuantity(request.getQuantity());
        cartItem.setTotalPrice(calculatorTotalPrice(cartItem.getItems()));
        cartRepository.save(cartItem);
      }
      return true;
    }
    return false;
  }

  @Override
  @Transactional
  public Boolean removeItemsFromCart(String email, List<Integer> itemIds) {
    CartItem cartItem = findById(email);
    Boolean itemsRemoved = cartItem.getItems().removeIf(item -> itemIds.contains(item.getId()));

    cartRepository.save(cartItem);
    return itemsRemoved;
  }
}
