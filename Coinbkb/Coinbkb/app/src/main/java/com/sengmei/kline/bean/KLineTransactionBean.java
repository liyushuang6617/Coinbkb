package com.sengmei.kline.bean;

public class KLineTransactionBean {


    /**
     * currency_id : 32
     * legal_id : 2
     * number : 13500.0000000
     * price : 0.2918000
     * time : 2019-01-2114: 31: 47
     * way : 2
     */

    private int currency_id;
    private int legal_id;
    private String number;
    private String price;
    private String time;
    private int way;

    public int getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(int currency_id) {
        this.currency_id = currency_id;
    }

    public int getLegal_id() {
        return legal_id;
    }

    public void setLegal_id(int legal_id) {
        this.legal_id = legal_id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getWay() {
        return way;
    }

    public void setWay(int way) {
        this.way = way;
    }

    @Override
    public String toString() {
        return "KLineTransactionBean{" +
                "currency_id=" + currency_id +
                ", legal_id=" + legal_id +
                ", number='" + number + '\'' +
                ", price='" + price + '\'' +
                ", time='" + time + '\'' +
                ", way=" + way +
                '}';
    }
}
