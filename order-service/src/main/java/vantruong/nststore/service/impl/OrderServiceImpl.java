package vantruong.nststore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vantruong.nststore.entity.Order;
import vantruong.nststore.entity.Payment;
import vantruong.nststore.entity.PaymentMethod;
import vantruong.nststore.entity.dto.OrderDetailDto;
import vantruong.nststore.entity.dto.OrderRequest;
import vantruong.nststore.enums.EPaymentMethod;
import vantruong.nststore.enums.OrderStatus;
import vantruong.nststore.enums.PaymentStatus;
import vantruong.nststore.repository.OrderRepository;
import vantruong.nststore.service.OrderDetailService;
import vantruong.nststore.service.OrderService;
import vantruong.nststore.service.PaymentMethodService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
  private final OrderRepository orderRepository;
  private final OrderDetailService orderDetailService;
  private final PaymentMethodService paymentMethodService;

  @Override
  public List<Order> getAllOrder() {
    return orderRepository.findAll();
  }

  @Override
  @Transactional
  public Boolean createOrder(OrderRequest orderRequest) {
    PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(orderRequest.getPaymentMethodId());
    PaymentStatus paymentStatus = paymentMethod.getMethod().equals(EPaymentMethod.COD.getPaymentMethod()) ? PaymentStatus.UNPAID : PaymentStatus.PAID;
    Payment payment = Payment.builder()
            .paymentStatus(paymentStatus)
            .paymentMethod(paymentMethod)
            .build();

    Order newOrder = Order.builder()
            .email(orderRequest.getEmail())
            .phone(orderRequest.getPhone())
            .address(orderRequest.getAddress())
            .totalPrice(orderRequest.getTotalPrice())
            .orderStatus(OrderStatus.PROCESSING)
            .payment(payment)
            .build();
    orderRepository.save(newOrder);

    for (OrderDetailDto orderDetail : orderRequest.getListProduct()) {
      orderDetailService.createOrderDetail(newOrder, orderDetail);
    }

    return true;
  }
}
