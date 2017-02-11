package cn.suxiangbao.sosark;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class IdcDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idc_detail);
        init();
    }

    private void init(){
        toolbarSetting();
    }
}
