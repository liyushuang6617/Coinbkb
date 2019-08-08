package com.sengmei.kline.bean;

public class KLineBean {


    /**
     * close : 30.580000000000000000
     * high : 30.580000000000000000
     * kline_type : 1min
     * low : 30.560000000000000000
     * open : 30.580000000000000000
     * sybmol : LTC/USDT
     * time : 1547709660000
     * type : kline
     * volume : 91.841700000000000000
     * content :
     */

    private String close;
    private String high;
    private String period;
    private String low;
    private String open;
    private String symbol;
    private long time;
    private String volume;
    private String content;

    @Override
    public String toString() {
        return "KLineBean{" +
                "close='" + close + '\'' +
                ", high='" + high + '\'' +
                ", period='" + period + '\'' +
                ", low='" + low + '\'' +
                ", open='" + open + '\'' +
                ", symbol='" + symbol + '\'' +
                ", time=" + time +
                ", volume='" + volume + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
