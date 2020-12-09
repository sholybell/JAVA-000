
package com.holybell.homework08.order.service;


import java.math.BigDecimal;

public interface OrderService {

    /**
     * 模拟在订单支付操作中，库存在try阶段中的库存异常.
     *
     * @param count  购买数量
     * @param amount 支付金额
     * @return string string
     */
    String mockInventoryWithTryException(Integer count, BigDecimal amount);
}
