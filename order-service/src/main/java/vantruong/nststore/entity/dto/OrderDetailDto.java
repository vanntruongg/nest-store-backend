package vantruong.nststore.entity.dto;

import lombok.Getter;

@Getter
public class OrderDetailDto {
  private int productId;
  private String productName;
  private int quantity;
  private float productPrice;
}