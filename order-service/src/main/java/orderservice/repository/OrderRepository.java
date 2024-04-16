package orderservice.repository;

import orderservice.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import orderservice.entity.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
  List<Order> findOrderByOrderStatus(OrderStatus status);

  List<Order> findOrderByEmailAndOrderStatus(String email, OrderStatus status);

  List<Order> findAllByEmail(String email);

  @Query("select o.orderStatus, count(o) from Order o group by o.orderStatus")
  List<Object[]> findOrderCountByStatus();
}
