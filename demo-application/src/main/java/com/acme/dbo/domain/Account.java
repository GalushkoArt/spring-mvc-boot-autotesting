package com.acme.dbo.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {
    private int id;
    private BigDecimal amount;

    public Account(int id, BigDecimal amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Account " + getId() + " : " + getAmount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && Objects.equals(amount, account.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount);
    }
}
