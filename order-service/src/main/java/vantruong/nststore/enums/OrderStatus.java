package vantruong.nststore.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
  PENDING_CONFIRM("Chờ xác nhận"),
  PROCESSING("Đang xử lý"),
  SHIPPING("Đang giao"),
  COMPLETED("Hoàn thành"),
  CANCELED("Đã hủy");

  private final String orderStatus;
}
