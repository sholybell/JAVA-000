
package com.holybell.homework08.order.service.impl;

import com.holybell.homework08.order.client.AccountClient;
import com.holybell.homework08.order.client.InventoryClient;
import com.holybell.homework08.order.dto.AccountDTO;
import com.holybell.homework08.order.dto.InventoryDTO;
import com.holybell.homework08.order.entity.Order;
import com.holybell.homework08.order.enums.OrderStatusEnum;
import com.holybell.homework08.order.mapper.OrderMapper;
import com.holybell.homework08.order.service.PaymentService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private AccountClient accountClient;
    @Autowired
    private InventoryClient inventoryClient;

    @Override
    @HmilyTCC(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    public String mockPaymentInventoryWithTryException(Order order) {
        LOGGER.debug("===========执行springcloud  mockPaymentInventoryWithTryException 扣减资金接口==========");
        updateOrderStatus(order, OrderStatusEnum.PAYING);
        //扣除用户余额
        accountClient.payment(buildAccountDTO(order));
        inventoryClient.mockWithTryException(buildInventoryDTO(order));
        return "success";
    }

    public void confirmOrderStatus(Order order) {
        updateOrderStatus(order, OrderStatusEnum.PAY_SUCCESS);
        LOGGER.info("=========进行订单confirm操作完成================");
    }

    public void cancelOrderStatus(Order order) {
        updateOrderStatus(order, OrderStatusEnum.PAY_FAIL);
        LOGGER.info("=========进行订单cancel操作完成================");
    }

    private void updateOrderStatus(Order order, OrderStatusEnum orderStatus) {
        order.setStatus(orderStatus.getCode());
        orderMapper.update(order);
    }

    private AccountDTO buildAccountDTO(Order order) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAmount(order.getTotalAmount());
        accountDTO.setUserId(order.getUserId());
        return accountDTO;
    }

    private InventoryDTO buildInventoryDTO(Order order) {
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setCount(order.getCount());
        inventoryDTO.setProductId(order.getProductId());
        return inventoryDTO;
    }
}