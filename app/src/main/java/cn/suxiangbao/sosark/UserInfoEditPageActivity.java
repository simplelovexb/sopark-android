package cn.suxiangbao.sosark;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class UserInfoEditPageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_user_info_edit_page);
    }

    private void init(){
        toolbarSetting();
    }
}
