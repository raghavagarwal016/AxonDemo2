package AxonDemo.AxonDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import AxonDemo.AxonDemo.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String> {

}
