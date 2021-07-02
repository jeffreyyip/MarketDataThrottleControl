package org.clsa;

import java.math.BigDecimal;

public class MarketData {
    private final String symbol;
    private final BigDecimal bid;
    private final BigDecimal ask;
    private final BigDecimal last;
    private final long updateTime;

    public MarketData(String symbol, BigDecimal bid, BigDecimal ask, BigDecimal last, long updateTime) {
        this.symbol = symbol;
        this.bid = bid;
        this.ask = ask;
        this.last = last;
        this.updateTime = updateTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public BigDecimal getLast() {
        return last;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    @Override
    public String toString() {
        return "MarketData{" +
                "symbol='" + symbol + '\'' +
                ", bid=" + bid +
                ", ask=" + ask +
                ", last=" + last +
                ", updateTime=" + updateTime +
                '}';
    }
}
