package com.sengmei.RetrofitHttps.Beans;

import java.util.List;

public class HangQingZiXuanBean {
    private String type;
    private List<MessageBean>  message;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<MessageBean> getMessage() {
        return message;
    }

    public void setMessage(List<MessageBean> message) {
        this.message = message;
    }

    public static class MessageBean {

        private int legalId;
        private int currencyId;

        public int getLegalId() {
            return legalId;
        }

        public void setLegalId(int legalId) {
            this.legalId = legalId;
        }

        public int getCurrencyId() {
            return currencyId;
        }

        public void setCurrencyId(int currencyId) {
            this.currencyId = currencyId;
        }
    }
}
