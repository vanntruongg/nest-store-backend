package vantruong.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vantruong.productservice.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
