package vantruong.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vantruong.productservice.constant.MessageConstant;
import vantruong.productservice.entity.Category;
import vantruong.productservice.exception.ErrorCode;
import vantruong.productservice.exception.NotFoundException;
import vantruong.productservice.repository.CategoryRepository;
import vantruong.productservice.service.CategoryService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;

  @Override
  public List<Category> getTopLevelCategory() {
    return categoryRepository.findTopLevelCategory();
  }

  @Override
  public List<Category> getSubCategoriesByParentId(int parentId) {
    return categoryRepository.findSubcategoriesByParentId(parentId);
  }

  @Override
  public Category getCategoryById(int id) {
    return categoryRepository.findById(id).orElseThrow(() ->
            new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.CATEGORY_NOT_FOUND));
  }

}
