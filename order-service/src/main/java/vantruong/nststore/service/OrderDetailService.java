package vantruong.nststore.service;

import vantruong.nststore.entity.Order;
import vantruong.nststore.entity.dto.OrderDetailDto;

public interface OrderDetailService {
  void createOrderDetail(Order order, OrderDetailDto orderDetailDto);
}
