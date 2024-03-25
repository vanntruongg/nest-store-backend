package vantruong.productservice.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import vantruong.productservice.entity.Product;

@Component
public class ProductSpecification {

  public Specification<Product> hasCategory(int categoryId) {
    return ((root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("category").get("id"), categoryId)
    );
  }
}
