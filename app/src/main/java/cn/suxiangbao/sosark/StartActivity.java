package cn.suxiangbao.sosark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

public class StartActivity extends CheckPermissionsActivity {

    private ImageView mImage = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

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
    }

    private void showActivity(Class<?> clazz)
    {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }
}
