package vantruong.productservice.entity.dto;

import lombok.Getter;

@Getter
public class ProductDto {
  private String name;
  private double price;
  private String material;
  private String style;
  private String imageUrl;
  private int categoryId;
}
