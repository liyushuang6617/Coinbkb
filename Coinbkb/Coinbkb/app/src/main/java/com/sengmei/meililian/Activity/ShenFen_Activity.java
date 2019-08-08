package com.sengmei.meililian.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.Bean.DataBean;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.HttpUtils;
import com.sengmei.meililian.Utils.QRCodeUtil;
import com.sengmei.meililian.Utils.SharedPreferencesUtil;
import com.sengmei.meililian.Utils.StringUtil;
import com.sengmei.meililian.Utils.Updata;
import com.sengmei.meililian.Utils.Validator;
import com.sunfusheng.marqueeview.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShenFen_Activity extends BaseActivity implements View.OnClickListener{
    private TextView next;
    private EditText name, numname;
    private String Names, Numnames;
    private ImageView iv1,iv2,iv3;

    private static com.lidroid.xutils.HttpUtils httpUtils ;
    private File tempFile = new File(Environment.getExternalStorageDirectory(),
            getPhotoFileName());

    private String[] items = {"拍照", "相册"};
    private String title = "选择照片";
    private static final int PHOTO_CARMERA = 1;
    private static final int PHOTO_PICK = 2;
    private static final int PHOTO_CUT = 3;
    private static final int UPDATE_FXID = 4;// 结果
    private static final int UPDATE_NICK = 5;// 结果
    private static String value;
    private String tu;
    private String front;
    private String reverse;
    private String hand_pic;
    private ProgressDialog dialog;

    private RequestCallBack<Object> requestCallBack = new RequestCallBack<Object>() {
        @Override
        public void onSuccess(ResponseInfo<Object> responseInfo) {
            value = responseInfo.result.toString().trim();//返回字段
            Log.e("value==", value);
            StringUtil.ToastShow(ShenFen_Activity.this,"value1="+value);
        }

        @Override
        public void onFailure(HttpException e, String s) {
            value = s;
           // Updata.upload(tempFile.toString().trim(), UserBean.URLupload, requestCallBack);
            Log.e("value==", value);
        }
    };
    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.shenfen_activity);
    }

    @Override
    public void initViews() {
        iv1 = (ImageView) findViewById(R.id.iv1);
        iv1.setOnClickListener(this);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv2.setOnClickListener(this);
        iv3 = (ImageView) findViewById(R.id.iv3);
        iv3.setOnClickListener(this);
        next = (TextView) findViewById(R.id.next);
        next.setOnClickListener(this);
        name = (EditText) findViewById(R.id.name);
        numname = (EditText) findViewById(R.id.numname);
        name.addTextChangedListener(textWatcher);
        numname.addTextChangedListener(textWatcher);
    }

    @Override
    public void ReinitViews() {

    }

    @Override
    public void initData() {

    }
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            Names = name.getText().toString().trim();
            Numnames = numname.getText().toString().trim();
            if (Names.length() > 0 & Numnames.length() > 0) {
                next.setClickable(true);
                next.setBackgroundResource(R.color.blue);
            }else {
                next.setClickable(false);
                next.setBackgroundResource(R.color.login);

            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.next:
             boolean car= Validator.isIDCard(Numnames);
             if (car){
                 if (!StringUtil.isBlank(reverse)&!StringUtil.isBlank(front)&!StringUtil.isBlank(hand_pic)){
                     ShenFenShow();
                 }

             }else {
                 StringUtil.ToastShow(ShenFen_Activity.this,"身份证号码错误");
             }

                break;
            case R.id.iv1:
                tu="1";
                AlertDialog.Builder dialog = StringUtil.getListDialogBuilder(
                        ShenFen_Activity.this, items, title, dialogListener);
                dialog.show();
                break;
            case R.id.iv2:
                tu="2";
                AlertDialog.Builder dialog1 = StringUtil.getListDialogBuilder(
                        ShenFen_Activity.this, items, title, dialogListener);
                dialog1.show();

                break;
            case R.id.iv3:
                tu="3";
                AlertDialog.Builder dialog2 = StringUtil.getListDialogBuilder(
                        ShenFen_Activity.this, items, title, dialogListener);
                dialog2.show();

                break;
                default:
                    break;
        }
    }
    public void back(View view){
        finish();
    }

    private DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0:
                    // 调用拍照
                    startCamera(dialog);
                    break;
                case 1:
                    // 调用相册
                    startPick(dialog);

                    break;
                default:
                    break;
            }
        }
    };

    // 调用系统相机
    protected void startCamera(DialogInterface dialog) {
        dialog.dismiss();
        // 调用系统的拍照功能
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("camerasensorytpe", 2); // 调用前置摄像头
        intent.putExtra("autofocus", true); // 自动对焦
        intent.putExtra("fullScreen", false); // 全屏
        intent.putExtra("showActionIcons", false);
        // 指定调用相机拍照后照片的存储路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, PHOTO_CARMERA);
    }

    // 调用系统相册
    protected void startPick(DialogInterface dialog) {
        dialog.dismiss();
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, PHOTO_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_CARMERA:
                startPhotoZoom(Uri.fromFile(tempFile), 300);
                break;
            case PHOTO_PICK:
                if (null != data) {
                    startPhotoZoom(data.getData(), 300);
                }
                break;
            case PHOTO_CUT:
                if (null != data) {
                    setPicToView(data);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // 调用系统裁剪
    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以裁剪
        intent.putExtra("crop", true);
        // aspectX,aspectY是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY是裁剪图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        // 设置是否返回数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_CUT);
    }

    // 将裁剪后的图片显示在ImageView上
    private void setPicToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (null != bundle) {
            final Bitmap bmp = bundle.getParcelable("data");
            Log.e("value111==","1=" +tu);
            if (tu.equals("1")){
                iv1.setImageBitmap(bmp);
            }else if (tu.equals("2")){
                iv2.setImageBitmap(bmp);
            }else if (tu.equals("3")){
                iv3.setImageBitmap(bmp);
            }
            saveCropPic(bmp);
           // uploadUserAvatar(Bitmap2Bytes(bmp));
        }
    }


    // 把裁剪后的图片保存到sdcard上
    private void saveCropPic(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileOutputStream fis = null;
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        try {
            fis = new FileOutputStream(tempFile);
            fis.write(baos.toByteArray());
            fis.flush();
            uploadPic();
            Log.e("value111==","tempFile=" +tempFile.toString().trim());
         //   Updata.upload(tempFile.toString().trim(), UserBean.URLupload, requestCallBack);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != baos) {
                    baos.close();
                }
                if (null != fis) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // 使用系统当前日期加以调整作为照片的名称
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("'PNG'_yyyyMMdd_HHmmss");
        return sdf.format(date) + ".png";
    }

    public byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
    private void uploadUserAvatar(final byte[] data) {
       // dialog = ProgressDialog.show(this, getString(R.string.dl_update_photo), getString(R.string.dl_waiting));
        new Thread(new Runnable() {

            @Override
            public void run() {
         /*       final String avatarUrl = DemoHelper.getInstance().getUserProfileManager().uploadUserAvatar(data);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        if (avatarUrl != null) {
                            Toast.makeText(ShenFen_Activity.this, getString(R.string.toast_updatephoto_success),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ShenFen_Activity.this, getString(R.string.toast_updatephoto_fail),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
*/
            }
        }).start();

        dialog.show();
    }

    private void ShenFenShow() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", Names);
        map.put("card_id", Numnames);
        map.put("front_pic", front);
        map.put("reverse_pic", reverse);
        map.put("hand_pic", hand_pic);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(ShenFen_Activity.this).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.getURLupload(map);
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {

                if (response.body() == null) {
                    return;
                }
                Log.e("KeYongMaichuShow卖出=", "@@11=" + response.body().getType());
                if (response.body().getType().equals("ok")) {
                    StringUtil.ToastShow1(ShenFen_Activity.this, response.body().getMessage());
                    finish();

                }else {
                    StringUtil.ToastShow(ShenFen_Activity.this,response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("KeYongMaichuShow卖出=", "@@11=" + t.getMessage());
            }
        });
    }
    private void uploadPic() {
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了
        File file = tempFile;//filePath 图片地址

        Log.e("******************","tempFile="+tempFile);
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("file", file.getName(), imageBody);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(ShenFen_Activity.this).getService();
        Call<ZhuCeBean> resultCall = mFromServerInterface.uploadImage(imageBodyPart);
        resultCall.enqueue(new Callback<ZhuCeBean>() {
            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                Log.e("******************",response.body().getMessage());
                Log.e("******************","上传成功");
                if (tu.equals("1")){
                    front=response.body().getMessage();
                    Log.e("value111==","1=" +front);
                }else if (tu.equals("2")){
                    reverse=response.body().getMessage();
                    Log.e("value111==","2=" +reverse);
                }else if (tu.equals("3")){
                    hand_pic=response.body().getMessage();
                    Log.e("value111==","2=" +hand_pic);
                }

            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("******************","上传失败"+"   "+t.getMessage());
            }
        });



    }
}
