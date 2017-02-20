package cn.suxiangbao.sosark;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import java.io.File;

import cn.suxiangbao.sosark.entity.UserInfo;
import cn.suxiangbao.sosark.pic.KevinApplication;

public class StartActivity extends BaseActivity {

    private ImageView mImage = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f,1.0f);
        alphaAnimation.setDuration(1000);
        mImage = (ImageView) findViewById(R.id.img_start);
        mImage.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationEnd(Animation arg0)
            {
                showActivity(GuideActivity.class);
            }
            @Override
            public void onAnimationRepeat(Animation animation)
            {}
            @Override
            public void onAnimationStart(Animation animation)
            {}
        });

        loadData();
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_start);
    }

    private void showActivity(Class<?> clazz)
    {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    void loadData(){
        //TODO  加载用户数据  如何将一些比较大的如用户头像  图片等  对于安全性要求不高的但比较大的  保存在本地

    }
}
