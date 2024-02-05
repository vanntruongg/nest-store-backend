package vantruong.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vantruong.productservice.constant.MessageConstant;
import vantruong.productservice.entity.Category;
import vantruong.productservice.entity.Product;
import vantruong.productservice.entity.dto.ProductDto;
import vantruong.productservice.exception.ErrorCode;
import vantruong.productservice.exception.NotFoundException;
import vantruong.productservice.repository.ProductRepository;
import vantruong.productservice.service.CategoryService;
import vantruong.productservice.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;
  private final CategoryService categoryService;
  private static final int PAGE_SIZE = 20;

  private PageRequest createPagingAndSort(String order, int pageNo) {
    pageNo = Math.max(0, pageNo);
    Sort sort = Sort.unsorted();
    if (order != null && (order.equalsIgnoreCase("desc") || order.equalsIgnoreCase("asc"))) {
      sort = Sort.by(Sort.Direction.fromString(order.toUpperCase()), "price");
    }
    return PageRequest.of(pageNo, PAGE_SIZE, sort);
  }

  @Override
  public Page<Product> getAllProduct(String order, int pageNo) {
    return productRepository.findAll(createPagingAndSort(order, pageNo));
  }

  @Override
  public Product getProductById(int id) {
    return productRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.PRODUCT_NOT_FOUND));
  }

  @Override
  public List<Product> getAllProductByCategoryId(int id) {
    List<Category> categories = categoryService.getSubCategoriesByParentId(id);

    if (categories.isEmpty()) {
      return productRepository.findAllByCategoryId(id);
    } else {
      List<Product> productList = new ArrayList<>();
      for (Category category : categories) {
        List<Product> products = getAllProductByCategoryId(category.getId());
        productList.addAll(products);
      }
      return productList;
    }
  }

  @Override
  public Product createProduct(ProductDto productDto) {
    Category category = categoryService.getCategoryById(productDto.getCategoryId());

    Product product = Product.builder()
            .name(productDto.getName())
            .price(productDto.getPrice())
            .material(productDto.getMaterial())
            .style(productDto.getStyle())
            .imageUrl(productDto.getImageUrl())
            .category(category)
            .build();
    return productRepository.save(product);
  }
}
