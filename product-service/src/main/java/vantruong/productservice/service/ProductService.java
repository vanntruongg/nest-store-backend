package vantruong.productservice.service;

import org.springframework.data.domain.Page;
import vantruong.productservice.common.CommonResponse;
import vantruong.productservice.entity.Product;
import vantruong.productservice.entity.dto.ProductDto;
import vantruong.productservice.entity.dto.ProductResponse;

import java.util.List;
import java.util.Map;

public interface ProductService {
  Page<Product> getAllProduct(int categoryId, String order, int pageNo);

  ProductResponse getProductWithCategoryById(int id);
  Product getProductById(int id);

  List<Product> getAllProductByCategoryId(int id);

  List<Product> sortList(List<Product> products, String order);

  Product createProduct(ProductDto productDto);

  List<Product> findProductByName(String name, int limit);

  int getStockById(int id);

  CommonResponse<Object> updateProductQuantityByOrder(Map<Integer, Integer> stockUpdate);

  Boolean updateProduct(ProductDto productDto);

  List<Product> getAll();

  int getProductCount();
}
