package vantruong.nststore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vantruong.nststore.common.CommonResponse;
import vantruong.nststore.constant.MessageConstant;
import vantruong.nststore.dto.CartItemDto;
import vantruong.nststore.dto.RequestOrder;
import vantruong.nststore.dto.UpdateQuantityRequest;
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
            .message(MessageConstant.FIND_SUCCESS)
            .data(cartService.getItems(email))
            .build());
  }

  @PostMapping(ADD_TO_CART)
  public ResponseEntity<CommonResponse<Object>> addToCart(@RequestBody @Valid CartItemDto cartItemDto) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.ADD_TO_CART_SUCCESS)
            .data(cartService.addToCart(cartItemDto))
            .build());
  }

  @PostMapping(REMOVE_FROM_CART)
  public ResponseEntity<CommonResponse<Object>> removeFromCart(@PathVariable("email") String email, @PathVariable("id") int productId) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.DELETE_SUCCESS)
            .data(cartService.removeFromCart(email, productId))
            .build());
  }

  @PostMapping
  public ResponseEntity<CommonResponse<Object>> updateQuantity(@RequestBody UpdateQuantityRequest request) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.UPDATE_SUCCESS)
            .data(cartService.updateQuantity(request))
            .build());
  }

  @PostMapping("")
  public ResponseEntity<CommonResponse<Object>> requestOrder(@RequestBody RequestOrder requestOrder) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.UPDATE_SUCCESS)
            .data(cartService.requestOrder(requestOrder))
            .build());
  }
}
