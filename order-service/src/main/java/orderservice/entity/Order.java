package orderservice.entity;

import jakarta.persistence.*;
import lombok.*;
import orderservice.enums.OrderStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "o_id")
  private int orderId;

  @Column(name = "email")
  private String email;

  @Column(name = "o_name")
  private String name;

  @Column(name = "o_phone")
  private String phone;

  @Column(name = "o_address")
  private String address;

  @Column(name = "o_notes")
  private String notes;

  @Column(name = "O_total_price")
  private double totalPrice;

  @Column(name = "o_status")
  private OrderStatus orderStatus;

  @ManyToOne
  @JoinColumn(name = "o_payment_method", referencedColumnName = "pm_id")
  private PaymentMethod paymentMethod;

}
