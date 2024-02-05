package vantruong.productservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cat_id")
  private int id;

  @Column(name = "cat_name")
  private String name;

  @ManyToOne
  @JoinColumn(name = "parent_cat_id")
  @JsonIgnore
  private Category parentCategory;

}
