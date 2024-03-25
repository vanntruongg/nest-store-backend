package vantruong.productservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import vantruong.productservice.entity.Product;
import vantruong.productservice.entity.dto.ProductDto;
import vantruong.productservice.entity.dto.ProductResponse;

import java.util.List;

public interface ProductService {
  Page<Product> getAllProduct(int categoryId, String order, int pageNo);

  ProductResponse getProductById(int id);

  List<Product> getAllProductByCategoryId(int id);
  List<Product> sortList(List<Product> products, String order);

  Product createProduct(ProductDto productDto);

  List<Product> findProductByName(String name);
}
