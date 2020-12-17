package com.holybell.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyExchangeUtil {

    private CurrencyExchangeUtil() {

    }

    /**
     * 汇率换算
     */
    public static BigDecimal exchange(String fromCurrencyType, BigDecimal fromAmt, String toCurrencyType) {
        String key = fromCurrencyType + "_2_" + toCurrencyType;
        switch (key) {
            case "us_2_rmb": // 美元换人民币
                return fromAmt.multiply(BigDecimal.valueOf(7)).setScale(2, RoundingMode.HALF_UP);
            case "rmb_2_us": // 人民币换美元
                return fromAmt.divide(BigDecimal.valueOf(7), RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
            default:
                return BigDecimal.ZERO;
        }
    }
}
