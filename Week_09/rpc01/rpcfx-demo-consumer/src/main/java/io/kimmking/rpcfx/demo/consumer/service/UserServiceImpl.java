package io.kimmking.rpcfx.demo.consumer.service;

import io.kimmking.rpcfx.annotations.RpcClient;
import io.kimmking.rpcfx.demo.api.model.User;
import io.kimmking.rpcfx.demo.api.service.UserService;
import org.springframework.stereotype.Component;

@Component
@RpcClient(url = "http://localhost:8080/")
public class UserServiceImpl implements UserService {

    /**
     * 可以作为降级方法
     */
    @Override
    public User findById(int id) {
        return new User();
    }
}
