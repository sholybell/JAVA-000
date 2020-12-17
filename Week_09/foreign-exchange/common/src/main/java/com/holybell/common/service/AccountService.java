package com.holybell.common.service;


import com.holybell.common.dto.ExchangeDTO;
import com.holybell.common.model.Account;

import java.math.BigDecimal;

public interface AccountService {

    /**
     * 测试mybatis联通数据库使用
     */
    int save(Account account);

    /**
     * 主动外汇交易
     */
    default void fromExchange(ExchangeDTO exchangeDTO) {
    }

    /**
     * 被动外汇交易
     */
    default void toExchange(ExchangeDTO exchangeDTO) {
    }

    /**
     * 冻结资金
     */
    int freeze(String accountName, String currencyType, BigDecimal amt);

    /**
     * 从账户中清除冻结的资金
     */
    int removeFreezeAmt(String accountName, String currencyType, BigDecimal amt);

    /**
     * 增加余额
     */
    int addBalance(String accountName, String currencyType, BigDecimal amt);
}
