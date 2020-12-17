package com.holybell.common.dto;

import java.math.BigDecimal;

public class ExchangeDTO implements java.io.Serializable {

    private String fromAccountName;
    private String toAccountName;
    private String fromCurrencyType;
    private BigDecimal fromAmt;
    private String toCurrencyType;
    private BigDecimal toAmt;

    public String getFromAccountName() {
        return fromAccountName;
    }

    public void setFromAccountName(String fromAccountName) {
        this.fromAccountName = fromAccountName;
    }

    public String getToAccountName() {
        return toAccountName;
    }

    public void setToAccountName(String toAccountName) {
        this.toAccountName = toAccountName;
    }

    public String getFromCurrencyType() {
        return fromCurrencyType;
    }

    public void setFromCurrencyType(String fromCurrencyType) {
        this.fromCurrencyType = fromCurrencyType;
    }

    public BigDecimal getFromAmt() {
        return fromAmt;
    }

    public void setFromAmt(BigDecimal fromAmt) {
        this.fromAmt = fromAmt;
    }

    public String getToCurrencyType() {
        return toCurrencyType;
    }

    public void setToCurrencyType(String toCurrencyType) {
        this.toCurrencyType = toCurrencyType;
    }

    public BigDecimal getToAmt() {
        return toAmt;
    }

    public void setToAmt(BigDecimal toAmt) {
        this.toAmt = toAmt;
    }
}
