package vantruong.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vantruong.productservice.common.CommonResponse;
import vantruong.productservice.common.Utils;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;
  private final CategoryService categoryService;
  //  private final ProductSpecification specification;
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

      // sort list product before using paging because PageImpl not sort
      List<Product> sortedList = sortList(productList, order);
      int first = Math.min(Long.valueOf(pageable.getOffset()).intValue(), sortedList.size());
      int last = Math.min(first + pageable.getPageSize(), sortedList.size());

      return new PageImpl<>(sortedList.subList(first, last), pageable, sortedList.size());
    }
    return productRepository.findAll(createPagingAndSort(order, pageNo));
  }

  @Override
  public ProductResponse getProductWithCategoryById(int id) {
    Product product = getProductById(id);
    List<Category> categories = categoryService.getAllLevelParentByCategory(product.getCategory().getId());
    return ProductResponse.builder()
            .product(product)
            .categories(categories)
            .build();
  }

  @Override
  public Product getProductById(int id) {
    return productRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.PRODUCT_NOT_FOUND));
  }

  /**
   * recursion func
   *
   * @param id
   * @return
   */
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

  /**
   * @param products
   * @param order:   asc || desc
   * @return List
   */
  @Override
  public List<Product> sortList(List<Product> products, String order) {
    // if order empty return list product not sort
    if (order.isEmpty()) {
      return products;
    }
    Comparator<Product> comparator = Comparator.comparing(Product::getPrice);
    if (order.equalsIgnoreCase("desc")) {
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

  /**
   * @param name
   * @param limit
   * @return list product with name like %name% and ignoreCase
   */
  @Override
  public List<Product> findProductByName(String name, int limit) {
    return productRepository.findProductByNameContainingIgnoreCase(name.trim(), Limit.of(limit));
  }

  @Override
  public int getStockById(int id) {
    Optional<Product> product = productRepository.findById(id);
    return product.map(Product::getStock).orElse(0);
  }

  @Override
  @Transactional
  public CommonResponse<Object> updateProductQuantityByOrder(Map<Integer, Integer> stockUpdate) {
    List<String> errorMessages = new ArrayList<>();
    for (Map.Entry<Integer, Integer> productIdAndQuantity : stockUpdate.entrySet()) {
      int productId = productIdAndQuantity.getKey();
      int quantity = productIdAndQuantity.getValue();

      Product product = getProductById(productId);
      int newQuantity = product.getStock() - quantity;
      if (newQuantity < 0) {
        errorMessages.add("Insufficient quantity for product with Id: " + productId);
      } else {
        product.setStock(newQuantity);
        productRepository.save(product);
      }
    }

    if(!errorMessages.isEmpty()) {
      return CommonResponse.builder()
              .isSuccess(false)
              .message(Utils.formatErrorMessage(errorMessages))
              .build();
    }

    return CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.UPDATE_QUANTITY_SUCCESS)
            .build();
  }
}
