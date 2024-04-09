package orderservice.service;

import orderservice.entity.Order;
import orderservice.entity.OrderDetail;
import orderservice.entity.dto.OrderDetailDto;

import java.util.List;

public interface OrderDetailService {
  void createOrderDetail(Order order, OrderDetailDto orderDetailDto);
  void createOrderDetails(Order order, List<OrderDetailDto> orderDetailDTOs);

  List<OrderDetail> getAllOrderDetailByOrder(Order order);
}
