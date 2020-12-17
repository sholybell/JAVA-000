package com.holybell.foreignexchangea.service;

import com.holybell.common.dto.ExchangeDTO;
import com.holybell.common.mapper.AccountMapper;
import com.holybell.common.model.Account;
import com.holybell.common.service.AccountService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service("accountAService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired(required = false)
    @Qualifier("accountBService")
    private AccountService accountBService;

    @Override
    @Transactional
    public int save(Account account) {
        return accountMapper.save(account);
    }

    @Override
    @Transactional
    @HmilyTCC(confirmMethod = "confirmFromExchange", cancelMethod = "cancelFromExchange")
    public void fromExchange(ExchangeDTO dto) {
        // 先冻结本地要用于兑换币种的账户
        freeze(dto.getFromAccountName(), dto.getFromCurrencyType(), dto.getFromAmt());
        accountBService.toExchange(dto);
    }

    /**
     * 冻结部分资金
     */
    @Override
    @Transactional
    public int freeze(String accountName, String currencyType, BigDecimal amt) {
        return accountMapper.freeze(accountName, currencyType, amt);
    }

    /**
     * 将冻结的资金转移到交易的币种
     */
    public void confirmFromExchange(ExchangeDTO dto) {
        // 移除被冻结的金额
        removeFreezeAmt(dto.getFromAccountName(), dto.getFromCurrencyType(), dto.getFromAmt());
        // 将被冻结的金额转换为其他币种的数值，并加到该币种账户的余额
        addBalance(dto.getFromAccountName(), dto.getToCurrencyType(), dto.getToAmt());
    }


    /**
     * 清除被冻结的资金
     */
    @Transactional
    @Override
    public int removeFreezeAmt(String accountName, String currencyType, BigDecimal amt) {
        return accountMapper.removeFreezeAmt(accountName, currencyType, amt);
    }

    @Transactional
    @Override
    public int addBalance(String accountName, String currencyType, BigDecimal amt) {
        return accountMapper.addBalance(accountName, currencyType, amt);
    }

    /**
     * 将账户某币种被冻结的资金加回余额
     */
    public void cancelFromExchange(ExchangeDTO dto) {
        // 移除被冻结的金额
        removeFreezeAmt(dto.getFromAccountName(), dto.getFromCurrencyType(), dto.getFromAmt());
        // 将被冻结的金额加回原来的余额
        addBalance(dto.getFromAccountName(), dto.getFromCurrencyType(), dto.getFromAmt());
    }

}
