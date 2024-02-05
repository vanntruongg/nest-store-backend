package vantruong.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vantruong.productservice.entity.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

  @Query("select c from Category c where c.parentCategory is null")
  List<Category> findTopLevelCategory();

  @Query("select c from Category c where c.parentCategory.id = :parentId")
  List<Category> findSubcategoriesByParentId(int parentId);
}
