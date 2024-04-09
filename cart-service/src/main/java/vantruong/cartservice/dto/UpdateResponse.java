package vantruong.cartservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateResponse {
  private int newQuantity;
}
