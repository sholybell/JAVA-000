package com.holybell.homework05.lesson10.q6;

import java.sql.*;

public class JDBCPrepareStatementDemo {

    public static final String LOCAL_URL = "jdbc:mysql://localhost:3306/bluestore_core_db?characterEncoding=UTF8&serverTimezone=UTC";
    public static final String LOCAL_USER = "root";
    public static final String LOCAL_PASSWORD = "123456";

    /**
     * 获取一个数据库链接
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(LOCAL_URL, LOCAL_USER, LOCAL_PASSWORD);
    }

    /**
     * 创建表
     */
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS `user`("
                + "`id` INT UNSIGNED AUTO_INCREMENT,"
                + " `user_name` VARCHAR(100),"
                + " `user_password` VARCHAR(100),"
                + " `user_age` INT(11),"
                + "PRIMARY KEY ( `id` )"
                + ")ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        doUpdate(sql);
    }

    /**
     * 添加表记录
     */
    public static void add() {
        String sql = "INSERT INTO USER (user_name,user_password,user_age) VALUES('老王','123456',18)";
        doUpdate(sql);
    }

    /**
     * 删除表记录
     */
    public static void delete() {
        String sql = "delete from USER";
        doUpdate(sql);
    }

    /**
     * 查找数据
     */
    public static void query() {
        String sql = "SELECT * FROM USER";
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            //执行查询语句并返回结果集
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                //注意：这里要与数据库里的字段对应
                String username = resultSet.getString("user_name");
                String password = resultSet.getString("user_password");
                String age = resultSet.getString("user_age");
                System.out.println(username + " " + password + " " + age);
            }
            conn.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 修改数据
     */
    public static void updata() {
        String sql = "UPDATE USER SET USER.user_name = '老李'";
        doUpdate(sql);
    }


    /**
     * 执行变更操作
     *
     * @param sql
     */
    private static void doUpdate(String sql) {
        try (Connection conn = getConnection()) {
            //开启事务
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate();
            //提交事务
            conn.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createTable();
        add();
        query();
        updata();
        query();
        delete();
        query();
    }
}
