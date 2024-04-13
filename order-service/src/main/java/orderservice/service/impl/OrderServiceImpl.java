package orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import orderservice.constant.MessageConstant;
import orderservice.entity.Order;
import orderservice.entity.OrderDetail;
import orderservice.entity.PaymentMethod;
import orderservice.entity.dto.OrderDto;
import orderservice.entity.dto.OrderRequest;
import orderservice.enums.OrderStatus;
import orderservice.exception.ErrorCode;
import orderservice.exception.NotFoundException;
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
  public List<OrderDto> getAllOrder() {
    List<Order> orders = orderRepository.findAll();
    return convertToListOrderDto(orders);
  }

  private Order findById(int id) {
    return orderRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.ORDER_NOT_FOUND));
  }

  @Override
  @Transactional
  public Boolean createOrder(OrderRequest orderRequest) {
    PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(orderRequest.getPaymentMethodId());

    Order newOrder = Order.builder()
            .email(orderRequest.getEmail())
            .phone(orderRequest.getPhone())
            .address(orderRequest.getAddress())
            .totalPrice(orderRequest.getTotalPrice())
            .notes(orderRequest.getNotes())
            .orderStatus(OrderStatus.PENDING_CONFIRM)
            .paymentMethod(paymentMethod)
            .build();
    orderRepository.save(newOrder);
    orderDetailService.createOrderDetails(newOrder, orderRequest.getListProduct());

    return true;
  }

  @Override
  public List<OrderDto> getOrderByStatus(String status) {
    try {
      OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
      List<Order> orders = orderRepository.findOrderByOrderStatus(orderStatus);
      return convertToListOrderDto(orders);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid order status: " + status);
    }
  }

  @Override
  public List<OrderDto> getOrderByEmailAndStatus(String email, String status) {
    try {
      OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
      List<Order> orders = orderRepository.findOrderByEmailAndOrderStatus(email, orderStatus);
      return convertToListOrderDto(orders);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid order status: " + status);
    }
  }

  @Override
  public List<OrderDto> getOrderByEmail(String email) {
    List<Order> orders = orderRepository.findAllByEmail(email);
    return convertToListOrderDto(orders);
  }

  @Override
  public OrderDto getOrderById(int id) {
    Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, "Không tìm thấy đơn hàng có mã: " + id));
    List<OrderDetail> orderDetails = orderDetailService.getAllOrderDetailByOrder(order);
    return convertToOrderDto(order, orderDetails);
  }

  @Override
  @Transactional
  public Boolean updateStatus(int id, String status) {
      Order order = findById(id);
      order.setOrderStatus(OrderStatus.getOrderStatus(status));
      orderRepository.save(order);
      return true;
  }


  private OrderDto convertToOrderDto(Order order, List<OrderDetail> orderDetail) {
    return OrderDto.builder()
            .orderId(order.getOrderId())
            .email(order.getEmail())
            .name(order.getName())
            .phone(order.getPhone())
            .address(order.getAddress())
            .notes(order.getNotes())
            .totalPrice(order.getTotalPrice())
            .orderStatus(order.getOrderStatus().getName())
            .paymentMethod(order.getPaymentMethod().getMethod())
            .orderDetail(orderDetail)
            .build();
  }

  private List<OrderDto> convertToListOrderDto(List<Order> orders) {
    return orders.stream().map(order -> {
      List<OrderDetail> orderDetail = orderDetailService.getAllOrderDetailByOrder(order);
      return convertToOrderDto(order, orderDetail);
    }).toList();
  }
}
