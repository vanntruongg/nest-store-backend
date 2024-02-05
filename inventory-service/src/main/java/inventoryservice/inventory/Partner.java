package inventoryservice.inventory;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Partner {
  private String name;
  private String phone;
  private String address;
}
