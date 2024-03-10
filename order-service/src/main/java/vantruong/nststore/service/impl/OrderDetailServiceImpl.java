package vantruong.nststore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vantruong.nststore.entity.Order;
import vantruong.nststore.entity.OrderDetail;
import vantruong.nststore.entity.dto.OrderDetailDto;
import vantruong.nststore.repository.OrderDetailRepository;
import vantruong.nststore.service.OrderDetailService;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
  private final OrderDetailRepository orderDetailRepository;

  @Override
  public void createOrderDetail(Order order, OrderDetailDto orderDetailDto) {
    OrderDetail orderDetail = OrderDetail.builder()
            .productId(orderDetailDto.getProductId())
            .productName(orderDetailDto.getProductName())
            .quantity(orderDetailDto.getQuantity())
            .productPrice(orderDetailDto.getProductPrice())
            .order(order)
            .build();
    orderDetailRepository.save(orderDetail);
  }
}
