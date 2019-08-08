package com.sengmei.meililian.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sengmei.RetrofitHttps.Beans.ZhuCeBean;
import com.sengmei.RetrofitHttps.GetDataFromServer;
import com.sengmei.RetrofitHttps.GetDataFromServerInterface;
import com.sengmei.kline.R;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.UserBean;
import com.sengmei.meililian.Utils.LQRPhotoSelectUtils;
import com.sengmei.meililian.Utils.RefreshDialog;
import com.sengmei.meililian.Utils.StringUtil;
import com.sengmei.meililian.Utils.Validator;
import com.sengmei.meililian.certificateCamera.CameraActivity;

import java.io.File;
import java.util.HashMap;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShenFen_Activity2 extends AppCompatActivity implements View.OnClickListener {
    private TextView next;
    private EditText name, numname;
    private String Names, Numnames;
    private ImageView iv1, iv2, iv3;
    private String tu;

    private String front;
    private String reverse;
    private String hand_pic;
    private TextView addr;
    private RefreshDialog dialog;

    private LQRPhotoSelectUtils mLqrPhotoSelectUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shenfen_activity);
        dialog = new RefreshDialog(ShenFen_Activity2.this);

        initViews();


    }

    @Override
    protected void onPause() {
        super.onPause();
        dialog.hideLoading();
    }

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
        // name.addTextChangedListener(textWatcher);
        // numname.addTextChangedListener(textWatcher);
        addr = findViewById(R.id.addr);
        addr.setOnClickListener(this);

        // 1、创建LQRPhotoSelectUtils（一个Activity对应一个LQRPhotoSelectUtils）
        mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, new LQRPhotoSelectUtils.PhotoSelectListener() {
            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                // 4、当拍照或从图库选取图片成功后回调
                Log.e("QQQQQQQQQQQ7", outputUri + tu+"=tu@@11=" + outputFile.getAbsolutePath());
                uploadPic(outputFile.getAbsolutePath());
                if (!TextUtils.isEmpty(outputFile.getAbsolutePath())) {
                    if (tu.equals("1")) {
                        Glide.with(ShenFen_Activity2.this).load(outputUri).into(iv1);
                    } else if (tu.equals("2")) {
                        Glide.with(ShenFen_Activity2.this).load(outputUri).into(iv2);
                    } else if (tu.equals("3")) {
                        Glide.with(ShenFen_Activity2.this).load(outputUri).into(iv3);
                    }
                }
            }
        }, false);//true裁剪，false不裁剪

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        addr.setText(UserBean.Address);
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

                next.setBackgroundResource(R.color.blue);
            } else {

                next.setBackgroundResource(R.color.login);

            }
        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next:
                Names = name.getText().toString().trim();
                Numnames = numname.getText().toString().trim();
                Log.e("QQQQQQQQQQQ6=", Names+"-@@-"+Numnames+"-@@-"+front+"-@@-"+reverse+"-@@-"+hand_pic);
                if (StringUtil.isBlank(Numnames)) {
                    StringUtil.ToastShow(ShenFen_Activity2.this, "身份证号码不能为空");
                    break;
                }
                if (StringUtil.isBlank(reverse)) {
                    StringUtil.ToastShow(ShenFen_Activity2.this, "身份证反面照片模糊为空");
                    break;
                }
                if (StringUtil.isBlank(front)) {
                    StringUtil.ToastShow(ShenFen_Activity2.this, "身份证正面照片模糊或为空");
                    break;
                }
                if (StringUtil.isBlank(hand_pic)) {
                    StringUtil.ToastShow(ShenFen_Activity2.this, "手持身份证照片模糊为空");
                    break;
                }
                ShenFenShow();
                break;
            case R.id.iv1:
                tu = "1";
                showDialog1();
                break;
            case R.id.iv2:
                tu = "2";
                showDialog1();
                break;
            case R.id.iv3:
                tu = "3";
                showDialog1();
                break;
            case R.id.addr:
                startActivity(new Intent(ShenFen_Activity2.this, AddressActivity.class));
                break;
            default:
                break;
        }
    }

    public void back(View view) {
        finish();
    }

    private void ShenFenShow() {
        Log.e("QQQQQQQQQQQ5=", Names+"-#-"+Numnames+"-##-"+front+"-##-"+reverse+"-@#-"+hand_pic);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", Names);
        map.put("card_id", Numnames);
        map.put("front_pic", front);
        map.put("reverse_pic", reverse);
        map.put("hand_pic", hand_pic);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(ShenFen_Activity2.this).getService();
        Call<ZhuCeBean> indexdata = mFromServerInterface.getURLupload(map);
        indexdata.enqueue(new Callback<ZhuCeBean>() {

            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {

                if (response.body() == null) {
                    return;
                }
                Log.e("KeYongMaichuShow卖出=", "@@11=" + response.body().getType());
                if (response.body().getType().equals("ok")) {
                    StringUtil.ToastShow1(ShenFen_Activity2.this, response.body().getMessage());
                    finish();

                } else {
                    StringUtil.ToastShow(ShenFen_Activity2.this, response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<ZhuCeBean> call, Throwable t) {
                Log.e("KeYongMaichuShow卖出=", "@@11=" + t.getMessage());
            }
        });
    }

    private void uploadPic(String str) {
        dialog.showLoading();
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了
        File file = new File(str);//filePath 图片地址

        Log.e("QQQQQQQQQQQ4", "tempFile=" + str);
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("file", file.getName(), imageBody);
        GetDataFromServerInterface mFromServerInterface = GetDataFromServer.getInstance(ShenFen_Activity2.this).getService();
        Call<ZhuCeBean> resultCall = mFromServerInterface.uploadImage(imageBodyPart);
        resultCall.enqueue(new Callback<ZhuCeBean>() {
            @Override
            public void onResponse(Call<ZhuCeBean> call, Response<ZhuCeBean> response) {
                Log.e("QQQQQQQQQQQ3", response.body().getMessage());
                Log.e("QQQQQQQQQQQ2", "上传成功");
                Log.e("QQQQQQQQQQQ1", "2=" + tu);

                dialog.hideLoading();
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
                dialog.hideLoading();
            }
        });


    }

    private void showDialog1() {
        final Dialog bottomDialog = new Dialog(ShenFen_Activity2.this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(ShenFen_Activity2.this).inflate(R.layout.dialog_button1, null);
        TextView paizhao = (TextView) contentView.findViewById(R.id.paizhao);
        TextView xuanqu = (TextView) contentView.findViewById(R.id.xuanqu);
        TextView quxiao = (TextView) contentView.findViewById(R.id.quxiao);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        paizhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.cancel();
                // 3、调用拍照方法
                if (tu.equals("1")) {
                    takePhoto(CameraActivity.TYPE_IDCARD_FRONT);
                } else if (tu.equals("2")) {
                    takePhoto(CameraActivity.TYPE_IDCARD_BACK);
                } else if (tu.equals("3")) {
                    takePhoto(CameraActivity.TYPE_COMPANY_PORTRAIT);
                }

            }
        });
        xuanqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.cancel();
                // 3、调用从图库选取图片方法
                PermissionGen.needPermission(ShenFen_Activity2.this,
                        LQRPhotoSelectUtils.REQ_SELECT_PHOTO,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}
                );
            }
        });
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.cancel();
            }
        });
    }

    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
    private void takePhoto() {
        mLqrPhotoSelectUtils.takePhoto();
    }

    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void selectPhoto() {
        mLqrPhotoSelectUtils.selectPhoto();
    }

    @PermissionFail(requestCode = LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
    private void showTip1() {
        //        Toast.makeText(getApplicationContext(), "不给我权限是吧，那就别玩了", Toast.LENGTH_SHORT).show();
        showDialog();
    }

    @PermissionFail(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void showTip2() {
        //        Toast.makeText(getApplicationContext(), "不给我权限是吧，那就别玩了", Toast.LENGTH_SHORT).show();
        showDialog();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 2、在Activity中的onActivityResult()方法里与LQRPhotoSelectUtils关联
        mLqrPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);

        if (requestCode == CameraActivity.REQUEST_CODE && resultCode == CameraActivity.RESULT_CODE) {
            //获取文件路径，显示图片
            final String path = CameraActivity.getResult(data);
            Log.e("QQQQQQQQQQQ8", tu+"path=" + path);
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

    public void showDialog() {
        //创建对话框创建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置对话框显示小图标
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        //设置标题
        builder.setTitle("权限申请");
        //设置正文
        builder.setMessage("在设置-应用-虎嗅-权限 中开启相机、存储权限，才能正常使用拍照或图片选择功能");

        //添加确定按钮点击事件
        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {//点击完确定后，触发这个事件

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //这里用来跳到手机设置页，方便用户开启权限
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + ShenFen_Activity2.this.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //添加取消按钮点击事件
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        //使用构建器创建出对话框对象
        AlertDialog dialog = builder.create();
        dialog.show();//显示对话框
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

}
