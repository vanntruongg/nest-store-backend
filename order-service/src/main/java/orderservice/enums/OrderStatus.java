package orderservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum OrderStatus {
  PENDING_CONFIRM("Chờ xác nhận"),
  PROCESSING("Đang xử lý"),
  SHIPPING("Đang giao"),
  COMPLETED("Hoàn thành"),
  CANCELED("Đã hủy");

  private final String orderStatus;

  public String getName() {
    return this.orderStatus;
  }

  public static OrderStatus findOrderStatus(String stt) {
    return Stream.of(OrderStatus.values())
            .filter(status -> status.name().equals(stt))
            .findFirst()
            .orElseThrow(IllegalAccessError::new);
  }
}
