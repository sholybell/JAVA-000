package com.holybell.homework05.lesson10.q6;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HikariDemo {

    public static DataSource dataSource = getDataSource();

    public static DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setMinimumIdle(5);
        config.setMaximumPoolSize(15);
        config.setAutoCommit(true);
        config.setIdleTimeout(3000);
        config.setPoolName("HikariCPPool");
        config.setMaxLifetime(1800000);
        config.setConnectionTimeout(30000);
        config.setConnectionTestQuery("SELECT 1");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/bluestore_core_db?characterEncoding=UTF8&serverTimezone=UTC");
        config.setUsername("root");
        config.setPassword("123456");
        return new HikariDataSource(config);
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
        try (Connection conn = dataSource.getConnection();
             Statement statement = conn.createStatement()) {
            //执行查询语句并返回结果集
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                //注意：这里要与数据库里的字段对应
                String username = resultSet.getString("user_name");
                String password = resultSet.getString("user_password");
                String age = resultSet.getString("user_age");
                System.out.println(username + " " + password + " " + age);
            }
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
        try (Connection conn = dataSource.getConnection();
             Statement statement = conn.createStatement()) {
            statement.execute(sql);
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
