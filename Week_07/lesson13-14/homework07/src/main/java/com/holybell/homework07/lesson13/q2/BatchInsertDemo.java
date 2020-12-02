package com.holybell.homework07.lesson13.q2;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * 第13课程，第2题
 * 比较各种方式插入100W记录的效率
 */
public class BatchInsertDemo {

    private final static Logger logger = LoggerFactory.getLogger(BatchInsertDemo.class);

    /**
     * Statement + 多事务
     */
    public static void insertByStatement(int totalRecords) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            for (int i = 0; i < totalRecords; i++) {
                Statement statement = conn.createStatement();
                String sql = "INSERT INTO `t_order` ( `order_no`, `user_id`, `mobile`, `sku_id`, `sku_name`, `sku_icon`, `create_time`, `province`, `city`, `county`, `address`, `remark`, `price`, `is_paid`, `status`) VALUES ('1111', '1111', '1111', '1111', '1111', '1111', '2020-11-26 21:33:43', '1111', '1111', '1111', '1111', '1111', '1111', '0', '1');";
                statement.executeUpdate(sql);
            }
        } catch (Exception ex) {
            logger.warn("批量插入异常", ex);
        }
    }


    /**
     * Statement + 单事务
     */
    public static void insertByStatementInSignleTrx(int totalRecords) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            conn.setAutoCommit(false);
            for (int i = 0; i < totalRecords; i++) {
                Statement statement = conn.createStatement();
                String sql = "INSERT INTO `t_order` ( `order_no`, `user_id`, `mobile`, `sku_id`, `sku_name`, `sku_icon`, `create_time`, `province`, `city`, `county`, `address`, `remark`, `price`, `is_paid`, `status`) VALUES ('1111', '1111', '1111', '1111', '1111', '1111', '2020-11-26 21:33:43', '1111', '1111', '1111', '1111', '1111', '1111', '0', '1');";
                statement.executeUpdate(sql);
            }
            conn.commit();
        } catch (Exception ex) {
            logger.warn("批量插入异常", ex);
        }
    }

    /**
     * PreparedStatement + 单事务 + addBatch
     */
    public static void insertByPreparedStatementInSignleTrx(int totalRecords) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO `t_order` ( `order_no`, `user_id`, `mobile`, `sku_id`, `sku_name`, `sku_icon`, `create_time`, `province`, `city`, `county`, `address`, `remark`, `price`, `is_paid`, `status`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            for (int i = 0; i < totalRecords; i++) {
                preparedStatement.setString(1, "1111");
                preparedStatement.setString(2, "1111");
                preparedStatement.setString(3, "1111");
                preparedStatement.setString(4, "1111");
                preparedStatement.setString(5, "1111");
                preparedStatement.setString(6, "1111");
                preparedStatement.setString(7, "2020-11-26 21:33:43");
                preparedStatement.setString(8, "1111");
                preparedStatement.setString(9, "1111");
                preparedStatement.setString(10, "1111");
                preparedStatement.setString(11, "1111");
                preparedStatement.setString(12, "1111");
                preparedStatement.setString(13, "1111");
                preparedStatement.setString(14, "0");
                preparedStatement.setString(15, "1");
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            conn.commit();
        } catch (Exception ex) {
            logger.warn("批量插入异常", ex);
        }
    }

    /**
     * PreparedStatement + 单事务 + addBatch + Multiple Value
     *
     * @param batchSize 一次多少个value
     */
    public static void insertByPreparedStatementInSignleTrxAndMultipleValue(int totalRecords, int batchSize) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            conn.setAutoCommit(false);
//            String sql = "INSERT INTO `t_order` ( `order_no`, `user_id`, `mobile`, `sku_id`, `sku_name`, `sku_icon`, `create_time`, `province`, `city`, `county`, `address`, `remark`, `price`, `is_paid`, `status`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
//            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            // 分批次
            int loopTimes = totalRecords / batchSize;     // batchSize一次
            int lastTimes = totalRecords % batchSize;     // 剩下不满batchSize的次数

            PreparedStatement preparedStatement1 = null;
            PreparedStatement preparedStatement2 = null;

            if (loopTimes != 0) {
                StringBuilder sqlBuilder = new StringBuilder("INSERT INTO `t_order` ( `order_no`, `user_id`, `mobile`, `sku_id`, `sku_name`, `sku_icon`, `create_time`, `province`, `city`, `county`, `address`, `remark`, `price`, `is_paid`, `status`) VALUES");
                for (int i = 0; i < batchSize; i++) {
                    if (i == batchSize - 1) {
                        sqlBuilder.append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
                    } else {
                        sqlBuilder.append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?),");
                    }
                }
                preparedStatement1 = conn.prepareStatement(sqlBuilder.toString());

                for (int z = 0; z < loopTimes; z++) {
                    for (int j = 0; j < batchSize; j++) {
                        preparedStatement1.setString(j * 15 + 1, "1111");
                        preparedStatement1.setString(j * 15 + 2, "1111");
                        preparedStatement1.setString(j * 15 + 3, "1111");
                        preparedStatement1.setString(j * 15 + 4, "1111");
                        preparedStatement1.setString(j * 15 + 5, "1111");
                        preparedStatement1.setString(j * 15 + 6, "1111");
                        preparedStatement1.setString(j * 15 + 7, "2020-11-26 21:33:43");
                        preparedStatement1.setString(j * 15 + 8, "1111");
                        preparedStatement1.setString(j * 15 + 9, "1111");
                        preparedStatement1.setString(j * 15 + 10, "1111");
                        preparedStatement1.setString(j * 15 + 11, "1111");
                        preparedStatement1.setString(j * 15 + 12, "1111");
                        preparedStatement1.setString(j * 15 + 13, "1111");
                        preparedStatement1.setString(j * 15 + 14, "0");
                        preparedStatement1.setString(j * 15 + 15, "1");
                    }
                    preparedStatement1.addBatch();
                }
                preparedStatement1.executeBatch();
            }

            if (lastTimes != 0) {
                StringBuilder sqlBuilder = new StringBuilder("INSERT INTO `t_order` ( `order_no`, `user_id`, `mobile`, `sku_id`, `sku_name`, `sku_icon`, `create_time`, `province`, `city`, `county`, `address`, `remark`, `price`, `is_paid`, `status`) VALUES");
                for (int i = 0; i < lastTimes; i++) {
                    if (i == lastTimes - 1) {
                        sqlBuilder.append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
                    } else {
                        sqlBuilder.append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?),");
                    }
                }
                preparedStatement2 = conn.prepareStatement(sqlBuilder.toString());

                for (int j = 0; j < lastTimes; j++) {
                    preparedStatement2.setString(j * 15 + 1, "1111");
                    preparedStatement2.setString(j * 15 + 2, "1111");
                    preparedStatement2.setString(j * 15 + 3, "1111");
                    preparedStatement2.setString(j * 15 + 4, "1111");
                    preparedStatement2.setString(j * 15 + 5, "1111");
                    preparedStatement2.setString(j * 15 + 6, "1111");
                    preparedStatement2.setString(j * 15 + 7, "2020-11-26 21:33:43");
                    preparedStatement2.setString(j * 15 + 8, "1111");
                    preparedStatement2.setString(j * 15 + 9, "1111");
                    preparedStatement2.setString(j * 15 + 10, "1111");
                    preparedStatement2.setString(j * 15 + 11, "1111");
                    preparedStatement2.setString(j * 15 + 12, "1111");
                    preparedStatement2.setString(j * 15 + 13, "1111");
                    preparedStatement2.setString(j * 15 + 14, "0");
                    preparedStatement2.setString(j * 15 + 15, "1");
                }
                preparedStatement2.addBatch();
                preparedStatement2.executeBatch();
            }

            conn.commit();
        } catch (Exception ex) {
            logger.warn("批量插入异常", ex);
        }

    }

    /**
     * 结果:
     * StopWatch '': running time = 2636392208483 ns
     * ---------------------------------------------
     * ns         %     Task name
     * ---------------------------------------------
     * 2383962868596  090%  insertByStatementInSignleTrx                            39.7327145 min
     * 176253616168  007%  insertByPreparedStatementInSignleTrx                     2.9375603 min
     * 76175723719  003%  insertByPreparedStatementInSignleTrxAndMultipleValue      1.2695954 min
     */
    public static void main(String[] args) {
        int totalRecords = 1000000;
        StopWatch stopWatch = new StopWatch();

//        stopWatch.start("insertByStatement");      // 超级耗时,不参与统计
//        insertByStatement(totalRecords);
//        stopWatch.stop();

        stopWatch.start("insertByStatementInSignleTrx");
        insertByStatementInSignleTrx(totalRecords);
        stopWatch.stop();

        stopWatch.start("insertByPreparedStatementInSignleTrx");
        insertByPreparedStatementInSignleTrx(totalRecords);
        stopWatch.stop();

        stopWatch.start("insertByPreparedStatementInSignleTrxAndMultipleValue");
        insertByPreparedStatementInSignleTrxAndMultipleValue(totalRecords, 10000);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());        // 输出各个方法耗时占比
    }
}
