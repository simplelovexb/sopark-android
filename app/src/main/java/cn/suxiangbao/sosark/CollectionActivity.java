package cn.suxiangbao.sosark;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class CollectionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        init();
    }

    private void init(){
        toolbarSetting();
    }

}
