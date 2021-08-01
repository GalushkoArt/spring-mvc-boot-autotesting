package com.acme.dbo.controller;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(Integer id) {
        super(id.toString());
    }
}
