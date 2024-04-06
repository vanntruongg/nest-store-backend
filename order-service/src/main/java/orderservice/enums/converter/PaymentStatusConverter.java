package orderservice.enums.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import orderservice.enums.PaymentStatus;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class PaymentStatusConverter implements AttributeConverter<PaymentStatus, String> {
  @Override
  public String convertToDatabaseColumn(PaymentStatus status) {
    return status != null ? status.getPaymentStatus() : null;
  }

  @Override
  public PaymentStatus convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }

    return Stream.of(PaymentStatus.values())
            .filter(status -> status.getPaymentStatus().equals(dbData))
            .findFirst()
            .orElseThrow(IllegalAccessError::new);
  }
}
