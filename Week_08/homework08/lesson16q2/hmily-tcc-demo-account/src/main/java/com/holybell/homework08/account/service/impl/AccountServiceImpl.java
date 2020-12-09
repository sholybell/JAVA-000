
package com.holybell.homework08.account.service.impl;


import com.holybell.homework08.account.client.InventoryClient;
import com.holybell.homework08.account.dto.AccountDTO;
import com.holybell.homework08.account.mapper.AccountMapper;
import com.holybell.homework08.account.service.AccountService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    /**
     * logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private InventoryClient inventoryClient;

    @Override
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    public boolean payment(final AccountDTO accountDTO) {
        LOGGER.info("============执行try付款接口===============");
        accountMapper.update(accountDTO);
        return Boolean.TRUE;
    }

    /**
     * Confirm boolean.
     *
     * @param accountDTO the account dto
     * @return the boolean
     */
    public boolean confirm(final AccountDTO accountDTO) {
        LOGGER.info("============执行confirm 付款接口===============");
        return accountMapper.confirm(accountDTO) > 0;
    }

    /**
     * Cancel boolean.
     *
     * @param accountDTO the account dto
     * @return the boolean
     */
    public boolean cancel(final AccountDTO accountDTO) {
        LOGGER.info("============执行cancel 付款接口===============");
        return accountMapper.cancel(accountDTO) > 0;
    }
}
