package vantruong.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vantruong.productservice.constant.MessageConstant;
import vantruong.productservice.entity.Category;
import vantruong.productservice.entity.dto.CategoryDto;
import vantruong.productservice.entity.dto.CategoryResponse;
import vantruong.productservice.exception.ErrorCode;
import vantruong.productservice.exception.NotFoundException;
import vantruong.productservice.repository.CategoryRepository;
import vantruong.productservice.service.CategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;

  @Override
  public Category createCategory(CategoryDto categoryDto) {
    Category category = Category.builder()
            .name(categoryDto.getName())
            .image(categoryDto.getImage())
            .parentCategory(categoryDto.getParentCategory())
            .build();
    return categoryRepository.save(category);
  }

  @Override
  public List<CategoryResponse> getALlCategory() {
    List<Category> categories = categoryRepository.findTopLevelCategory();
    List<CategoryResponse> result = new ArrayList<>();
    for (Category category: categories) {
      List<CategoryResponse> categoryResponses = getAllLevelChildrenByCategory(category.getId());
      CategoryResponse categoryResponse = CategoryResponse.builder()
              .category(category)
              .subCategories(categoryResponses)
              .build();
      result.add(categoryResponse);
    }
    return result;
  }

  @Override
  public List<Category> getTopLevelCategory() {
    return categoryRepository.findTopLevelCategory();
  }

  @Override
  public List<Category> getSubCategoriesByParentId(int parentId) {
    return categoryRepository.findSubcategoriesByParentId(parentId);
  }

  @Override
  public Optional<Category> getParentCategoryByCategoryId(int categoryId) {
    return categoryRepository.findParentCategoryByCategoryId(categoryId);
  }

  @Override
  public List<Category> getAllLevelParentByCategory(int categoryId) {
    List<Category> categories = new ArrayList<>();
    Category category = getCategoryById(categoryId);
    categories.add(category);
    Optional<Category> existedParentCategory = getParentCategoryByCategoryId(category.getId());
    existedParentCategory.ifPresent(value -> categories.addAll(getAllLevelParentByCategory(value.getId())));
    return categories;
  }

  @Override
  public Category getCategoryById(int id) {
    return categoryRepository.findById(id).orElseThrow(() ->
            new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.CATEGORY_NOT_FOUND));
  }

  @Override
  public List<CategoryResponse> getAllLevelChildrenByCategory(int categoryId) {
    List<CategoryResponse> result = new ArrayList<>();
    getAllChildrenByCategoryRecursive(categoryId, result);
    return result;
  }


  private void getAllChildrenByCategoryRecursive(int categoryId, List<CategoryResponse> result) {
    List<Category> children = getSubCategoriesByParentId(categoryId);

    /* Each time the recursive call is made,
      the list of categories is set to the subcategory */
    for (Category child : children) {
      CategoryResponse categoryResponse = new CategoryResponse();
      categoryResponse.setCategory(child);
      List<CategoryResponse> subCategories = new ArrayList<>();
      categoryResponse.setSubCategories(subCategories);
      result.add(categoryResponse);
      getAllChildrenByCategoryRecursive(child.getId(), subCategories);
    }
  }

}
