package cn.suxiangbao.sosark;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import cn.suxiangbao.sosark.entity.RetMsgObj;
import cn.suxiangbao.sosark.listener.UserInfoUpdateListener;
import cn.suxiangbao.sosark.pic.KevinApplication;
import cn.suxiangbao.sosark.util.JsonObjectRequest;

import static cn.suxiangbao.sosark.config.RetCodeConfig.SUCCESS;
import static cn.suxiangbao.sosark.config.ServerUrl.URL_LOGOUT;

public class ManageActivity extends BaseActivity {

    private Button btn_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_manage);
    }

    private void init(){
        toolbarSetting();
        initView();
    }

    private void initView(){
        btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_LOGOUT, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        RetMsgObj retMsgObj = mGson.fromJson(response.toString(),RetMsgObj.class);
                        if (retMsgObj.getCode()==SUCCESS){
                            Toast.makeText(getApplicationContext(),"退出成功",Toast.LENGTH_SHORT).show();
                            setLogin(false);
                            KevinApplication.getInstance().userInfo = null;
                            for (Activity activity:KevinApplication.getInstance().mActivityStack.activityList){
                                if (activity instanceof UserInfoUpdateListener){
                                    ((UserInfoUpdateListener) activity).listener(null);
                                }
                            }
                            finish();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                },null);
                getmQueue().add(request);
            }
        });

    }
}
