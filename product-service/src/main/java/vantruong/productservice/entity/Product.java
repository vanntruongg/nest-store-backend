package vantruong.productservice.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product")
public class Product extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "p_id")
  private int id;

  @Column(name = "p_name")
  private String mame;

  @Column(name = "p_price")
  private double price;

  @Column(name = "p_description")
  private String description;

  @Column(name = "p_detail")
  private String detail;

  @Column(name = "p_image_url")
  private String imageUrl;

  @ManyToOne
  @JoinColumn(name = "c_id", referencedColumnName = "id")
  private Category category;

}
