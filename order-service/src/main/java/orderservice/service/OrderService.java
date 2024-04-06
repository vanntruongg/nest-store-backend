package orderservice.service;

import orderservice.entity.dto.OrderRequest;
import orderservice.entity.Order;

import java.util.List;

public interface OrderService {
  List<Order> getAllOrder();

  Boolean createOrder(OrderRequest orderRequest);

  List<Order> getOrderByStatus(String status);
  List<Order> getOrderByEmailAndStatus(String email, String status);
}
