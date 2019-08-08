package com.sengmei.meililian.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.sengmei.kline.R;
import com.sengmei.meililian.Activity.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串辅助类 <br>
 * Description: <br>
 * Date: 2013-4-25 <br>
 * Copyright (c) 2011 LEYISoft <br>
 *
 * @author wwy
 */
public class StringUtil {

    public static char split = 0x01;// 分隔符

    public static char feed = 0x0A;// 换行
    static AlertDialog alertDialog;
    public static String KEY = "zgpg";
    public static String url;
    private static ProgressDialog dialog;
    private static boolean boo = false;

    public static long getDateMinute(long date) {
        return date / 1000 / 60;
    }

    public static long getDateHours(long date) {
        return date / 1000 / 60 / 60;
    }


    /**
     * @param targetStr 要处理的字符串
     * @description 切割字符串，将文本和img标签碎片化，如"ab<img>cd"转换为"ab"、"<img>"、"cd"
     */
    public static List<String> cutStringByImgTag(String targetStr) {
        List<String> splitTextList = new ArrayList<String>();
        Pattern pattern = Pattern.compile("<img.*?src=\\\"(.*?)\\\".*?>");
        Matcher matcher = pattern.matcher(targetStr);
        int lastIndex = 0;
        while (matcher.find()) {
            if (matcher.start() > lastIndex) {
                splitTextList.add(targetStr.substring(lastIndex, matcher.start()));
            }
            splitTextList.add(targetStr.substring(matcher.start(), matcher.end()));
            lastIndex = matcher.end();
        }
        if (lastIndex != targetStr.length()) {
            splitTextList.add(targetStr.substring(lastIndex, targetStr.length()));
        }
        return splitTextList;
    }

    public static String randomNumber(int j) {
        String s = "";
        Random random = new Random();
        for (int i = 0; i < j; i++) {
            s += Math.abs(random.nextInt()) % 10;
        }
        return s;
    }

    public static String random(int r, int n) {
        String str = "";
        int[] intRet = new int[n];
        int intRd = 0; //存放随机数
        int count = 0; //记录生成的随机数个数
        int flag = 0; //是否已经生成过标志
        while (count < n) {
            Random rdm = new Random(System.currentTimeMillis());
            intRd = Math.abs(rdm.nextInt()) % r + 1;
            for (int i = 0; i < count; i++) {
                if (intRet[i] == intRd) {
                    flag = 1;
                    break;
                } else {
                    flag = 0;
                }
            }
            if (flag == 0) {
                intRet[count] = intRd;
                count++;
            }
        }
        for (int t = 0; t < n; t++) {
            str += intRet[t] + ",";
        }
        str = str.substring(0, str.length() - 1);
        return str;
    }


    public static Window dialog(Activity activity, int layout) {
        alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.show();
        Window win = alertDialog.getWindow();
        win.setContentView(layout);
        win.setGravity(Gravity.CENTER);
        win.setBackgroundDrawableResource(R.color.white);
        int with = getwithWindow(activity);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = (with / 2) + 150;
        Log.e("width=======", lp.width + "-----------");
        lp.height = (with / 2) + 150;
        lp.alpha = 0.7f;
        win.setAttributes(lp);

        return win;
    }

    public static Window dialogs(Context context, int layout) {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();
        Window win = alertDialog.getWindow();

        WindowManager.LayoutParams lp = win.getAttributes();
        win.setGravity(Gravity.CENTER);
        lp.alpha = 0.7f;
        win.setAttributes(lp);
        win.setContentView(layout);
        return win;
    }

    public static void diss() {
        alertDialog.dismiss();
    }

    // 定义一个
    public static void setTextViewTxt(TextView tv, String str) {
        if (!TextUtils.isEmpty(str)) {
            tv.setText(str);
        }
    }

    /**
     * 判断字符串是否为 null/空/无内容
     *
     * @param str
     * @return
     * @author wwy
     */
    public static boolean isBlank(String str) {
        if (null == str)
            return true;
        if ("".equals(str.trim()))
            return true;
        if (str.equals("null"))
            return true;
        return false;
    }

    /**
     * 字符串相等 null和空字符串认为相等，忽略字符串前后空格
     *
     * @param str1
     * @param str2
     * @return
     * @author wwy
     */
    public static boolean compareString(String str1, String str2) {
        if (null == str1) {
            str1 = "";
        }
        if (null == str2) {
            str2 = "";
        }
        if (str1.trim().equals(str2.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 截取字符串
     *
     * @param string
     * @return
     */
    public static String partition(String string) {
        String newString = string.substring(0, 37);
        return newString;
    }

    /**
     * 将对象转成String
     *
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        if (obj == null) {
            return "";
        }
        return obj.toString().trim();
    }

    public static String encodePassword(String password, String algorithm) {
        if (algorithm == null)
            return password;
        byte unencodedPassword[] = password.getBytes();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            return password;
        }
        md.reset();
        md.update(unencodedPassword);
        byte encodedPassword[] = md.digest();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < encodedPassword.length; i++) {
            if ((encodedPassword[i] & 0xff) < 16)
                buf.append("0");
            buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
        }
        return buf.toString();
    }

    public static String getEncryptPassword(String password) {
        try {
            // return Des.encrypt(password, KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }

    public static String getEncryptPasswordMD5(String password) {
        return encodePassword(password, "MD5");
    }

    /**
     * 获取json节点值
     *
     * @param jsonObject
     * @param jsonNode
     * @return
     */
    public static String getJSONObject(JSONObject jsonObject, String jsonNode) {
        try {

            if (jsonObject.has(jsonNode))
                return jsonObject.get(jsonNode).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static JSONObject getJSONNode(JSONObject jsonObject, String jsonNode) {
        try {
            if (jsonObject.has(jsonNode))
                return jsonObject.getJSONObject(jsonNode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 像数据库插入字段
     */
    public static ContentValues pubValues(ContentValues values, String cloumn, String str_value) {
        if (str_value != null) {
            values.put(cloumn, str_value);
        }
        return values;
    }

    /**
     * 字符串转整数
     *
     * @param l_ser
     * @return
     */

    public static int strToInt(String l_ser) {
        int covs = 0;
        try {
            covs = new Integer(l_ser);
        } catch (Exception e) {
        }
        return covs;
    }

    /**
     * 字符串转double
     *
     * @param gis
     * @return
     */

    public static double strToDouble(String gis) {
        double covs = 0d;
        try {
            covs = new Double(gis).doubleValue();
        } catch (Exception e) {
        }
        return covs;
    }

    /**
     * 字符串转long
     *
     * @param time
     * @return
     */

    public static long strToLong(String time) {
        long covs = 0l;
        try {
            covs = new Long(time).longValue();
        } catch (Exception e) {
        }
        return covs;
    }

    /**
     * 验证手机电话号码
     * <p/>
     * 手机号码 移动：134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188
     * 联通：130,131,132,152,155,156,185,186 电信：133,1349,153,180,189
     *
     * @param mobilephone
     * @return
     */
    public static boolean checkMobilephone(String mobilephone) {
        String cm = "^1(34[0-8]|(3[5-9]|5[017-9]|8[278]|7[0-9])\\d)\\d{7}$";// 中国移动正则
        String cu = "^1(3[0-2]|5[256]|8[56]|7[0])\\d{8}$";// 中国联通正则
        String ct = "^1((33|53|8[09])[0-9]|349)\\d{7}$";// 中国电信正则
        if (Pattern.matches(cm, mobilephone) || Pattern.matches(cu, mobilephone) || Pattern.matches(ct, mobilephone)) {
            return true;
        }
        return false;
    }

    public static boolean isNember(String nember) {
        String cm = "^[0-9]\\d{10}$";
        if (Pattern.matches(cm, nember)) {
            return true;
        }
        return false;
    }

    public static boolean isuserNember(String nember) {
        String cm = "^[0-9]\\d{5}$";
        if (Pattern.matches(cm, nember)) {
            return true;
        }
        return false;
    }

    public static boolean isIDcard(String text) {
        String regx = "[0-9]{17}x";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(reg1) || text.matches(regex);
    }

    /**
     * 返回原型图
     *
     * @param thumbnial
     * @return
     */
    public static String convertPrototype(String thumbnial) {
        try {
            if (null == thumbnial)
                return "";
            return (new StringBuilder()).append(thumbnial.substring(0, thumbnial.lastIndexOf('.'))).append("_prototype")
                    .append(thumbnial.substring(thumbnial.lastIndexOf('.'))).toString();
        } catch (Exception e) {
            return thumbnial;
        }
    }

    /**
     * 将多个对象进行组装<br>
     *
     * @param parts
     * @return
     * @author wwy
     */
//     public static String assemblingString(Object... parts) {
//     StringBuilder builder = new StringBuilder();
//     if (CollectionUtil.isEmpty(parts)) {
//     return null;
//     }
//     for (Object part : parts) {
//     if(part == null){
//     part = Constant.EMPTY_STRING;
//     }
//     builder.append(part);
//     }
//     return builder.toString();
//     }

    /**
     * 转化时间字符转 类型：\/Date(1395396358000)\/
     */
    public static String date(String date) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？Date]";
        try {

            if (null == date || date.equals("")) {
                return "";
            } else {

//                SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//12小时制
//
//                SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制


                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(date);
                System.out.println(m.replaceAll(""));
                SimpleDateFormat sdf = new SimpleDateFormat("HH/mm");
                String sd = sdf.format(new Date(Long.parseLong(m.replaceAll("").trim())));
                return sd;

            }

        } catch (Exception e) {
            return "";
        }

    }
    public static String date1(String date) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？Date]";
        try {

            if (null == date || date.equals("")) {
                return "";
            } else {

//                SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//12小时制
//
//                SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制


                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(date);
                System.out.println(m.replaceAll(""));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String sd = sdf.format(new Date(Long.parseLong(m.replaceAll("").trim())));
                return sd;

            }

        } catch (Exception e) {
            return "";
        }

    }

    /**
     * 是否包含特殊字符
     */
    public static boolean containsAny(String str) {

        String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

        // System.out.println("++++++++++++++++++++++++++++++++"+str.contains(regEx));
        if (str != null) {
            Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(str);
            return m.find();
        } else {
            return false;
        }
    }

    public static boolean isCardId(String str) {
        if (str != null && !str.equals("")) {
            Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
            Matcher idNumMatcher = idNumPattern.matcher(str);
            return idNumMatcher.matches();
        } else {
            return false;
        }
    }

    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)

                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    public static String getDateString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 ");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    public static long getTimeLong() {
        Date dt = new Date();
        Long time = dt.getTime();//这就是距离1970年1月1日0点0分0秒的毫秒数
        return time;
    }

    /**
     * 弹出框
     */
    public static void ShowDialog(Context mContext, String msg) {
        dialog = new ProgressDialog(mContext);
        if (!dialog.isShowing()) {
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage(msg);
            dialog.show();
        }
    }

    /**
     * 弹出框
     */
    public static void ShowTishiDialog(Context mContext, String msg) {
        alertDialog = new AlertDialog.Builder(mContext).create();
        if (!alertDialog.isShowing()) {
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.setMessage(msg);
            alertDialog.show();
        }
    }

    /**
     * 弹出框
     */
    public static void ShowJinDu(Context mContext, String msg) {
        dialog = new ProgressDialog(mContext);
        if (!dialog.isShowing()) {
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage(msg);
            dialog.show();
        }
    }

    public static void ShowToast(Context mContext, String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 关闭弹出窗ProgressDialog
     */
    public static void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * EditText，是否获取到焦点，
     */
    public static boolean editTextHasFocus(EditText editText) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                boo = hasFocus;
            }
        });
        return boo;
    }

    /**
     * 手机闪光灯的状态
     */
    public static String getFlashMode() {
        Camera cam = Camera.open();
        Camera.Parameters p = cam.getParameters();
        return p.getFlashMode();//获取闪光灯的状态
    }

    /**
     * 生成带logo的中文二维码
     */
    /*    public static void createEWM(final Activity context, final ImageView imageView, final String con, final Bitmap logoBitmap) {
     *//*
        这里为了偷懒，就没有处理匿名 AsyncTask 内部类导致 Activity 泄漏的问题
        请开发在使用时自行处理匿名内部类导致Activity内存泄漏的问题，处理方式可参考 https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E6%80%BB%E7%BB%93.md
         *//*
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                int size = (getwithWindow(context)/2)+50;
                Log.e("size=======",size+"-----------");
                return QRCodeEncoder.syncEncodeQRCode(con, size, Color.BLACK, Color.WHITE, logoBitmap);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(context, "生成带logo的中文二维码失败", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }*/

    /**
     * 获取屏幕宽高的方法
     * <p/>
     * WindowManager wm = (WindowManager) this
     * .getSystemService(Context.WINDOW_SERVICE);
     * int width = wm.getDefaultDisplay().getWidth();
     * int height = wm.getDefaultDisplay().getHeight();
     * WindowManager manager = this.getWindowManager();
     * DisplayMetrics outMetrics = new DisplayMetrics();
     * manager.getDefaultDisplay().getMetrics(outMetrics);
     * int width2 = outMetrics.widthPixels;
     * int height2 = outMetrics.heightPixels;
     * Resources resources = this.getResources();
     * DisplayMetrics dm = resources.getDisplayMetrics();
     * float density1 = dm.density;
     * int width3 = dm.widthPixels;
     * int height3 = dm.heightPixels;
     */
    public static int getwithWindow(Activity activity) {
        WindowManager wm = activity.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    public static int getheightWindow(Activity activity) {
        WindowManager wm = activity.getWindowManager();
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    public static String ToastShow(Activity activity, String s) {
        Toast.makeText(activity, "" + s, Toast.LENGTH_SHORT).show();
        if (s.equals("请先登录")) {
            activity.startActivity(new Intent(activity, LoginActivity.class));
        }
        return s;
    }

    public static String ToastShow1(Context context, String s) {
        Toast.makeText(context, "" + s, Toast.LENGTH_SHORT).show();
        if (s.equals("请先登录")) {
            context.startActivity(new Intent(context, LoginActivity.class));
        }
        return s;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    // 返回一个列表对话框
    public static AlertDialog.Builder getListDialogBuilder(Context context,
                                                           String[] items, String title, DialogInterface.OnClickListener clickListener) {
        return new AlertDialog.Builder(context).setTitle(title).setItems(items, clickListener);

    }
/*    public static String uploadImageToQiniu(String filePath, String token) {
        final String[] s = new String[1];
            UploadManager uploadManager = new UploadManager();
            // 设置图片名字
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String key = "icon_" + sdf.format(new Date());
            Log.e("@##@@#@#=" , key + "=res=" + filePath);
            // String key = "https://up.qbox.me/";
            uploadManager.put(filePath, key, token, new UpCompletionHandler() {
                @Override
                public void complete(String key, ResponseInfo info, JSONObject res) {
                    // info.error中包含了错误信息，可打印调试
                    // 上传成功后将key值上传到自己的服务器
                    Log.e("info.error=" + info.error, key + "=res=" + res);
                    try {
                        s[0] = res.getString("key");
                        //   GetImgShow(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, null);
        return s[0];
    }*/

    /**
     * 判断邮箱是否合法
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 验证是否是手机号码
     *
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {

        if (null == str || "".equals(str)) return false;
        Pattern pattern = Pattern.compile("1[0-9]{10}");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 纯数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 纯字母
     *
     * @param fstrData
     * @return
     */
    public static boolean isChar(String fstrData) {
        char c = fstrData.charAt(0);
        if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 乘法
     *
     * @param var1
     * @param var2
     * @return
     */
    public static String mul(String var1, String var2) {
        BigDecimal b1 = new BigDecimal(var1);
        BigDecimal b2 = new BigDecimal(var2);
        return b1.multiply(b2).toString();
    }

}
