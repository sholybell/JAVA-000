
package com.holybell.homework08.order.service;


import com.holybell.homework08.order.entity.Order;

public interface PaymentService {

    /**
     * mock订单支付的时候库存异常.
     *
     * @param order 订单实体
     * @return String string
     */
    String mockPaymentInventoryWithTryException(Order order);
}
