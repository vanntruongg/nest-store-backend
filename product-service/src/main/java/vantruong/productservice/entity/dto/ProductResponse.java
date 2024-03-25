package vantruong.productservice.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vantruong.productservice.entity.Category;
import vantruong.productservice.entity.Product;

import java.util.List;

@Builder
@Getter
@Setter
public class ProductResponse {
  private Product product;
  private List<Category> categories;
}
