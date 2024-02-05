package inventoryservice.inventory;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Inventory")
public class Inventory {
  @Id
  private ObjectId id;
  private String type;
  private String productId;
  private String quantity;
  private Partner supplier;
  private Partner customer;
}
