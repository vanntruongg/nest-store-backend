package orderservice.service;

import orderservice.entity.PaymentMethod;

import java.util.List;

public interface PaymentMethodService {
  PaymentMethod getPaymentMethodById(int methodId);

  List<PaymentMethod> getAllPaymentMethod();
}
