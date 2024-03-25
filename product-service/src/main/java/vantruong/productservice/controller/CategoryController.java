package vantruong.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vantruong.productservice.common.CommonResponse;
import vantruong.productservice.constant.ApiEndpoint;
import vantruong.productservice.constant.MessageConstant;
import vantruong.productservice.service.CategoryService;

@RestController
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;

  @GetMapping(ApiEndpoint.CATEGORY)
  private ResponseEntity<CommonResponse<Object>> getTopLevelCategory() {
    return ResponseEntity.ok().body(CommonResponse.builder()
                    .isSuccess(true)
                    .message(MessageConstant.FIND_SUCCESS)
                    .data(categoryService.getTopLevelCategory())
            .build());
  }

  @GetMapping(ApiEndpoint.CATEGORY_GET_SUBCATEGORY)
  private ResponseEntity<CommonResponse<Object>> getSubCategoriesByParentId(@PathVariable("id") int parentId) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(categoryService.getSubCategoriesByParentId(parentId))
            .build());
  }

  @GetMapping(ApiEndpoint.CATEGORY_GET_SUBCATEGORY_ALL_LEVEL)
  private ResponseEntity<CommonResponse<Object>> getAllLevelSubCategoriesByParentId(@PathVariable("id") int categoryId) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(categoryService.getAllLevelChildrenByCategory(categoryId))
            .build());
  }
}
