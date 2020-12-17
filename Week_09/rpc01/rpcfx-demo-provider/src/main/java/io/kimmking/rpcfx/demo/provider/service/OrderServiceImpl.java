package io.kimmking.rpcfx.demo.provider.service;

import io.kimmking.rpcfx.demo.api.model.Order;
import io.kimmking.rpcfx.demo.api.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public Order findOrderById(int id) {
        if (id == 1111) {
            throw new RuntimeException("查询订单异常!");
        }
        return new Order(id, "Cuijing" + System.currentTimeMillis(), 9.9f);
    }
}
