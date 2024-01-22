package vantruong.nststore.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_detail")
public class OrderDetail extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "od_id")
  private int orderDetailId;

  @Column(name = "od_quantity")
  private int quantity;

  @Column(name = "product_id")
  private int productId;

  @Column(name = "product_name")
  private String productName;

  @Column(name = "product_price")
  private double productPrice;

  @ManyToOne
  @JoinColumn(name = "o_id", referencedColumnName = "orderId")
  private Order order;
}
