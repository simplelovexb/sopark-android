package cn.suxiangbao.sosark;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.suxiangbao.sosark.entity.UserInfo;
import cn.suxiangbao.sosark.listener.UserInfoUpdateListener;
import cn.suxiangbao.sosark.pic.KevinApplication;
import cn.suxiangbao.sosark.pic.activity.PicContentActivity;
import cn.suxiangbao.sosark.pic.fragment.basic.PictureSelectFragment;


public class UserInfoActivity extends BaseActivity {

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
        //TODO
        if (getUserInfo()!=null){

            mIconUser.setImageURI(getUserInfo().getLocalIconPath());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            switch (requestCode)
            {
                case 1:


                    Object o = data.getExtras().get("userInfo");
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
            if (msg.obj !=null || msg.obj instanceof Uri)
            mIconUser.setImageURI((Uri)msg.obj);
            System.out.println("uri >>>>>>>>>>>:"+msg.obj);
            //TODO 上传到服务器
            for (Activity activity :KevinApplication.getInstance().mActivityStack.activityList){
                if (activity instanceof UserInfoUpdateListener){
                    UserInfo userInfo = new UserInfo();
                    userInfo.setLocalIconPath((Uri)msg.obj);
                    userInfo.setNick("姓名：苏湘堡");
                    userInfo.setIdentifySign("梦想总要有的万一实现了呢");
                    ((UserInfoUpdateListener)activity).listener(userInfo);
                }
            }
            super.handleMessage(msg);
        }
    };

}
