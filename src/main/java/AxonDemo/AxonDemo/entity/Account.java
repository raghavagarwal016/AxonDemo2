package AxonDemo.AxonDemo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "account")
public class Account {

  @Id
  private String id;

  private double accountBalance;

  private String currency;

  private String status;
}
