package orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import orderservice.client.RestClient;
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
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
  private final OrderRepository orderRepository;
  private final OrderDetailService orderDetailService;
  private final PaymentMethodService paymentMethodService;
  private final RestClient restClient;
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
    Order savedOrder = orderRepository.save(newOrder);
    List<OrderDetail> orderDetails = orderDetailService.createOrderDetails(newOrder, orderRequest.getListProduct());
    restClient.sendMailConfirmOrder(convertToOrderDto(savedOrder, orderDetails));
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
  public Map<Integer, Double> getRevenue(Integer year, Integer month) {
    Map<Integer, Double> revenue = new LinkedHashMap<>();

    initializeRevenueMap(revenue, year, month, 0.0);

    Set<Object[]> results = !Objects.isNull(month)
            ? orderRepository.getTotalPriceByMonthInYear(year, month)
            : orderRepository.getTotalPriceByYear(year);

    if (!results.isEmpty()) {
      for (Object[] result : results) {
        revenue.put((Integer) result[0], (Double) result[1]);
      }
    }
    return revenue;
  }

  @Override
  public Map<Integer, Long> statisticOrder(Integer year, Integer month) {
    Map<Integer, Long> totalOrder = new LinkedHashMap<>();

    initializeRevenueMap(totalOrder, year, month, 0L);

    Set<Object[]> results = !Objects.isNull(month)
            ? orderRepository.getTotalOrderByMonthInYear(year, month)
            : orderRepository.getTotalOrderByYear(year);

    if (!results.isEmpty()) {
      for (Object[] result : results) {
        totalOrder.put((Integer) result[0], (Long) result[1]);
      }
    }
    return totalOrder;
  }


  // Method to initialize revenue map with default values
  private <T extends Number> void initializeRevenueMap(Map<Integer, T> revenue, Integer year, Integer month, T defaultValue) {
    int numMonths = (Objects.isNull(month)) ? MONTH_IN_YEAR : LocalDate.of(year, month, 1).lengthOfMonth();
    for (int i = 1; i <= numMonths; i++) {
      revenue.put(i, defaultValue);
    }
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
