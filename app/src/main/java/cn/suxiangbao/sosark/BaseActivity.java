/**
 *
 */
package cn.suxiangbao.sosark;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
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
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import cn.suxiangbao.sosark.entity.RetMsgObj;
import cn.suxiangbao.sosark.entity.UserInfo;
import cn.suxiangbao.sosark.pic.KevinApplication;
import cn.suxiangbao.sosark.util.JsonObjectRequest;

import static cn.suxiangbao.sosark.config.RetCodeConfig.SUCCESS;
import static cn.suxiangbao.sosark.config.RetCodeConfig.UNLOGIN;
import static cn.suxiangbao.sosark.config.ServerUrl.URL_FIND_SELF_INFO;
import static cn.suxiangbao.sosark.config.ServerUrl.URL_IS_LOGIN;

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
public abstract class BaseActivity extends AppCompatActivity {
	/**
	 * 需要进行检测的权限数组
	 */
    private static final String TAG = BaseActivity.class.getCanonicalName();
	private RequestQueue mQueue = null;
	protected Toolbar toolbar = null;
	protected Context mContext			= null;
	protected String[] needPermissions = {
			Manifest.permission.ACCESS_COARSE_LOCATION,
			Manifest.permission.ACCESS_FINE_LOCATION,
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.READ_PHONE_STATE
			};
	protected Gson mGson = null;
	private static final int PERMISSON_REQUESTCODE = 0;
	/**
	 * 判断是否需要检测，防止不停的弹框
	 */
	private boolean isNeedCheck = true;



	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		KevinApplication.getInstance().mActivityStack.addActivity(this);
		mQueue = KevinApplication.getInstance().getmQueue();
		setOverflowShowingAlways();
        mGson = KevinApplication.getInstance().getmGson();
		super.onCreate(savedInstanceState);
		initContentView();
		ButterKnife.bind(this);

	}

    public RequestQueue getmQueue() {
        return mQueue;
    }

    @Override
	protected void onResume() {
		super.onResume();
		if(isNeedCheck){
			checkPermissions(needPermissions);
		}
	}
	protected abstract void initContentView();

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
        if (KevinApplication.getInstance().userInfo == null){
            initUserInfo();
        }
        return  KevinApplication.getInstance().userInfo;
    }

    protected void initUserInfo(){
        final JsonObjectRequest userInfoRequest = new JsonObjectRequest(Request.Method.GET, URL_FIND_SELF_INFO, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                RetMsgObj<UserInfo> ret = mGson.fromJson(response.toString(),new TypeToken<RetMsgObj<UserInfo>>(){}.getType());
                Log.i(TAG,"initUserInfo : ret = " + ret );
                int code = ret.getCode();

                if (code == SUCCESS ){
                    KevinApplication.getInstance().userInfo = ret.getData();
                    setLogin(true);
                }else if (code == UNLOGIN){
                    setLogin(false);
                    Log.i(TAG,ret.getMsg());
                }else{
                    Log.i(TAG,ret.getMsg());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        },null);
        mQueue.add(userInfoRequest);
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

	/**
	 * 设置总是显示溢出菜单
	 *
	 * @return void
	 * @date 2015-7-25 12:01:31
	 */
	private void setOverflowShowingAlways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ButterKnife.unbind(this);
	}


	public void finish() {
		KevinApplication.getInstance().mActivityStack.removeActivity(this);
		super.finish();
	}


	public Boolean getLogin() {
		if (KevinApplication.getInstance().isLogin == null){
			isLogin();
			return false;
		}
		return KevinApplication.getInstance().isLogin;
	}

	public void setLogin(Boolean isLogin) {
        KevinApplication.getInstance().isLogin = isLogin;
	}

	protected void isLogin(){
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_IS_LOGIN, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
                RetMsgObj ret = mGson.fromJson(response.toString(),new TypeToken<RetMsgObj>(){}.getType());
                Log.i(TAG,"isLogin : ret = " + ret );
                int code = ret.getCode();
                if (code == 1){
                    setLogin(true);
                }else{
                    setLogin(false);
                }
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				setLogin(null);
			}
		},null){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                Log.d(TAG,">>>>>>>>>>>> " + response.toString() + " >>>>>>>>>>>>>");
                return super.parseNetworkResponse(response);
            }
        };
		mQueue.add(request);
	}

}
