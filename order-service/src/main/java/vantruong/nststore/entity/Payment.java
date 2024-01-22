package vantruong.nststore.entity;

import jakarta.persistence.*;
import lombok.*;
import vantruong.nststore.enums.PaymentStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "payment")
public class Payment extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "p_id")
  private int paymentId;

  @Column(name = "p_status")
  private PaymentStatus paymentStatus;

  @Column(name = "p_description")
  private String paymentDescription;

  @OneToOne
  @JoinColumn(name = "o_id", referencedColumnName = "orderId")
  private Order order;

  @ManyToOne
  @JoinColumn(name = "pm_id", referencedColumnName = "paymentMethodId")
  private PaymentMethod paymentMethod;
}
