package com.holybell.foreignexchangeb.service;

import com.holybell.common.dto.ExchangeDTO;
import com.holybell.common.mapper.AccountMapper;
import com.holybell.common.model.Account;
import com.holybell.common.service.AccountService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service("accountBService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    @Transactional
    public int save(Account account) {
        return accountMapper.save(account);
    }

    /**
     * 被动外汇交易
     */
    @Override
    @Transactional
    @HmilyTCC(confirmMethod = "confirmToExchange", cancelMethod = "cancelToExchange")
    public void toExchange(ExchangeDTO exchangeDTO) {
        // 先冻结部分资金
        freeze(exchangeDTO.getToAccountName(), exchangeDTO.getToCurrencyType(), exchangeDTO.getToAmt());
    }

    /**
     * 冻结一部分资金
     */
    @Override
    @Transactional
    public int freeze(String accountName, String currencyType, BigDecimal amt) {
        return accountMapper.freeze(accountName, currencyType, amt);
    }

    /**
     * 确认被动交易
     */
    public void confirmToExchange(ExchangeDTO exchangeDTO) {
        // 从被交易的币种账户中移除冻结资金
        removeFreezeAmt(exchangeDTO.getToAccountName(), exchangeDTO.getToCurrencyType(), exchangeDTO.getToAmt());
        // 将冻结资金添加到其他币种账户
        addBalance(exchangeDTO.getToAccountName(), exchangeDTO.getFromCurrencyType(), exchangeDTO.getFromAmt());
    }

    @Override
    @Transactional
    public int removeFreezeAmt(String accountName, String currencyType, BigDecimal amt) {
        return accountMapper.removeFreezeAmt(accountName, currencyType, amt);
    }

    @Override
    @Transactional
    public int addBalance(String accountName, String currencyType, BigDecimal amt) {
        return accountMapper.addBalance(accountName, currencyType, amt);
    }

    /**
     * 取消被动交易
     */
    public void cancelToExchange(ExchangeDTO exchangeDTO) {
        // 从被交易的币种账户中移除冻结资金
        removeFreezeAmt(exchangeDTO.getToAccountName(), exchangeDTO.getToCurrencyType(), exchangeDTO.getToAmt());
        // 将冻结资金添加到会该币种的余额
        addBalance(exchangeDTO.getToAccountName(), exchangeDTO.getToCurrencyType(), exchangeDTO.getToAmt());
    }
}
