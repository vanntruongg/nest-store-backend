package vantruong.nststore.enums.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import vantruong.nststore.enums.OrderStatus;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {
  @Override
  public String convertToDatabaseColumn(OrderStatus status) {
    return status != null ? status.getOrderStatus() : null;
  }

  @Override
  public OrderStatus convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }

    return Stream.of(OrderStatus.values())
            .filter(status -> status.getOrderStatus().equals(dbData))
            .findFirst()
            .orElseThrow(IllegalAccessError::new);
  }
}
