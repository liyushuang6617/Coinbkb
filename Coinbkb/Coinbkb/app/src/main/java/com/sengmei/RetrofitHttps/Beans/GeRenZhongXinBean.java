package com.sengmei.RetrofitHttps.Beans;

import java.io.Serializable;

/**
 * Created by chengwenlong on 2016/10/21.
 */

public class GeRenZhongXinBean implements Serializable {


    private ObjectBean object;
    private String type;
    private ObjectBean message;

    public ObjectBean getMessage() {
        return message;
    }

    public void setMessage(ObjectBean message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public ObjectBean getObject() {
        return object;
    }

    public void setObject(ObjectBean object) {
        this.object = object;
    }

    public static class ObjectBean {
        private String account;
        private String id;
        private String review_status;
        private String is_seller;
        private String extension_code;

        private String name;
        private String card_id;

        public String getExtension_code() {
            return extension_code;
        }

        public void setExtension_code(String extension_code) {
            this.extension_code = extension_code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCard_id() {
            return card_id;
        }

        public void setCard_id(String card_id) {
            this.card_id = card_id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getReview_status() {
            return review_status;
        }

        public void setReview_status(String review_status) {
            this.review_status = review_status;
        }

        public String getIs_seller() {
            return is_seller;
        }

        public void setIs_seller(String is_seller) {
            this.is_seller = is_seller;
        }
    }
}
