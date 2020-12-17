package com.holybell.common.model;

import java.math.BigDecimal;

public class Account {

    private int id;                 // 主键
    private String accountName;     // 账户名称 或做账户号使用
    private BigDecimal balance;     // 账户余额
    private BigDecimal freezeAmt;   // 当前账户冻结金额
    private String currencyType;    // 账户资金类型

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getFreezeAmt() {
        return freezeAmt;
    }

    public void setFreezeAmt(BigDecimal freezeAmt) {
        this.freezeAmt = freezeAmt;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }
}
