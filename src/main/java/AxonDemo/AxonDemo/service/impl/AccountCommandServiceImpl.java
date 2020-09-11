package AxonDemo.AxonDemo.service.impl;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import AxonDemo.AxonDemo.command.CreateAccountCommand;
import AxonDemo.AxonDemo.command.CreditMoneyCommand;
import AxonDemo.AxonDemo.command.DebitMoneyCommand;
import AxonDemo.AxonDemo.constants.Status;
import AxonDemo.AxonDemo.dto.AccountCreateDTO;
import AxonDemo.AxonDemo.dto.MoneyCreditDTO;
import AxonDemo.AxonDemo.dto.MoneyDebitDTO;
import AxonDemo.AxonDemo.entity.Account;
import AxonDemo.AxonDemo.repository.AccountRepository;
import AxonDemo.AxonDemo.service.AccountCommandService;

@Service
public class AccountCommandServiceImpl implements AccountCommandService {

  @Autowired
  private CommandGateway commandGateway;

  @Autowired
  private AccountRepository accountRepository;

  @Override
  public CompletableFuture<String> createAccount(AccountCreateDTO accountCreateDTO) {
    String id = UUID.randomUUID().toString();
    accountRepository.save(createAccountEntity(accountCreateDTO, id));
    return commandGateway.send(
        new CreateAccountCommand(id, accountCreateDTO.getStartingBalance(),
            accountCreateDTO.getCurrency()));
  }

  @Override
  public CompletableFuture<String> creditMoneyToAccount(String accountNumber, MoneyCreditDTO moneyCreditDTO) {
    upadateAccount(accountNumber, moneyCreditDTO.getCreditAmount());
    return commandGateway
        .send(new CreditMoneyCommand(accountNumber, moneyCreditDTO.getCreditAmount(), moneyCreditDTO.getCurrency()));
  }

  @Override
  public CompletableFuture<String> debitMoneyFromAccount(String accountNumber, MoneyDebitDTO moneyDebitDTO) {
    upadateAccount(accountNumber, moneyDebitDTO.getDebitAmount() * -1);
    return commandGateway
        .send(new DebitMoneyCommand(accountNumber, moneyDebitDTO.getDebitAmount(), moneyDebitDTO.getCurrency()));
  }

  private Account createAccountEntity(AccountCreateDTO accountCreateDTO, String id) {
    Account account = new Account();
    account.setId(id);
    account.setAccountBalance(accountCreateDTO.getStartingBalance());
    account.setCurrency(accountCreateDTO.getCurrency());
    account.setStatus(Status.ACTIVATED.getStatus());
    return account;
  }

  @Transactional(rollbackFor = Exception.class)
  private Account upadateAccount(String id, double amount) {
    Account account = accountRepository.getOne(id);
    account.setAccountBalance(account.getAccountBalance() + amount);
    if (amount < 0 && account.getAccountBalance() < 0) {
      account.setStatus(Status.HOLD.getStatus());
    }
    else if (amount > 0 && account.getAccountBalance() >= 0){
      account.setStatus(Status.ACTIVATED.getStatus());
    }
    return accountRepository.save(account);
  }

}
