package AxonDemo.AxonDemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
  private String id;

  private double accountBalance;

  private String currency;

  private String status;
}
