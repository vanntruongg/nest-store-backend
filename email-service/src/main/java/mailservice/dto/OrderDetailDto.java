package mailservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@Builder
public class OrderDetailDto {
  private int orderDetailId;

  private int productId;

  private int quantity;

  private String productName;

  private float productPrice;

  private String productImage;
}
