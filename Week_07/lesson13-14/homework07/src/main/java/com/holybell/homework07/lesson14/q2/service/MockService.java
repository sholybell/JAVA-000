package com.holybell.homework07.lesson14.q2.service;

import com.holybell.homework07.lesson14.q2.annotations.ReadOnly;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Service
public class MockService {

    private final Logger loggger = LoggerFactory.getLogger(MockService.class);

    @Autowired
    private DataSource dataSource;

    /**
     * 模拟从从库读取
     */
    @ReadOnly
    public void queryFromSlave() {
        query();
    }

    public void queryFromMaster() {
        query();
    }


    /**
     * 查找数据
     */
    public void query() {
        String sql = "SELECT * FROM USER_INFO ORDER BY ID DESC LIMIT 1";
        try (Connection conn = dataSource.getConnection();
             Statement statement = conn.createStatement()) {
            //执行查询语句并返回结果集
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                //注意：这里要与数据库里的字段对应
                String username = resultSet.getString("nickname");
                String mobile = resultSet.getString("mobile");
                String zoneCode = resultSet.getString("zone_code");
                loggger.info("数据库查询结果 : {} {} {}", username, mobile, zoneCode);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
