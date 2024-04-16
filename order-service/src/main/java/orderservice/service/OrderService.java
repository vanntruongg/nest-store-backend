package orderservice.service;

import orderservice.entity.dto.OrderDto;
import orderservice.entity.dto.OrderRequest;
import orderservice.entity.Order;
import orderservice.enums.OrderStatus;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface OrderService {
  List<OrderDto> getAllOrder();

  OrderRequest createOrder(OrderRequest orderRequest);

  List<OrderDto> getOrderByStatus(String status);
  List<OrderDto> getOrderByEmailAndStatus(String email, String status);

  List<OrderDto> getOrderByEmail(String email);

  OrderDto getOrderById(int id);

  Boolean updateStatus(int id, String status);

  Map<String, Long> getTotalOrderCountByStatus();

  Map<Integer, Integer> getCountOrderByMonth();
}
