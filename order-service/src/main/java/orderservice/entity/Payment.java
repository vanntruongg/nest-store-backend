package orderservice.entity;

import jakarta.persistence.*;
import lombok.*;
import orderservice.enums.PaymentStatus;

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

  @ManyToOne
  @JoinColumn(name = "pm_id", referencedColumnName = "pm_id")
  private PaymentMethod paymentMethod;
}
