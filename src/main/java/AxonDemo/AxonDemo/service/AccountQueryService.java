package AxonDemo.AxonDemo.service;

import java.util.List;

import AxonDemo.AxonDemo.dto.AccountResponse;

public interface AccountQueryService {
  public List<Object> listEventsForAccount(String accountNumber);
  public List<AccountResponse> getAllAccounts();
  public AccountResponse getAccountByAccountNumber(String accountNumber);
}
