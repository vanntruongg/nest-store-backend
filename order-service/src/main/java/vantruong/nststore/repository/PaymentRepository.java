package vantruong.nststore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vantruong.nststore.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
