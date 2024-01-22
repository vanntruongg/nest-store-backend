package vantruong.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import vantruong.productservice.service.CategoryService;

@RestController
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;
}
