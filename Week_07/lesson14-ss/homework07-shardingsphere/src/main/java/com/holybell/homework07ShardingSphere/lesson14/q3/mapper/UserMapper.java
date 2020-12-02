package com.holybell.homework07ShardingSphere.lesson14.q3.mapper;

import com.holybell.homework07ShardingSphere.lesson14.q3.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * add a new user
     *
     * @param model
     * @return
     */
    Integer insert(User model);

    /**
     * select all users
     *
     * @return
     */
    List<User> selectAll();
}
