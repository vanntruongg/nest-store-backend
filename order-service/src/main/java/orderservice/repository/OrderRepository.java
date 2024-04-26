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

  List<Order> findOrderByEmailAndOrderStatusOrderByCreatedDateDesc(String email, OrderStatus status);

  List<Order> findAllByEmailOrderByCreatedDateDesc(String email);

  @Query("select o.orderStatus, count(o) from Order o group by o.orderStatus")
  List<Object[]> findOrderCountByStatus();

  @Query("select extract(month from o.createdDate), count(*) " +
          "from Order o " +
          "where extract(year from o.createdDate) = extract(year from current_date )" +
          "group by extract(month from o.createdDate)")
  List<Object[]> countOrderByMonth();

  @Query("select sum(o.totalPrice) from Order o")
  double getTotalPrice();

  @Query("select extract(month from o.createdDate), sum(o.totalPrice) " +
          "from Order o " +
          "where extract(year from o.createdDate) = extract(year from current_date ) " +
          "group by extract(month from o.createdDate) ")
  List<Object[]> getTotalPriceByMonth();


  @Query("select extract(month from o.createdDate), sum(o.totalPrice) " +
          "from Order o " +
          "where extract(year from o.createdDate) = :year " +
          "group by extract(month from o.createdDate) ")
  List<Object[]> getTotalPriceByYear(int year);

  @Query("select extract(day from o.createdDate), sum(o.totalPrice) " +
          "from Order o " +
          "where extract(year from o.createdDate) = :year " +
          "and extract(month from o.createdDate) = :month " +
          "group by extract(day from o.createdDate) ")
  List<Object[]> getTotalPriceByMonthInYear(int year, int month);

}
