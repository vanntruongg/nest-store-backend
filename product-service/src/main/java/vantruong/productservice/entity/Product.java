package vantruong.productservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "p_id")
  private int id;

  @Column(name = "p_name")
  private String name;

  @Column(name = "p_price")
  private double price;

  @Column(name = "p_material")
  private String material;

  @Column(name = "p_styles")
  private String style;

  @Column(name = "p_image_url")
  private String imageUrl;

  @ManyToOne
  @JoinColumn(name = "cat_id", referencedColumnName = "cat_id")
  private Category category;

}
