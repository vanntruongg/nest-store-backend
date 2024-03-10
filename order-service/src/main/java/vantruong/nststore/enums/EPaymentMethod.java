package vantruong.nststore.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EPaymentMethod {
  COD("COD"),
  VNPAY("VNPay");

  private final String paymentMethod;
}
