package vantruong.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vantruong.productservice.service.ProductService;

import static vantruong.productservice.constant.ApiEndpoint.PRODUCT;


@RestController
@RequestMapping(PRODUCT)
@RequiredArgsConstructor
public class ProductController {
  private final ProductService productService;


}
