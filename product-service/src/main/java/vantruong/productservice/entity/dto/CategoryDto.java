package vantruong.productservice.entity.dto;

import lombok.Getter;
import vantruong.productservice.entity.Category;

@Getter
public class CategoryDto {

  private int id;

  private String name;

  private String image;

  private Category parentCategory;
}
