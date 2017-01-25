/**
 * 
 */
package cn.suxiangbao.sosark;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.commons.lang3.StringUtils;

import cn.suxiangbao.sosark.entity.UserInfo;

import static android.R.attr.breadCrumbShortTitle;
import static android.R.attr.defaultValue;
import static android.R.attr.width;

/**
 * 继承了Activity，实现Android6.0的运行时权限检测
 * 需要进行运行时权限检测的Activity可以继承这个类
 * 
 * @创建时间：2016年5月27日 下午3:01:31 
 * @项目名称： AMapLocationDemo
 * @author hongming.wang
 * @文件名称：PermissionsChecker.java
 * @类型名称：PermissionsChecker
 * @since 2.5.0
 */
public class BaseActivity extends AppCompatActivity {
	/**
	 * 需要进行检测的权限数组
	 */
    private static final String TAG = BaseActivity.class.getCanonicalName();
	protected RequestQueue mQueue = null;
    protected UserInfo userInfo;
	protected Toolbar toolbar = null;
	protected String[] needPermissions = {
			Manifest.permission.ACCESS_COARSE_LOCATION,
			Manifest.permission.ACCESS_FINE_LOCATION,
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.READ_PHONE_STATE
			};
	
	private static final int PERMISSON_REQUESTCODE = 0;
	
	/**
	 * 判断是否需要检测，防止不停的弹框
	 */
	private boolean isNeedCheck = true;
	
	@Override
	protected void onResume() {
		super.onResume();
		if(isNeedCheck){
			checkPermissions(needPermissions);
		}
	}
	
	/**
	 * 
	 * @param permissions
	 * @since 2.5.0
	 *
	 */
	private void checkPermissions(String... permissions) {
		List<String> needRequestPermissonList = findDeniedPermissions(permissions);
		if (null != needRequestPermissonList
				&& needRequestPermissonList.size() > 0) {
			ActivityCompat.requestPermissions(this,
					needRequestPermissonList.toArray(
							new String[needRequestPermissonList.size()]),
					PERMISSON_REQUESTCODE);
		}
	}

	/**
	 * 获取权限集中需要申请权限的列表
	 * 
	 * @param permissions
	 * @return
	 * @since 2.5.0
	 *
	 */
	private List<String> findDeniedPermissions(String[] permissions) {
		List<String> needRequestPermissonList = new ArrayList<String>();
		for (String perm : permissions) {
			if (ContextCompat.checkSelfPermission(this,
					perm) != PackageManager.PERMISSION_GRANTED
					|| ActivityCompat.shouldShowRequestPermissionRationale(
							this, perm)) {
				needRequestPermissonList.add(perm);
			} 
		}
		return needRequestPermissonList;
	}

	/**
	 * 检测是否说有的权限都已经授权
	 * @param grantResults
	 * @return
	 * @since 2.5.0
	 *
	 */
	private boolean verifyPermissions(int[] grantResults) {
		for (int result : grantResults) {
			if (result != PackageManager.PERMISSION_GRANTED) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
			String[] permissions, int[] paramArrayOfInt) {
		if (requestCode == PERMISSON_REQUESTCODE) {
			if (!verifyPermissions(paramArrayOfInt)) {
				showMissingPermissionDialog();
				isNeedCheck = false;
			}
		}
	}

	/**
	 * 显示提示信息
	 * 
	 * @since 2.5.0
	 *
	 */
	private void showMissingPermissionDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.notifyTitle);
		builder.setMessage(R.string.notifyMsg);

		// 拒绝, 退出应用
		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});

		builder.setPositiveButton(R.string.setting,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						startAppSettings();
					}
				});

		builder.setCancelable(false);

		builder.show();
	}



	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		mQueue = Volley.newRequestQueue(getApplicationContext());
        userInfo = getUserInfo();
		super.onCreate(savedInstanceState);
	}

	/**
	 *  启动应用的设置
	 * 
	 * @since 2.5.0
	 *
	 */
	private void startAppSettings() {
		Intent intent = new Intent(
				Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		intent.setData(Uri.parse("package:" + getPackageName()));
		startActivity(intent);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void loadImage(String url, final ImageView imageView){
        if (imageView == null){
            return;
        }
        if (url==null || StringUtils.isBlank(url) || StringUtils.isEmpty(url)){
            return;
        }
		float width = imageView.getWidth();
		float height = imageView.getHeight();
		loadImage(url,imageView, ImageView.ScaleType.FIT_XY,(int)width,(int) height);
	}

    public void loadImage(String url, final ImageView imageView, ImageView.ScaleType type ,int width,int height){
        if (imageView == null){
            return;
        }
        if (url==null || StringUtils.isBlank(url) || StringUtils.isEmpty(url)){
            return;
        }

        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                Log.i(TAG,"loadImage Success");
                imageView.setImageBitmap(response);
            }
        }, (int)width, (int) height, type, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG,"loadImage Error");
            }
        });
        mQueue.add(request);
    }

    public UserInfo getUserInfo(){
        if (userInfo == null){

        }
        //TODO
        return  userInfo;
    }

	public void toolbarSetting(){
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar!=null){
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case android.R.id.home:{
				finish();
				return true;
			}
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
