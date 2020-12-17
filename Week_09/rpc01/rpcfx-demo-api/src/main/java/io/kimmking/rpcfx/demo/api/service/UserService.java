package io.kimmking.rpcfx.demo.api.service;

import io.kimmking.rpcfx.demo.api.model.User;

public interface UserService {

    User findById(int id);

}
