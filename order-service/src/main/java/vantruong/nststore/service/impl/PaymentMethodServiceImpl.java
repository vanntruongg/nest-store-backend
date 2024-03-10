package vantruong.nststore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vantruong.nststore.constant.MessageConstant;
import vantruong.nststore.entity.PaymentMethod;
import vantruong.nststore.exception.ErrorCode;
import vantruong.nststore.exception.NotFoundException;
import vantruong.nststore.repository.PaymentMethodRepository;
import vantruong.nststore.service.PaymentMethodService;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {
  private final PaymentMethodRepository paymentMethodRepository;

  @Override
  public PaymentMethod getPaymentMethodById(int methodId) {
    return paymentMethodRepository.findById(methodId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.PAYMENT_METHOD_NOT_FOUND));
  }
}
