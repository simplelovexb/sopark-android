package cn.suxiangbao.sosark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import cn.suxiangbao.sosark.entity.RetMsgObj;
import cn.suxiangbao.sosark.entity.UserInfo;
import cn.suxiangbao.sosark.pic.KevinApplication;
import cn.suxiangbao.sosark.util.JsonObjectRequest;

import static cn.suxiangbao.sosark.config.RetCodeConfig.SUCCESS;
import static cn.suxiangbao.sosark.config.RetCodeConfig.UNLOGIN;
import static cn.suxiangbao.sosark.config.ServerUrl.URL_IS_LOGIN;
import static cn.suxiangbao.sosark.config.SystemConf.START_WAIT_TIME_MS;

public class StartActivity extends BaseActivity {

    private ImageView mImage = null ;
    private static final String TAG = StartActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f,1.0f);
        alphaAnimation.setDuration(START_WAIT_TIME_MS);
        mImage = (ImageView) findViewById(R.id.img_start);
        mImage.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationEnd(Animation arg0)
            {
                SharedPreferences preferences = getSharedPreferences("startConfig",MODE_PRIVATE);
                Boolean isFirst = preferences.getBoolean("isFirst",true);
                if (isFirst){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isFirst",false);
                    editor.apply();
                    showActivity(GuideActivity.class);
                }else{
                    showActivity(HomeActivity.class);
                }

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

    private void loadData(){
        //TODO  加载用户数据  如何将一些比较大的如用户头像  图片等  对于安全性要求不高的但比较大的  保存在本地

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_IS_LOGIN, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG,"loadData :" +response.toString());
                RetMsgObj<UserInfo> ret   = mGson.fromJson(response.toString(),new TypeToken<RetMsgObj<UserInfo>>(){}.getType());
                Log.i(TAG,"loadData: ret = "+ret.toString());
                if (ret.getCode() == UNLOGIN){
                    setLogin(Boolean.FALSE);
                }else if (ret.getCode() == SUCCESS){
                    setLogin(Boolean.TRUE);
                    KevinApplication.getInstance().userInfo = ret.getData();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        },null);
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        getmQueue().add(request);
        getUserInfo();
    }
}
