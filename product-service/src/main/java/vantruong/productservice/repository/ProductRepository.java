package vantruong.productservice.repository;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import vantruong.productservice.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, PagingAndSortingRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
  @Query("select p from Product p where p.category.id = :id")
  List<Product> findAllByCategoryId(int id, Limit limit);

  @Query(value = "WITH RECURSIVE category_tree AS ( " +
          "    SELECT cat_id, parent_cat_id, cat_name " +
          "    FROM categories " +
          "    WHERE cat_id = :categoryId " +
          "    UNION ALL " +
          "    SELECT c.cat_id, c.parent_cat_id, c.cat_name " +
          "    FROM categories c " +
          "    JOIN category_tree ct ON c.parent_cat_id = ct.cat_id " +
          ") " +
          "SELECT p.* " +
          "FROM products p " +
          "JOIN category_tree ct ON p.cat_id = ct.cat_id", nativeQuery = true)
  List<Product> findAllByCategoryAndSubcategories(int categoryId);

  List<Product> findProductByNameContainingIgnoreCase(String name, Limit limit);

  @Query("select count(p) from Product p")
  Long getProductCount();

}
