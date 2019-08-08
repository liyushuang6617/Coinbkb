package com.sengmei.RetrofitHttps.Beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chengwenlong on 2016/10/21.
 */

public class LunBoBean implements Serializable {


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



    public static class ObjectBean {
        private List<Bean> list;

        public List<Bean> getList() {
            return list;
        }

        public void setList(List<Bean> list) {
            this.list = list;
        }
    }
    public static class Bean {
        private String thumbnail;
        private String c_id;
        private String id;
        private String title;
        private String time;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getC_id() {
            return c_id;
        }

        public void setC_id(String c_id) {
            this.c_id = c_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
