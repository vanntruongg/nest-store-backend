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

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
  private final OrderRepository orderRepository;
  private final OrderDetailService orderDetailService;
  private final PaymentMethodService paymentMethodService;

  private static final int MONTH_IN_YEAR = 12;

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
  public OrderRequest createOrder(OrderRequest orderRequest) {
    PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(orderRequest.getPaymentMethodId());

    Order newOrder = Order.builder()
            .email(orderRequest.getEmail())
            .name(orderRequest.getName())
            .phone(orderRequest.getPhone())
            .address(orderRequest.getAddress())
            .totalPrice(orderRequest.getTotalPrice())
            .notes(orderRequest.getNotes())
            .orderStatus(OrderStatus.PENDING_CONFIRM)
            .paymentMethod(paymentMethod)
            .build();
    orderRepository.save(newOrder);
    orderDetailService.createOrderDetails(newOrder, orderRequest.getListProduct());

    return orderRequest;
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
      List<Order> orders = orderRepository.findOrderByEmailAndOrderStatusOrderByCreatedDateDesc(email, orderStatus);
      return convertToListOrderDto(orders);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid order status: " + status);
    }
  }

  @Override
  public List<OrderDto> getOrderByEmail(String email) {
    List<Order> orders = orderRepository.findAllByEmailOrderByCreatedDateDesc(email);
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
    order.setOrderStatus(OrderStatus.findOrderStatus(status));
    orderRepository.save(order);
    return true;
  }

  @Override
  public Map<String, Long> getTotalOrderCountByStatus() {
    List<Object[]> results = orderRepository.findOrderCountByStatus();
    Map<String, Long> orderCountByStatus = new HashMap<>();
    for (Object[] result : results) {
      orderCountByStatus.put(result[0].toString(), (Long) result[1]);
    }
    return orderCountByStatus;
  }

  @Override
  public Map<Integer, Integer> getCountOrderByMonth() {
    List<Object[]> results = orderRepository.countOrderByMonth();
    Map<Integer, Integer> orderCountByMonth = new HashMap<>();

    // initial the order for each month to 0
    for (int i = 1; i <= MONTH_IN_YEAR; i++) {
      orderCountByMonth.put(i, 0);
    }

    // iterate through the results update the order count by month
    for (Object[] result : results) {
      int month = (int) result[0];
      int count = ((Number) result[1]).intValue();

      // update the order count for the corresponding month in the map
      orderCountByMonth.put(month, count);
    }

    return orderCountByMonth;
  }

  @Override
  public Map<Integer, Double> getYearlyRevenueTotal(int year) {
    Map<Integer, Double> yearlyRevenueTotal = new LinkedHashMap<>();
    // from JAN to DEC
    for (int i = 1; i <= 12; i++) {
      yearlyRevenueTotal.put(i, 0.0);
    }
    List<Object[]> results = orderRepository.getTotalPriceByYear(year);
    for (Object[] result : results) {
      yearlyRevenueTotal.put((Integer) result[0], (Double) result[1]);
    }
    return yearlyRevenueTotal;
  }

  @Override
  public Map<Integer, Double> getMonthlyRevenueByYear(int year, int month) {
    Map<Integer, Double> monthlyRevenueByYear = new LinkedHashMap<>();
    List<Object[]> results = orderRepository.getTotalPriceByMonthInYear(year, month);

    // from JAN to the number of month
    for (int i = 1; i <= LocalDate.of(year, month, 1).lengthOfMonth(); i++) {
      monthlyRevenueByYear.put(i, 0.0);
    }
    for (Object[] result : results) {
      monthlyRevenueByYear.put((Integer) result[0], (Double) result[1]);
    }
    return monthlyRevenueByYear;
  }


  public Double getAllRevenue() {
    return orderRepository.getTotalPrice();
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
            .orderStatus(order.getOrderStatus().name())
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
