package vantruong.productservice.service;

import org.springframework.data.domain.Page;
import vantruong.productservice.entity.Product;
import vantruong.productservice.entity.dto.ProductDto;

import java.util.List;

public interface ProductService {
  Page<Product> getAllProduct(String order, int pageNo);

  Product getProductById(int id);

  List<Product> getAllProductByCategoryId(int id);

  Product createProduct(ProductDto productDto);

  List<Product> findProductByName(String name);
}
