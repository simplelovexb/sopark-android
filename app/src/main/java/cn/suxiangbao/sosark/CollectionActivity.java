package cn.suxiangbao.sosark;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class CollectionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_collection);
    }

    private void init(){
        toolbarSetting();
    }

}
