package com.holybell.homework08.mapper;

import com.holybell.homework08.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 删除全部记录
     */
    long deleteAll();

    Long countAll();

    User select(@Param("id") Long id, @Param("age") Integer age);

    List<User> selectAgeRangeLimit(@Param("age") Integer age, @Param("limit") Integer limit);

    void updateById(@Param("user") User user, @Param("id") Integer id);

    void deleteById(Integer id);
}
