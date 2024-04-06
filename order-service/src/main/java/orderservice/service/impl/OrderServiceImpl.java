package orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import orderservice.client.RestClient;
import orderservice.entity.Order;
import orderservice.entity.Payment;
import orderservice.entity.PaymentMethod;
import orderservice.entity.dto.OrderRequest;
import orderservice.enums.EPaymentMethod;
import orderservice.enums.OrderStatus;
import orderservice.enums.PaymentStatus;
import orderservice.repository.OrderRepository;
import orderservice.service.OrderDetailService;
import orderservice.service.OrderService;
import orderservice.service.PaymentMethodService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    orderDetailService.createOrderDetails(newOrder, orderRequest.getListProduct());

    return true;
  }

  @Override
  public List<Order> getOrderByStatus(String status) {
    try {
      OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
      return orderRepository.findOrderByOrderStatus(orderStatus);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid order status: " + status);
    }
  }

  @Override
  public List<Order> getOrderByEmailAndStatus(String email, String status) {
    try {
      OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
      return orderRepository.findOrderByEmailAndOrderStatus(email, orderStatus);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid order status: " + status);
    }
  }
}
