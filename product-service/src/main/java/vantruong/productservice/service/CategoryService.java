package vantruong.productservice.service;

import vantruong.productservice.entity.Category;
import vantruong.productservice.entity.dto.CategoryResponse;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
  List<Category> getTopLevelCategory();

  List<Category> getSubCategoriesByParentId(int parentId);
  Optional<Category> getParentCategoryByCategoryId(int categoryId);

  List<Category> getAllLevelParentByCategory(int categoryId);

  Category getCategoryById(int id);

  List<CategoryResponse> getAllLevelChildrenByCategory(int categoryId);
}
