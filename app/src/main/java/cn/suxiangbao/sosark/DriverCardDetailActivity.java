package cn.suxiangbao.sosark;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DriverCardDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_card_detail);
        init();
    }

    private void init() {
        toolbarSetting();
    }


}
