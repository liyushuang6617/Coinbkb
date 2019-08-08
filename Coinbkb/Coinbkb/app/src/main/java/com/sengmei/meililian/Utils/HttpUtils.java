package com.sengmei.meililian.Utils;


import android.util.Log;

import com.sengmei.meililian.MyApplication;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Cookie;

public class HttpUtils {
    public static String post(HashMap<String, String> params , String url){
        String result = "";

//		username password

        // 获取网络连接

        try {
            URL newurl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) newurl.openConnection();
        /*    Map<String,List<String>> cookies = conn.getHeaderFields();
            List<String> setCookies = cookies.get("Set-Cookie");
        for (String Cookie:setCookies){

               }*/
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(15000);//
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Charset", "utf-8");
            //conn.setRequestProperty("Cookie", Cookie);
            // 对参数进行处理
            String data = "";
            Set<String> set = params.keySet();// 获取到所有map的键
            for (String string : set) {// 遍历参数，拼接data
                String value = params.get(string);
                data = data + string + "=" + URLEncoder.encode(value, "utf-8") + "&";
            }
            data = data.substring(0, data.length() - 1);
            conn.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
            // 写如数据需要流
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();

            // 读取流
            InputStream inputStream = conn.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader reder = new BufferedReader(reader);
            String line = "";// 每一行的数据

            while ((line = reder.readLine()) != null) {
                result = result + line;
            }
            reader.close();
            conn.disconnect();


        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    public static String get(HashMap<String, String> params, String url) {

        String result = "";
        try {
            String newUrl = url + "?";
            Set<String> set = params.keySet();
            for (String string : set) {
                String value = params.get(string);

                newUrl = newUrl + string + "=" + URLEncoder.encode(value, "utf-8") + "&";

            }
            newUrl = newUrl.substring(0, newUrl.length() - 1);
            //打开URL连接
            URL u = new URL(newUrl);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            //直接读取
            InputStream input = conn.getInputStream();
            InputStreamReader reader = new InputStreamReader(input);
            BufferedReader bureader = new BufferedReader(reader);
            String line = "";
            while ((line = bureader.readLine()) != null) {
                result = result + line;

            }
            bureader.close();
            conn.disconnect();


        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    public static String newpost(HashMap<String, String> params, String url) {
        String result = "";
        try {
            //创建实例
            HttpClient client = new DefaultHttpClient();
            //创建post请求
            HttpPost post = new HttpPost(url);
            //处理参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Set<String> set = params.keySet();
            for (String string : set) {
                list.add(new BasicNameValuePair(string, params.get(string)));
            }
            //给post请求添加参数
            UrlEncodedFormEntity entity;

            entity = new UrlEncodedFormEntity(list, "utf-8");
            post.setEntity(entity);
           post.addHeader("Authorization",MyApplication.Authori);
            //post.addHeader("Authorization","123");
            Log.e("MyApplication","MyApplication.Authori"+MyApplication.Authori);
            //执行请求获得响应
            HttpResponse response = client.execute(post);
            //根据返回码拿到数据
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity());

            } else {
                result = "访问服务器失败,错误码为:" + response.getStatusLine().getStatusCode();
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;


    }

    public static String newget(HashMap<String, String> params, String url) {
        String result = "";

        HttpClient client = null;
        client = new DefaultHttpClient();
        try {
            String newUrl = url + "?";
            Set<String> set = params.keySet();
            for (String string : set) {
                String value = params.get(string);
                newUrl = newUrl + string + "=" + URLEncoder.encode(value, "utf-8") + "&";
            }
            newUrl = newUrl.substring(0, newUrl.length() - 1);
            //httpclient实例
//            client = new DefaultHttpClient();
            HttpGet get = new HttpGet(newUrl);
            get.addHeader("Authorization",MyApplication.Authori);
            // get.addHeader("Authorization","123");
            Log.e("MyApplication","AUTHORIZATION"+MyApplication.Authori);
            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity());

            } else {
                result = "访问服务器失败,错误码为:" + response.getStatusLine().getStatusCode();
            }


        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {//绝对会执行的文件
            client.getConnectionManager().shutdown();
        }
        return result;
    }

    public static long getTimeLong() {
        Date dt= new Date();
        Long time= dt.getTime();//这就是距离1970年1月1日0点0分0秒的毫秒数
        return time;
    }
}
