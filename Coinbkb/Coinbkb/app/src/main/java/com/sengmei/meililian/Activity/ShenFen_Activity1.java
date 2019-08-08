package com.sengmei.meililian.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.StringUtil;
import com.sengmei.meililian.Utils.Validator;
import com.sengmei.meililian.certificateCamera.CameraActivity;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShenFen_Activity1 extends BaseActivity implements View.OnClickListener {
    private TextView next;
    private EditText name, numname;
    private String Names, Numnames;
    private ImageView iv1, iv2, iv3;
    private String tu;

    private String front;
    private String reverse;
    private String hand_pic;
    private TextView addr;

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
        addr = findViewById(R.id.addr);
        addr.setOnClickListener(this);
    }

    @Override
    public void ReinitViews() {
        addr.setText(UserBean.Address);
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
                next.setTextColor(getResources().getColor(R.color.black));
            } else {
                next.setClickable(false);
                next.setBackgroundResource(R.color.login);
                next.setTextColor(getResources().getColor(R.color.white));

            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CameraActivity.REQUEST_CODE && resultCode == CameraActivity.RESULT_CODE) {
            //获取文件路径，显示图片
            final String path = CameraActivity.getResult(data);
            Log.e("path", "path=" + path);
            File file = new File(path);//filePath 图片地址
            uploadPic(path);
            if (!TextUtils.isEmpty(path)) {
                if (tu.equals("1")) {
                    iv1.setImageBitmap(BitmapFactory.decodeFile(path));
                } else if (tu.equals("2")) {
                    iv2.setImageBitmap(BitmapFactory.decodeFile(path));
                } else if (tu.equals("3")) {
                    iv3.setImageBitmap(BitmapFactory.decodeFile(path));
                }
            }
        }
    }

    /**
     * 拍摄证件照片
     *
     * @param type 拍摄证件类型
     */
    private void takePhoto(int type) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0x12);
            return;
        }
        CameraActivity.openCertificateCamera(this, type);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next:
                if (StringUtil.isBlank(Numnames)){
                    StringUtil.ToastShow(ShenFen_Activity1.this, "请填写身份证号码");
                    break;
                }
                boolean car = Validator.isIDCard(Numnames);
                if (car) {

                    if (StringUtil.isBlank(front)) {
                        StringUtil.ToastShow(ShenFen_Activity1.this, "身份证正面照片为空");
                        break;
                    }
                    if (StringUtil.isBlank(reverse)) {
                        StringUtil.ToastShow(ShenFen_Activity1.this, "身份证反面照片为空");
                        break;
                    }
                    if (StringUtil.isBlank(hand_pic)) {
                        StringUtil.ToastShow(ShenFen_Activity1.this, "手持身份证照片为空");
                        break;
                    }
                    ShenFenShow();

                } else {
                    StringUtil.ToastShow(ShenFen_Activity1.this, "身份证号码错误");
                }

                break;
            case R.id.iv1:
                tu = "1";
                takePhoto(CameraActivity.TYPE_IDCARD_FRONT);
                break;
            case R.id.iv2:
                tu = "2";
                takePhoto(CameraActivity.TYPE_IDCARD_BACK);
                break;
            case R.id.iv3:
                tu = "3";
                takePhoto(CameraActivity.TYPE_COMPANY_PORTRAIT);
                break;
            case R.id.addr:
                startActivity(new Intent(ShenFen_Activity1.this, AddressActivity.class));
                break;
            default:
                break;
        }
    }

    public void back(View view) {
        finish();
    }

    private void ShenFenShow() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", Names);
        map.put("card_id", Numnames);
        map.put("front_pic", front);
        map.put("reverse_pic", reverse);
        map.put("hand_pic", hand_pic);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(ShenFen_Activity1.this).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.getURLupload(map);
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {

                if (response.body() == null) {
                    return;
                }
                Log.e("KeYongMaichuShow卖出=", "@@11=" + response.body().getType());
                if (response.body().getType().equals("ok")) {
                    StringUtil.ToastShow1(ShenFen_Activity1.this, response.body().getMessage());
                    finish();

                } else {
                    StringUtil.ToastShow(ShenFen_Activity1.this, response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("KeYongMaichuShow卖出=", "@@11=" + t.getMessage());
            }
        });
    }

    private void uploadPic(String str) {
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了
        File file = new File(str);//filePath 图片地址

        Log.e("******************", "tempFile=" + str);
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("file", file.getName(), imageBody);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(ShenFen_Activity1.this).getService();
        Call<ZhuCeBean> resultCall = mFromServerInterface.uploadImage(imageBodyPart);
        resultCall.enqueue(new Callback<ZhuCeBean>() {
            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                Log.e("******************", response.body().getMessage());
                Log.e("******************", "上传成功");
                Log.e("value111==", "2=" + tu);
                if (tu.equals("1")) {
                    front = response.body().getMessage();
                } else if (tu.equals("2")) {
                    reverse = response.body().getMessage();
                } else if (tu.equals("3")) {
                    hand_pic = response.body().getMessage();
                }

            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("******************", "上传失败" + "   " + t.getMessage());
            }
        });


    }
}
