package AxonDemo.AxonDemo.service.impl;

import java.util.Optional;
import java.util.stream.Collectors;

import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AxonDemo.AxonDemo.dto.AccountResponse;
import AxonDemo.AxonDemo.entity.Account;
import AxonDemo.AxonDemo.repository.AccountRepository;
import AxonDemo.AxonDemo.service.AccountQueryService;
import java.util.List;

@Service
public class AccountQueryServiceImpl implements AccountQueryService {

 @Autowired
 private EventStore eventStore;

 @Autowired
 private AccountRepository accountRepository;

  @Override
  public List<Object> listEventsForAccount(String accountNumber) {
    return eventStore.readEvents(accountNumber).asStream().map( s -> s.getPayload()).collect(Collectors.toList());
  }

  @Override
  public List<AccountResponse> getAllAccounts() {
    List<Account> accounts = accountRepository.findAll();
    return accounts.stream().map(account -> {
      AccountResponse accountResponse = new AccountResponse();
      BeanUtils.copyProperties(account, accountResponse);
      return accountResponse;
    }).collect(Collectors.toList());
  }

  @Override
  public AccountResponse getAccountByAccountNumber(String accountNumber) {
    Optional<Account> account = accountRepository.findById(accountNumber);
    AccountResponse accountResponse = new AccountResponse();
    if (account.isPresent()) {
      BeanUtils.copyProperties(account.get(), accountResponse);
      return accountResponse;
    } else {
      return accountResponse;
    }
  }

}
