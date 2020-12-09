
package com.holybell.homework08.account.controller;

import com.holybell.homework08.account.dto.AccountDTO;
import com.holybell.homework08.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/payment")
    public Boolean payment(@RequestBody AccountDTO accountDO) {
        return accountService.payment(accountDO);
    }
}
