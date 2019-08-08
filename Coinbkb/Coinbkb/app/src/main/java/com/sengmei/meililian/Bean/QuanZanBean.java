package com.sengmei.meililian.Bean;

import java.util.List;

public class QuanZanBean {
    /*
     * {"type":"market_trade",
     * "symbol":"ETH\/USDT",
     * "base-currency":"ETH",
     * "quote-currency":"USDT",
     * "currency_id":"2","currency_name":"ETH",
     * "legal_id":"3","legal_name":"USDT"
     * ,"data":"[{\"amount\":0.001,\"ts\":1554312019154,\"id\":\"10080266217028513234772\",\"price\":171.11,\"direction\":\"sell\"}]","content":""}*/
    private String type;
    private String symbol;
    private String currency_id;
    private String legal_id;
    private List<Databean> data;

    @Override
    public String toString() {
        return "QuanZanBean{" +
                "type='" + type + '\'' +
                ", symbol='" + symbol + '\'' +
                ", currency_id='" + currency_id + '\'' +
                ", legal_id='" + legal_id + '\'' +
                ", data=" + data +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(String currency_id) {
        this.currency_id = currency_id;
    }

    public String getLegal_id() {
        return legal_id;
    }

    public void setLegal_id(String legal_id) {
        this.legal_id = legal_id;
    }

    public List<Databean> getData() {
        return data;
    }

    public void setData(List<Databean> data) {
        this.data = data;
    }

    public static class   Databean{

        private String amount ;
        private String  ts;
        private String  price;
        private String  direction;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getTs() {
            return ts;
        }

        public void setTs(String ts) {
            this.ts = ts;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        @Override
        public String toString() {
            return "Databean{" +
                    "amount='" + amount + '\'' +
                    ", ts='" + ts + '\'' +
                    ", price='" + price + '\'' +
                    ", direction='" + direction + '\'' +
                    '}';
        }
    }

}
