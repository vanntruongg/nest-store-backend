package vantruong.nststore.entity.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequest {
  private String email;
  private String phone;
  private String address;
  private List<OrderDetailDto> listProduct;
  private float totalPrice;
  private int paymentMethodId;
}
