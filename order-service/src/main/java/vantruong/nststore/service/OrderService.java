package vantruong.nststore.service;

import vantruong.nststore.entity.Order;
import vantruong.nststore.entity.dto.OrderRequest;

import java.util.List;

public interface OrderService {
  List<Order> getAllOrder();

  Boolean createOrder(OrderRequest orderRequest);
}
