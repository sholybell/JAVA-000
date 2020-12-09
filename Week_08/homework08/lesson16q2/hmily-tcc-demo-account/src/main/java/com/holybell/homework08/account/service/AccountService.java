package com.holybell.homework08.account.service;

import com.holybell.homework08.account.dto.AccountDTO;

public interface AccountService {
    /**
     * 扣款支付.
     *
     * @param accountDTO 参数dto
     * @return true boolean
     */
    boolean payment(AccountDTO accountDTO);
}
