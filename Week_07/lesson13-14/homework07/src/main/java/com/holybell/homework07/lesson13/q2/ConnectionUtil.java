package com.holybell.homework07.lesson13.q2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 获取数据库连接工具类
 */
public class ConnectionUtil {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionUtil.class);

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if (logger.isDebugEnabled()) {
                logger.debug("数据库驱动加载成功......");
            }
            return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tx?characterEncoding=UTF-8&serverTimezone=UTC", "root", "123456");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(ConnectionUtil.getConnection());
    }
}
