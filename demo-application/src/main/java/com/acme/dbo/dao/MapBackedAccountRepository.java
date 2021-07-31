package com.acme.dbo.dao;

import com.acme.dbo.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class MapBackedAccountRepository implements AccountRepository {
    private static final Logger log = LoggerFactory.getLogger(MapBackedAccountRepository.class);
    private Map<Integer, Account> accounts ;

    public MapBackedAccountRepository(@Value("${accounts.repo.init-capacity}") int initialCapacity) {
        accounts = new HashMap<>(initialCapacity);
        log.debug("Created MapBackedAccountRepository with initial capacity {}", initialCapacity);
    }

    public void setAccounts(Map<Integer, Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public Account create(Account accountData) {
        if (accountData.getId() != 0 && accounts.containsKey(accountData.getId())) throw new IllegalStateException("account Data already exists");
        accounts.put(accountData.getId(), accountData);
        return accountData;
    }

    @Override
    public Account findById(Integer id) {
        return accounts.get(id);
    }

    @Override
    public Collection<Account> findAll() {
        return accounts.values();
    }
}
