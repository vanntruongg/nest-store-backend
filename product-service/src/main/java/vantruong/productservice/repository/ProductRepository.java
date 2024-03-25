package vantruong.productservice.repository;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import vantruong.productservice.entity.Category;
import vantruong.productservice.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, PagingAndSortingRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
  @Query("select p from Product p where p.category.id = :id")
  List<Product> findAllByCategoryId(int id);

  List<Product> findProductByNameContainingIgnoreCase(String name, Limit limit);

}
