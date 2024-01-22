package vantruong.nststore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vantruong.nststore.repository.OrderRepository;
import vantruong.nststore.service.OrderService;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
  private final OrderRepository orderRepository;
}
