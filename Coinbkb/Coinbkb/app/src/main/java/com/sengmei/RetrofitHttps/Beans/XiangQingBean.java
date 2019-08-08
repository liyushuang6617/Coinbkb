package com.sengmei.RetrofitHttps.Beans;

public class XiangQingBean {

    private String type;
    private ObjectBean message;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
/**
 * {
 *     "type": "ok",
 *     "message": {
 *         "thumbnail": "http://47.75.200.255:8080/upload/123456.png",
 *         "create_time": 1542211200000,
 *         "abstracts": "123456",
 *         "author": "  8888",
 *         "display": 1,
 *         "recommend": 1,
 *         "title": "879564936",
 *         "content": "<p>123456789</p>",
 *         "update_time": 1541754870000,
 *         "audit": 1,
 *         "c_id": 2,
 *         "id": 3,
 *         "keyword": "123456",
 *         "browse_grant": 0,
 *         "views": 99999,
 *         "discuss": 1
 *     }
 * }
 * */
    public ObjectBean getMessage() {
        return message;
    }

    public void setMessage(ObjectBean message) {
        this.message = message;
    }

    public static class ObjectBean {
        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
