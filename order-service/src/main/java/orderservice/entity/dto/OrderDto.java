package orderservice.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import orderservice.entity.OrderDetail;
import orderservice.enums.OrderStatus;

import java.util.List;

@Getter
@Setter
@Builder
public class OrderDto {
  private int orderId;
  private String email;
  private String phone;
  private String address;
  private String notes;
  private double totalPrice;
  private String orderStatus;
  private String paymentMethod;
  private List<OrderDetail> orderDetail;
}
