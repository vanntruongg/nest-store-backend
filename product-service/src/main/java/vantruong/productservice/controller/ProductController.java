package vantruong.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vantruong.productservice.common.CommonResponse;
import vantruong.productservice.constant.MessageConstant;
import vantruong.productservice.entity.dto.ProductDto;
import vantruong.productservice.service.ProductService;

import static vantruong.productservice.constant.ApiEndpoint.*;


@RestController
@RequiredArgsConstructor
public class ProductController {
  private final ProductService productService;

  @PostMapping(CREATE_PRODUCT)
  public ResponseEntity<CommonResponse<Object>> createProduct(@RequestBody ProductDto productDto) {
    return ResponseEntity.ok().body(CommonResponse.builder()
                    .isSuccess(true)
                    .message(MessageConstant.CREATE_PRODUCT_SUCCESS)
                    .data(productService.createProduct(productDto))
            .build());
  }

  @GetMapping(PRODUCTS)
  public ResponseEntity<CommonResponse<Object>> getAllProduct(
          @RequestParam(name = "order") String order,
          @RequestParam(name = "page", defaultValue = "0") int pageNo
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(productService.getAllProduct(order, pageNo))
            .build());
  }

  @GetMapping(PRODUCT_GET_BY_ID)
  public ResponseEntity<CommonResponse<Object>> getProductById(@PathVariable("id") int id) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(productService.getProductById(id))
            .build());
  }

  @GetMapping(PRODUCT_GET_BY_CATEGORY_ID)
  public ResponseEntity<CommonResponse<Object>> getAllProductByCategoryId(@PathVariable("id") int id) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(productService.getAllProductByCategoryId(id))
            .build());
  }



}
