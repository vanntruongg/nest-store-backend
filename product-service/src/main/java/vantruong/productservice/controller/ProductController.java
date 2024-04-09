package vantruong.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vantruong.productservice.common.CommonResponse;
import vantruong.productservice.constant.MessageConstant;
import vantruong.productservice.entity.dto.ProductDto;
import vantruong.productservice.service.ProductService;

import java.util.Map;

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
          @RequestParam(name = "category", defaultValue = "0") int categoryId,
          @RequestParam(name = "order") String order,
          @RequestParam(name = "page", defaultValue = "0") int pageNo
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(productService.getAllProduct(categoryId, order, pageNo))
            .build());
  }

  @GetMapping(PRODUCT_GET_BY_ID)
  public ResponseEntity<CommonResponse<Object>> getProductById(@PathVariable("id") int id) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(productService.getProductWithCategoryById(id))
            .build());
  }

  @GetMapping(PRODUCT_GET_STOCK_BY_ID)
  public ResponseEntity<CommonResponse<Object>> getStockById(@PathVariable("id") int id) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(productService.getStockById(id))
            .build());
  }

  @PostMapping("/update-quantity-order")
  public ResponseEntity<CommonResponse<Object>> updateProductQuantityByOrder(@RequestBody Map<Integer, Integer> stockUpdate) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(productService.updateProductQuantityByOrder(stockUpdate))
            .build());
  }

  @GetMapping(PRODUCT_GET_BY_NAME)
  public ResponseEntity<CommonResponse<Object>> getProductByName(
          @RequestParam("name") String name,
          @RequestParam("limit") int limit
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(productService.findProductByName(name, limit))
            .build());
  }


}
