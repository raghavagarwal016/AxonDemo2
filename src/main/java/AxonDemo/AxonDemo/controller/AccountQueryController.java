package AxonDemo.AxonDemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AxonDemo.AxonDemo.dto.AccountResponse;
import AxonDemo.AxonDemo.service.AccountQueryService;

@RestController
@RequestMapping(value = "/bank-accounts")
public class AccountQueryController {

  @Autowired
  private AccountQueryService accountQueryService;

  @GetMapping("/{accountNumber}/events")
  public List<Object> listEventsForAccount(@PathVariable(value = "accountNumber") String accountNumber){
    return  accountQueryService.listEventsForAccount(accountNumber);
  }

  @GetMapping("/all/account")
  public List<AccountResponse> getAllAccounts() {
    return accountQueryService.getAllAccounts();
  }

  @GetMapping("/{accountNumber}/account")
  public AccountResponse getAccountByAccountNumber(@PathVariable(value = "accountNumber") String accountNumber) {
    return accountQueryService.getAccountByAccountNumber(accountNumber);
  }

}
