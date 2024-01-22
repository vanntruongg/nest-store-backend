package vantruong.nststore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vantruong.nststore.common.CommonResponse;
import vantruong.nststore.dto.CartItemDto;
import vantruong.nststore.service.CartService;

import static vantruong.nststore.constant.ApiEndpoint.*;

@RestController
@RequestMapping(CART)
@RequiredArgsConstructor
public class CartController {
  private final CartService cartService;

  @GetMapping(CART_GET_ALL)
  public ResponseEntity<CommonResponse<Object>> getAll(@PathVariable("email") String email) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message("Find successfully!")
            .data(cartService.getItems(email))
            .build());
  }

  @PostMapping(ADD_TO_CART)
  public ResponseEntity<CommonResponse<Object>> addToCart(@RequestBody @Valid CartItemDto cartItemDto) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message("Find successfully!")
            .data(cartService.addToCart(cartItemDto))
            .build());
  }

  @PostMapping(REMOVE_FROM_CART)
  public ResponseEntity<CommonResponse<Object>> removeFromCart(@PathVariable("email") String email, @PathVariable("id") int productId) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message("Find successfully!")
            .data(cartService.removeFromCart(email, productId))
            .build());
  }
}
