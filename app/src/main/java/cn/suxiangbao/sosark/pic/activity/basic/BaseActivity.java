package cn.suxiangbao.sosark.pic.activity.basic;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import cn.suxiangbao.sosark.pic.KevinApplication;

/**
 * 版权所有：XXX有限公司</br>
 * 
 * BaseActivity </br>
 * 
 * @author zhou.wenkai ,Created on 2015-7-21 09:16:37</br>
 * 		Major Function：Activity基类 </br>
 * 
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！</br>
 * @author mender，Modified Date Modify Content:
 */
public abstract class BaseActivity extends AppCompatActivity {

	protected Context mContext			= null;
	protected Toolbar toolbar = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		mContext = this;
        KevinApplication.getInstance().mActivityStack.addActivity(this);
        super.onCreate(savedInstanceState);
    }



	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ButterKnife.unbind(this);
	}


	public void finish() {
		super.finish();
		KevinApplication.getInstance().mActivityStack.removeActivity(this);
	}


}
