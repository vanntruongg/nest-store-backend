package orderservice.service;

import orderservice.entity.PaymentMethod;

public interface PaymentMethodService {
  PaymentMethod getPaymentMethodById(int methodId);
}
