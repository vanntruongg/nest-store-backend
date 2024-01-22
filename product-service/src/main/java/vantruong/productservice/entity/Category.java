package vantruong.productservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "category")
public class Category extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "c_id")
  private int id;

  @Column(name = "c_name")
  private String name;

}
