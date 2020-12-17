package com.holybell.foreignexchangea.controller;

import com.holybell.common.dto.ExchangeDTO;
import com.holybell.common.model.Account;
import com.holybell.common.service.AccountService;
import com.holybell.common.util.CurrencyExchangeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class AccountController {

    @Autowired
    @Qualifier("accountAService")
    private AccountService accountAService;

//    @GetMapping("save")
//    public void save() {
//        Account account = new Account();
//        account.setBalance(BigDecimal.valueOf(100));
//        account.setAccountName("a");
//        account.setCurrencyType("rmb");
//        account.setFreezeAmt(BigDecimal.ZERO);
//        accountAService.save(account);
//
//        account = new Account();
//        account.setBalance(BigDecimal.valueOf(100));
//        account.setAccountName("a");
//        account.setCurrencyType("us");
//        account.setFreezeAmt(BigDecimal.ZERO);
//        accountAService.save(account);
//    }

    /**
     * 外汇交易
     */
    @PostMapping("exchange")
    public void exchange(ExchangeDTO dto) {
        dto.setToAmt(CurrencyExchangeUtil.exchange(dto.getFromCurrencyType(), dto.getFromAmt(), dto.getToCurrencyType()));
        accountAService.fromExchange(dto);
    }
}
