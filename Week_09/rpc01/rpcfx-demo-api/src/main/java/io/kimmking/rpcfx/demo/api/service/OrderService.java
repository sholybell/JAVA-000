package io.kimmking.rpcfx.demo.api.service;

import io.kimmking.rpcfx.demo.api.model.Order;

public interface OrderService {

    Order findOrderById(int id);

}
