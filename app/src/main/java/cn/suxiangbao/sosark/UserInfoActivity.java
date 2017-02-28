package cn.suxiangbao.sosark;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;

import cn.suxiangbao.sosark.config.SystemConf;
import cn.suxiangbao.sosark.entity.UserInfo;
import cn.suxiangbao.sosark.listener.UserInfoUpdateListener;
import cn.suxiangbao.sosark.pic.KevinApplication;
import cn.suxiangbao.sosark.pic.activity.PicContentActivity;
import cn.suxiangbao.sosark.util.ImageType;
import cn.suxiangbao.sosark.util.ImageUploadTask;
import cn.suxiangbao.sosark.util.UploadImage;

import static cn.suxiangbao.sosark.config.ServerUrl.URL_IMG_UPLOAD;


public class UserInfoActivity extends BaseActivity implements UserInfoUpdateListener {

    private ImageView mIconUser;
    private ImageView mImgQRcode;
    private ImageView mImgIdcStatus;
    private ImageView mImgDcStatus;
    private TextView mTxtNick;
    private TextView mTextAccount;
    private View mIdcView;
    private View mDcView;
    private View mUserInfoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_user_info);
    }

    private void init(){
        toolbarSetting();
        initView();
        loadData();
    }

    private void initView() {
        mUserInfoView = findViewById(R.id.layout_user_Info);
        mIconUser = (ImageView) findViewById(R.id.icon_user);
        mImgQRcode = (ImageView) findViewById(R.id.img_qrcode);
        mTxtNick = (TextView) findViewById(R.id.txt_nick);
        mTextAccount = (TextView) findViewById(R.id.txt_account);
        mDcView = findViewById(R.id.layout_dc);
        mIdcView = findViewById(R.id.layout_idc);
        mImgDcStatus = (ImageView) mDcView.findViewById(R.id.img_dc_status);
        mImgIdcStatus = (ImageView)mIdcView.findViewById(R.id.img_idc_status);


        mUserInfoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserInfoActivity.this, UserInfoEditPageActivity.class);
                startActivity(i);
            }
        });
        mIdcView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserInfoActivity.this,IdcDetailActivity.class);
                startActivity(i);
            }
        });
        mDcView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserInfoActivity.this,DriverCardDetailActivity.class);
                startActivity(i);
            }
        });

        mImgQRcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mIconUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserInfoActivity.this,PicContentActivity.class);
                startActivityForResult(i,1);
            }
        });
    }

    private void loadData(){
        UserInfo userInfo = getUserInfo();
        if (userInfo != null){
            if (getUserInfo().getLocalIconPath()!=null){
                mIconUser.setImageURI(getUserInfo().getLocalIconPath());
            }else{
                if (userInfo.getIcon()!=null){
                    loadImage(userInfo.getIcon(),mIconUser);
                }
            }
            if (!StringUtils.isBlank(userInfo.getNick())){
                mTxtNick.setText(userInfo.getNick());
            }else{
                mTxtNick.setText(getResources().getString(R.string.default_nick));
            }
            if (!StringUtils.isBlank(userInfo.getUsername())){
                mTextAccount.setText(userInfo.getUsername());
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            switch (requestCode)
            {
                case 1:
                    Object o = data.getExtras().get("fileUri");
                    if (o!=null || o instanceof Uri){
                        System.out.println(o.toString());
                    }
                    Message message = new Message();
                    message.obj = o;
                    myHandler.sendMessage(message);
                    break;
            }
        }
    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.obj !=null || msg.obj instanceof Uri){
                mIconUser.setImageURI((Uri)msg.obj);
            }

            UploadImage uploadImage = new UploadImage(URL_IMG_UPLOAD, SystemConf.UPLOAD_IMG_TIME_OUT);
            try {

                uploadImage.setData("usericon.png",getImageByte((Uri) msg.obj), ImageType.PNG,KevinApplication.getInstance().getSid());
                ImageUploadTask task = new ImageUploadTask(uploadImage,new ImageUploadTask.PostExecuteListener() {
                    @Override
                    public void listener(String fileUrl,String result) {
                        Toast.makeText(UserInfoActivity.this,result,Toast.LENGTH_SHORT).show();
                        mIconUser.setTag(fileUrl);
                        UserInfo userInfo ;
                        if (KevinApplication.getInstance().userInfo == null){
                            userInfo = new UserInfo();
                            userInfo.setCreateDate(new Date());
                        }else{
                            userInfo = KevinApplication.getInstance().userInfo;
                        }
                        userInfo.setIcon(fileUrl);
                        updateUserInfo(userInfo);
                    }
                });
                task.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (Activity activity :KevinApplication.getInstance().mActivityStack.activityList){
                if (activity instanceof UserInfoUpdateListener){
                    UserInfo userInfo = new UserInfo();
                    userInfo.setLocalIconPath((Uri)msg.obj);
                    ((UserInfoUpdateListener)activity).listener(userInfo);
                }
            }
            super.handleMessage(msg);
        }
    };


    byte[]getImageByte(Uri uri){
        File file = new File(uri.getPath());
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();

    }


    @Override
    public void listener(UserInfo userInfo) {

    }
}
