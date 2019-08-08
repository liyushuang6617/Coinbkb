package com.sengmei.RetrofitHttps.Beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chengwenlong on 2016/10/21.
 */

public class LunBoTopBean implements Serializable {


    private String type;
    private List<Bean> message;

    public List<Bean> getMessage() {
        return message;
    }

    public void setMessage(List<Bean> message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public static class Bean {
        private String pic;

       /* public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }*/

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
/*{
    "type": "ok",
    "message": [
        {
            "notes": "app轮播图",
            "name": "apppic1543988639699",
            "id": 24,
            "pic": "http://47.90.97.13:8080/upload/c573aede-10c7-45ed-9712-2fce0479a0671543988624.png"
        }
    ]
}*/