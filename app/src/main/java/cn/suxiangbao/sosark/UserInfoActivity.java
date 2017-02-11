package cn.suxiangbao.sosark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.suxiangbao.sosark.album.PickOrTakeImageActivity;

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
        setContentView(R.layout.activity_user_info);
        init();
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
                Intent i = new Intent(UserInfoActivity.this, PickOrTakeImageActivity.class);
                startActivity(i);
            }
        });
    }

    private void loadData(){

    }
}
