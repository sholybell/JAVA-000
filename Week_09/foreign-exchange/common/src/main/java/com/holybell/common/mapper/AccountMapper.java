package com.holybell.common.mapper;

import com.holybell.common.model.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

@Mapper
public interface AccountMapper {
    /**
     * 测试数据库联通使用
     */
    @Insert("insert into account(account_name,balance,freeze_amt,currency_type) values(#{accountName},#{balance},#{freezeAmt},#{currencyType})")
    int save(Account account);

    /**
     * 外汇交易前尝试冻结一部分本地资金
     */
    @Update("update account set balance = balance - #{amt}, freeze_amt = freeze_amt + #{amt} " +
            "where account_name=#{accountName} and currency_type=#{currencyType} and (balance - #{amt})>=0")
    int freeze(@Param("accountName") String accountName, @Param("currencyType") String currencyType, @Param("amt") BigDecimal amt);

    /**
     * 移除冻结的资金
     */
    @Update("update account set freeze_amt = freeze_amt - #{amt} " +
            "where account_name=#{accountName} and currency_type=#{currencyType} and (freeze_amt - #{amt})>=0")
    int removeFreezeAmt(@Param("accountName") String accountName, @Param("currencyType") String currencyType, @Param("amt") BigDecimal amt);

    /**
     * 增加某账户某币种账户余额
     */
    @Update("update account set balance = balance + #{amt} where account_name = #{accountName} and currency_type = #{currencyType}")
    int addBalance(@Param("accountName") String accountName, @Param("currencyType") String currencyType, @Param("amt") BigDecimal amt);
}
