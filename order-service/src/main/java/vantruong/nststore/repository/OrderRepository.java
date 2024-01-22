package vantruong.nststore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vantruong.nststore.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
