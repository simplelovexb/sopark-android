package cn.suxiangbao.sosark.pic.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import cn.suxiangbao.sosark.BaseActivity;
import cn.suxiangbao.sosark.R;
import cn.suxiangbao.sosark.pic.fragment.MainFragment;
import cn.suxiangbao.sosark.pic.fragment.basic.BaseFragment;
import cn.suxiangbao.sosark.pic.fragment.basic.PictureSelectFragment;


/**
 * 版权所有：XXX有限公司
 *
 * PicContentActivity
 *
 * @author zhou.wenkai ,Created on 2016-5-5 10:24:35
 * 		   Major Function：<b>主界面</b>
 *
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */
public class PicContentActivity extends BaseActivity {


    Bitmap mBitmap = null;
    BaseFragment mFragment = null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_pic_content);
    }

    private void init(){
        toolbarSetting();
        initMainFragment();
    }



    /**
     * 初始化内容Fragment
     *
     * @return void
     */
    public void initMainFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mFragment = MainFragment.newInstance();
        transaction.replace(R.id.main_act_container, mFragment, mFragment.getFragmentName());
        mFragment.setActivityListener(new PictureSelectFragment.OnPictureSelectedListener() {
            @Override
            public void onPictureSelected(Uri fileUri, Bitmap bitmap) {
                mBitmap = bitmap;
                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra("userInfo", fileUri);
                //设置返回数据
                setResult(RESULT_OK, intent);
            }
        });
        transaction.commit();
    }



    @Override
    public String toString() {
        return PicContentActivity.class.getCanonicalName();
    }

}
