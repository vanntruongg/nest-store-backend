package orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import orderservice.entity.PaymentMethod;
import orderservice.service.PaymentMethodService;
import org.springframework.stereotype.Service;
import orderservice.constant.MessageConstant;
import orderservice.exception.ErrorCode;
import orderservice.exception.NotFoundException;
import orderservice.repository.PaymentMethodRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {
  private final PaymentMethodRepository paymentMethodRepository;

  @Override
  public PaymentMethod getPaymentMethodById(int methodId) {
    return paymentMethodRepository.findById(methodId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.PAYMENT_METHOD_NOT_FOUND));
  }

  @Override
  public List<PaymentMethod> getAllPaymentMethod() {
    return paymentMethodRepository.findAll();
  }
}
