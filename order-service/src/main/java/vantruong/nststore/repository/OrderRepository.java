package vantruong.nststore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vantruong.nststore.entity.Order;
import vantruong.nststore.enums.OrderStatus;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
  List<Order> findOrderByOrderStatus(OrderStatus status);

  List<Order> findOrderByEmailAndOrderStatus(String email, OrderStatus status);
}
