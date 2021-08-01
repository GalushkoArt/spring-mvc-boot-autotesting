package com.acme.dbo;

import com.acme.dbo.config.TestConfig;
import com.acme.dbo.controller.AccountController;
import com.acme.dbo.controller.AccountNotFoundException;
import com.acme.dbo.dao.AccountRepository;
import com.acme.dbo.domain.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Collections;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * see {@link org.springframework.test.context.junit.jupiter.SpringJUnitConfig} annotation
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@TestPropertySource("classpath:application-test.properties")
public class AccountCrudIT {
    @Autowired private AccountRepository accountRepositoryStub;
    @Autowired private AccountController accountController;

    @Test
    public void shouldGetNoAccountsWhenNoCreated() {
        when(accountRepositoryStub.findAll()).thenReturn(
                Collections.emptyList());

        assertTrue(accountController.findAll().isEmpty());
    }

    @Test
    public void getAllNotEmptyRepoTest() {
        when(accountRepositoryStub.findAll()).thenReturn(
                asList(new Account(3, new BigDecimal("3.33")), new Account(2, new BigDecimal("3.33"))));

        assertTrue(accountController.findAll().containsAll(asList(
                new Account(3, new BigDecimal("3.33")),
                new Account(3, new BigDecimal("3.33")))));
    }

    @Test
    public void findByIdTest() {
        when(accountRepositoryStub.findById(5)).thenReturn(new Account(5, new BigDecimal("3.33")));

        assertEquals(accountController.findById(5), new Account(5, new BigDecimal("3.33")));
    }

    @Test
    public void findByIdNotExistAccountIdTest() {
        when(accountRepositoryStub.findById(3)).thenReturn(null);

        assertThrows(AccountNotFoundException.class, () -> accountController.findById(3));
    }

    @Test
    public void createTest() {
        when(accountRepositoryStub.create(new Account(1, new BigDecimal("255")))).thenReturn(new Account(1, new BigDecimal("255")));

        assertEquals(accountController.create(new Account(1, new BigDecimal("255"))), new Account(1, new BigDecimal("255")));
        verify(accountRepositoryStub).create(new Account(1, new BigDecimal("255")));
    }
}
