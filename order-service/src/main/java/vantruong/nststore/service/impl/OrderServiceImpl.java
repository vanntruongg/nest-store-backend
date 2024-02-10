package vantruong.nststore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vantruong.nststore.entity.Order;
import vantruong.nststore.repository.OrderRepository;
import vantruong.nststore.service.OrderService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
  private final OrderRepository orderRepository;

  @Override
  public List<Order> getAllOrder() {
    return orderRepository.findAll();
  }
}
