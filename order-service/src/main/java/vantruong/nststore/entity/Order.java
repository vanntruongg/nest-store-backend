package vantruong.nststore.entity;

import jakarta.persistence.*;
import lombok.*;
import vantruong.nststore.enums.OrderStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order")
public class Order extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "o_id")
  private int orderId;

  @Column(name = "email")
  private String email;

  @Column(name = "o_phone")
  private String phone;

  @Column(name = "o_shipping_address")
  private String address;

  @Column(name = "O_total_price")
  private double totalPrice;

  @Column(name = "o_status")
  private OrderStatus orderStatus;

  @ManyToOne
  @JoinColumn(name = "p_id", referencedColumnName = "p_id")
  private Payment payment;
}
