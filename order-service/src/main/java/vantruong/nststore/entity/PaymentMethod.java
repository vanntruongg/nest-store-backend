package vantruong.nststore.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "payment_method")
public class PaymentMethod {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "pm_id")
  private int paymentMethodId;

  @Column(name = "pm_method")
  private String method;

  @Column(name = "pm_description")
  private String description;
}
