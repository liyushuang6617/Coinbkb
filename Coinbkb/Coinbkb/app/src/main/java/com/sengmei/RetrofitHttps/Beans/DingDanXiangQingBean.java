package com.sengmei.RetrofitHttps.Beans;

public class DingDanXiangQingBean {
    private String type;
    private ObjectBean message;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ObjectBean getMessage() {
        return message;
    }

    public void setMessage(ObjectBean message) {
        this.message = message;
    }

    public static class ObjectBean {

        private String deal_money;//交易总额
        /*private String seller_name;//买家*/
        private String price;//单价
        private String number;//数量
        private String  create_time;//时间
        private String id;//参号
        private String is_sure;//判断
        private String type;//买卖家
        private String currency_name;//
        private String way_name;//
        private String ali_account;//
        private userBean user_cash_info;
        private String bank_address;//

        public String getBank_address() {
            return bank_address;
        }

        public void setBank_address(String bank_address) {
            this.bank_address = bank_address;
        }

        public String getAli_account() {
            return ali_account;
        }

        public void setAli_account(String ali_account) {
            this.ali_account = ali_account;
        }

        public String getDeal_money() {
            return deal_money;
        }

        public void setDeal_money(String deal_money) {
            this.deal_money = deal_money;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIs_sure() {
            return is_sure;
        }

        public void setIs_sure(String is_sure) {
            this.is_sure = is_sure;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCurrency_name() {
            return currency_name;
        }

        public void setCurrency_name(String currency_name) {
            this.currency_name = currency_name;
        }

        public String getWay_name() {
            return way_name;
        }

        public void setWay_name(String way_name) {
            this.way_name = way_name;
        }

        public userBean getUser_cash_info() {
            return user_cash_info;
        }

        public void setUser_cash_info(userBean user_cash_info) {
            this.user_cash_info = user_cash_info;
        }
    }
    public static class userBean {

        private String account_number;
        private String alipay_account;
        private String create_time;
        private String user_id;
        private String  wechat_nickname;
        private String bank_name;
        private String real_name;
        private String id;
        private String wechat_account;//
        private String bank_account;//

        public String getAccount_number() {
            return account_number;
        }

        public void setAccount_number(String account_number) {
            this.account_number = account_number;
        }

        public String getAlipay_account() {
            return alipay_account;
        }

        public void setAlipay_account(String alipay_account) {
            this.alipay_account = alipay_account;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getWechat_nickname() {
            return wechat_nickname;
        }

        public void setWechat_nickname(String wechat_nickname) {
            this.wechat_nickname = wechat_nickname;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWechat_account() {
            return wechat_account;
        }

        public void setWechat_account(String wechat_account) {
            this.wechat_account = wechat_account;
        }

        public String getBank_account() {
            return bank_account;
        }

        public void setBank_account(String bank_account) {
            this.bank_account = bank_account;
        }
    }
}
/**
 * {
 *     "type":"ok",
 *     "message":{
 *         "currency_name":"USDT",
 *         "create_time":"20:59 11/21",
 *         "hes_account":"1995123",
 *         "user_cash_info":{
 *             "account_number":"13243179585",
 *             "alipay_account":"1995123",
 *             "create_time":1542897466000,
 *             "user_id":20,
 *             "wechat_nickname":"自行车",
 *             "bank_name":"中国银行",
 *             "real_name":"刘",
 *             "id":20,
 *             "wechat_account":"zixingcar",
 *             "bank_account":"41302619951008"
 *         },
 *         "way_name":"支付宝",
 *         "type":"sell",
 *         "number":10,
 *         "update_time":null,
 *         "is_seller":"0",
 *         "is_sure":0,
 *         "price":"10.00000",
 *         "format_create_time":null,
 *         "id":391,
 *         "legal_deal_send_id":115,
 *         "deal_money":100
 *     }
 * }
* */