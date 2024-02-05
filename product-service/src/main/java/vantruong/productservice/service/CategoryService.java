package vantruong.productservice.service;

import vantruong.productservice.entity.Category;

import java.util.List;

public interface CategoryService {
  List<Category> getTopLevelCategory();

  List<Category> getSubCategoriesByParentId(int parentId);

  Category getCategoryById(int id);
}
