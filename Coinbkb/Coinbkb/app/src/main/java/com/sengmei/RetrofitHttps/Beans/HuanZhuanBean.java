package com.sengmei.RetrofitHttps.Beans;

public class HuanZhuanBean {

    /**
     * type : ok
     * message : {"is_legal":1,"currency_name":"ETH","lock_legal_balance":"0.00000000","legal_cny_price":"0.00000","lock_change_balance":"0.00000000","legal_price":"0.00000","change_balance":"0.00000000","id":452710,"change_price":"0.00000","currencyId":2,"is_pick_up":1,"legal_balance":"0.00000000","cny_price":"0.00000","is_recharge":1}
     */

    private String type;
    private MessageBean message;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public static class MessageBean {
        /**
         * is_legal : 1
         * currency_name : ETH
         * lock_legal_balance : 0.00000000
         * legal_cny_price : 0.00000
         * lock_change_balance : 0.00000000
         * legal_price : 0.00000
         * change_balance : 0.00000000
         * id : 452710
         * change_price : 0.00000
         * currencyId : 2
         * is_pick_up : 1
         * legal_balance : 0.00000000
         * cny_price : 0.00000
         * is_recharge : 1
         */

        private int is_legal;
        private String currency_name;

        private String change_balance;
        private String legal_balance;

        private String lock_legal_balance;
        private String lock_change_balance;

        private String legal_cny_price;

        private String legal_price;
        private String change_price;

        private int id;
        private int currencyId;
        private int is_pick_up;
        private String cny_price;
        private int is_recharge;

        public int getIs_legal() {
            return is_legal;
        }

        public void setIs_legal(int is_legal) {
            this.is_legal = is_legal;
        }

        public String getCurrency_name() {
            return currency_name;
        }

        public void setCurrency_name(String currency_name) {
            this.currency_name = currency_name;
        }

        public String getLock_legal_balance() {
            return lock_legal_balance;
        }

        public void setLock_legal_balance(String lock_legal_balance) {
            this.lock_legal_balance = lock_legal_balance;
        }

        public String getLegal_cny_price() {
            return legal_cny_price;
        }

        public void setLegal_cny_price(String legal_cny_price) {
            this.legal_cny_price = legal_cny_price;
        }

        public String getLock_change_balance() {
            return lock_change_balance;
        }

        public void setLock_change_balance(String lock_change_balance) {
            this.lock_change_balance = lock_change_balance;
        }

        public String getLegal_price() {
            return legal_price;
        }

        public void setLegal_price(String legal_price) {
            this.legal_price = legal_price;
        }

        public String getChange_balance() {
            return change_balance;
        }

        public void setChange_balance(String change_balance) {
            this.change_balance = change_balance;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getChange_price() {
            return change_price;
        }

        public void setChange_price(String change_price) {
            this.change_price = change_price;
        }

        public int getCurrencyId() {
            return currencyId;
        }

        public void setCurrencyId(int currencyId) {
            this.currencyId = currencyId;
        }

        public int getIs_pick_up() {
            return is_pick_up;
        }

        public void setIs_pick_up(int is_pick_up) {
            this.is_pick_up = is_pick_up;
        }

        public String getLegal_balance() {
            return legal_balance;
        }

        public void setLegal_balance(String legal_balance) {
            this.legal_balance = legal_balance;
        }

        public String getCny_price() {
            return cny_price;
        }

        public void setCny_price(String cny_price) {
            this.cny_price = cny_price;
        }

        public int getIs_recharge() {
            return is_recharge;
        }

        public void setIs_recharge(int is_recharge) {
            this.is_recharge = is_recharge;
        }
    }
}
