package cn.suxiangbao.sosark;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class UserInfoEditPageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_edit_page);
        init();
    }

    private void init(){
        toolbarSetting();
    }
}
