package vantruong.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vantruong.productservice.constant.MessageConstant;
import vantruong.productservice.entity.Category;
import vantruong.productservice.entity.Product;
import vantruong.productservice.entity.dto.ProductDto;
import vantruong.productservice.entity.dto.ProductResponse;
import vantruong.productservice.exception.ErrorCode;
import vantruong.productservice.exception.NotFoundException;
import vantruong.productservice.repository.ProductRepository;
import vantruong.productservice.service.CategoryService;
import vantruong.productservice.service.ProductService;
import vantruong.productservice.specification.ProductSpecification;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;
  private final CategoryService categoryService;
  private final ProductSpecification specification;
  private static final int PAGE_SIZE = 10;

  private Pageable createPagingAndSort(String order, int pageNo) {
    pageNo = Math.max(0, pageNo - 1);
    Sort sort = Sort.unsorted();
    if (order != null && (order.equalsIgnoreCase("desc") || order.equalsIgnoreCase("asc"))) {
      sort = Sort.by(Sort.Direction.fromString(order.toUpperCase()), "price");
    }
    return PageRequest.of(pageNo, PAGE_SIZE, sort);
  }

  @Override
  public Page<Product> getAllProduct(int categoryId, String order, int pageNo) {
    if (categoryId != 0) {
      List<Product> productList = getAllProductByCategoryId(categoryId);
      Pageable pageable = createPagingAndSort(order, pageNo);

      List<Product> sortedList = sortList(productList, order);
      int first = Math.min(Long.valueOf(pageable.getOffset()).intValue(), sortedList.size());
      int last = Math.min(first + pageable.getPageSize(), sortedList.size());

      return new PageImpl<>(sortedList.subList(first, last), pageable, sortedList.size());
    }
    return productRepository.findAll(createPagingAndSort(order, pageNo));
  }

  @Override
  public ProductResponse getProductById(int id) {
    Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.PRODUCT_NOT_FOUND));
    List<Category> categories = categoryService.getAllLevelParentByCategory(product.getCategory().getId());
    return ProductResponse.builder()
            .product(product)
            .categories(categories)
            .build();
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
  public List<Product> sortList(List<Product> products, String order) {
    Comparator<Product> comparator = Comparator.comparing(Product::getPrice);
    if (order != null && order.equalsIgnoreCase("desc")) {
      comparator = comparator.reversed();
    }
    return products.stream()
            .sorted(comparator)
            .collect(Collectors.toList());
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

  @Override
  public List<Product> findProductByName(String name) {
    return productRepository.findProductByNameContainingIgnoreCase(name.trim(), Limit.of(10));
  }
}
