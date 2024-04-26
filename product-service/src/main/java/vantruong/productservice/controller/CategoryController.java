package vantruong.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vantruong.productservice.common.CommonResponse;
import vantruong.productservice.constant.ApiEndpoint;
import vantruong.productservice.constant.MessageConstant;
import vantruong.productservice.entity.dto.CategoryDto;
import vantruong.productservice.service.CategoryService;

@RestController
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;

  @PostMapping()
  public ResponseEntity<CommonResponse<Object>> createCategory(@RequestBody CategoryDto categoryDto) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.CREATE_CATEGORY_SUCCESS)
            .data(categoryService.createCategory(categoryDto))
            .build());
  }

  @GetMapping(ApiEndpoint.CATEGORY)
  public ResponseEntity<CommonResponse<Object>> getALlCategory() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(categoryService.getALlCategory())
            .build());
  }

  @GetMapping(ApiEndpoint.TOP_LEVEL_CATEGORY)
  public ResponseEntity<CommonResponse<Object>> getTopLevelCategory() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(categoryService.getTopLevelCategory())
            .build());
  }


  @GetMapping(ApiEndpoint.CATEGORY_GET_SUBCATEGORY)
  public ResponseEntity<CommonResponse<Object>> getSubCategoriesByParentId(@PathVariable("id") int parentId) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(categoryService.getSubCategoriesByParentId(parentId))
            .build());
  }

  @GetMapping(ApiEndpoint.CATEGORY_GET_SUBCATEGORY_ALL_LEVEL)
  public ResponseEntity<CommonResponse<Object>> getAllLevelSubCategoriesByParentId(@PathVariable("id") int categoryId) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(categoryService.getAllLevelChildrenByCategory(categoryId))
            .build());
  }
}
