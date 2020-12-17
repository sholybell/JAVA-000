package io.kimmking.rpcfx.demo.provider.service;

import io.kimmking.rpcfx.demo.api.model.User;
import io.kimmking.rpcfx.demo.api.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public User findById(int id) {
        if (id == 1111) {
            throw new RuntimeException("查询用户异常!");
        }
        return new User(id, "KK" + System.currentTimeMillis());
    }
}
