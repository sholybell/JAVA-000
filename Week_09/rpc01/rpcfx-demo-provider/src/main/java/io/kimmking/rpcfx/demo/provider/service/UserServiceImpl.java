package io.kimmking.rpcfx.demo.provider.service;

import io.kimmking.rpcfx.annotations.RpcServer;
import io.kimmking.rpcfx.demo.api.model.User;
import io.kimmking.rpcfx.demo.api.service.UserService;

/**
 * 作为一个RPC的服务端接口实现
 */
@RpcServer(value = "userService")
public class UserServiceImpl implements UserService {

    @Override
    public User findById(int id) {
        if (id == 1111) {
            throw new RuntimeException("查询用户异常!");
        }
        return new User(id, "KK" + System.currentTimeMillis());
    }
}
