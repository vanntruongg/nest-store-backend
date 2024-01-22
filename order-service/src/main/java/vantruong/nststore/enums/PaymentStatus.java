package vantruong.nststore.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {
  PAID("Đã thanh toán"),
  UNPAID("Chưa thanh toán");

  private final String paymentStatus;

}
