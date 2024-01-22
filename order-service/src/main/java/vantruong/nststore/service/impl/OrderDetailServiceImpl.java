package vantruong.nststore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vantruong.nststore.repository.OrderDetailRepository;
import vantruong.nststore.service.OrderDetailService;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
  private final OrderDetailRepository orderDetailRepository;
}
