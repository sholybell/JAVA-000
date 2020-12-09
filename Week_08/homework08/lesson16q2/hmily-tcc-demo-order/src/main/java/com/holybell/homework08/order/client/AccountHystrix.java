
package com.holybell.homework08.order.client;

import com.holybell.homework08.order.dto.AccountDTO;
import org.springframework.stereotype.Component;

@Component
public class AccountHystrix implements AccountClient {

    @Override
    public Boolean payment(AccountDTO accountDO) {
        System.out.println("执行断路器。。" + accountDO.toString());
        return false;
    }
}
