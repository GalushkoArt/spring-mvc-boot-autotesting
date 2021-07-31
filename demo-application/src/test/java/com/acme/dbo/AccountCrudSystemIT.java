package com.acme.dbo;

import com.acme.dbo.config.Config;
import com.acme.dbo.controller.AccountController;
import com.acme.dbo.controller.AccountNotFoundException;
import com.acme.dbo.dao.MapBackedAccountRepository;
import com.acme.dbo.domain.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * see {@link org.springframework.test.context.junit.jupiter.SpringJUnitConfig} annotation
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
public class AccountCrudSystemIT {
    @Autowired private AccountController accountController;
    @Autowired private MapBackedAccountRepository repository;

    @Test
    public void shouldGetNoAccountsWhenNoCreated() {
        assertTrue(accountController.findAll().isEmpty());
    }

    @Test
    public void findAllReturnsListTest() {
        repository.create(new Account(53242, BigDecimal.valueOf(55597)));
        repository.create(new Account(53243, BigDecimal.valueOf(55597)));

        assertTrue(accountController.findAll().containsAll(List.of(new Account(53242, BigDecimal.valueOf(55597)), new Account(53243, BigDecimal.valueOf(55597)))));

        repository.setAccounts(new HashMap<>(10));
    }

    @Test
    public void findByIdTest() {
        repository.create(new Account(53242, BigDecimal.valueOf(55597)));

        assertEquals(accountController.findById(53242), new Account(53242, BigDecimal.valueOf(55597)));

        repository.setAccounts(new HashMap<>(10));
    }

    @Test
    public void findByIdThrowsExceptionTest() {
        assertThrows(AccountNotFoundException.class , () -> accountController.findById(53242));
    }

    @Test
    public void createTest() {
        accountController.create(new Account(1245, BigDecimal.valueOf(255)));

        assertEquals(new Account(1245, BigDecimal.valueOf(255)), repository.findById(1245));
        repository.setAccounts(new HashMap<>(10));
    }

    @Test
    public void createExistAccountTest() {
        repository.create(new Account(1245, BigDecimal.valueOf(55597)));

        assertThrows(IllegalStateException.class , () -> accountController.create(new Account(1245, BigDecimal.valueOf(255))));
        repository.setAccounts(new HashMap<>(10));
    }

    @Test
    public void createNullTest() {
        assertThrows(NullPointerException.class , () -> accountController.create(null));
    }
}
