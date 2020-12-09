
package com.holybell.homework08.order.service.impl;

import com.holybell.homework08.order.entity.Order;
import com.holybell.homework08.order.enums.OrderStatusEnum;
import com.holybell.homework08.order.mapper.OrderMapper;
import com.holybell.homework08.order.service.OrderService;
import com.holybell.homework08.order.service.PaymentService;
import org.dromara.hmily.common.utils.IdWorkerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private PaymentService paymentService;

    @Override
    public String mockInventoryWithTryException(Integer count, BigDecimal amount) {
        Order order = saveOrder(count, amount);
        return paymentService.mockPaymentInventoryWithTryException(order);
    }

    private Order saveOrder(Integer count, BigDecimal amount) {
        final Order order = buildOrder(count, amount);
        orderMapper.save(order);
        return order;
    }

    private Order buildOrder(Integer count, BigDecimal amount) {
        LOGGER.debug("构建订单对象");
        Order order = new Order();
        order.setCreateTime(new Date());
        order.setNumber(String.valueOf(IdWorkerUtils.getInstance().createUUID()));
        //demo中的表里只有商品id为 1的数据
        order.setProductId("1");
        order.setStatus(OrderStatusEnum.NOT_PAY.getCode());
        order.setTotalAmount(amount);
        order.setCount(count);
        //demo中 表里面存的用户id为10000
        order.setUserId("10000");
        return order;
    }
}
