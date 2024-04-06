package orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import orderservice.client.RestClient;
import orderservice.common.CommonResponse;
import orderservice.entity.Order;
import orderservice.entity.OrderDetail;
import orderservice.entity.dto.OrderDetailDto;
import orderservice.exception.ErrorCode;
import orderservice.exception.InsufficientProductQuantityException;
import orderservice.repository.OrderDetailRepository;
import orderservice.service.OrderDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
  private final OrderDetailRepository orderDetailRepository;
  private final RestClient restClient;

  @Override
  public void createOrderDetail(Order order, OrderDetailDto orderDetailDto) {
    OrderDetail orderDetail = mapOrderDetailDTOToOrderDetail(orderDetailDto);
    orderDetail.setOrder(order);
    orderDetailRepository.save(orderDetail);
  }

  @Override
  @Transactional
  public void createOrderDetails(Order order, List<OrderDetailDto> orderDetailDTOs) {
    List<OrderDetail> orderDetails = new ArrayList<>();
    orderDetailDTOs.forEach(orderDetailDto -> {
      OrderDetail orderDetail = mapOrderDetailDTOToOrderDetail(orderDetailDto);
      orderDetail.setOrder(order);
      orderDetails.add(orderDetail);

//    call the product service to update product quantity
      restClient.updateProductQuantity(orderDetailDto.getProductId(), orderDetailDto.getQuantity());
    });
    orderDetailRepository.saveAll(orderDetails);

//  call the cart service to remove items from the cart
    restClient.removeItemsFromCart(order.getEmail(), orderDetailDTOs);
  }

  private OrderDetail mapOrderDetailDTOToOrderDetail(OrderDetailDto orderDetailDto) {
    return OrderDetail.builder()
            .productId(orderDetailDto.getProductId())
            .productName(orderDetailDto.getProductName())
            .quantity(orderDetailDto.getQuantity())
            .productPrice(orderDetailDto.getProductPrice())
            .build();
  }
}
