package orderservice.entity.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequest {
  private String email;
  private String name;
  private String phone;
  private String address;
  private float totalPrice;
  private String notes;
  private int paymentMethodId;
  private List<OrderDetailDto> listProduct;
}
