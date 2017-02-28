package cn.suxiangbao.sosark;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import cn.suxiangbao.sosark.entity.RetMsgObj;
import cn.suxiangbao.sosark.pic.activity.PicContentActivity;
import cn.suxiangbao.sosark.util.JsonObjectRequest;

import static cn.suxiangbao.sosark.config.RetCodeConfig.SUCCESS;
import static cn.suxiangbao.sosark.config.ServerUrl.URL_IMG_UPLOAD;

public class CarEditActivity extends BaseActivity {

    private static final int REQUEST_FOR_CAR_FRONT = 1;
    private static final int REQUEST_FOR_CAR_TAIL = 2;
    private static final int REQUEST_FOR_CAR_LEFT = 3;
    private static final int REQUEST_FOR_CAR_RIGHT = 4;
    private static final int REQUEST_FOR_CAR_YEAR_CHECK = 5;
    private static final int REQUEST_FOR_CAR_DRIVER_LICENCE_1 = 6;
    private static final int REQUEST_FOR_CAR_DRIVER_LICENCE_2 = 7;
    private ImageView mCarFront;
    private ImageView mCarTail;
    private ImageView mCarLeft;
    private ImageView mCarRight;
    private ImageView mCarYearCheck;
    private ImageView mCarDriverLicence1;
    private ImageView mCarDriverLicence2;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_car_edit);
    }

    private void init(){
        toolbarSetting();
        initView();
    }

    private void initView(){
        mCarFront = (ImageView) findViewById(R.id.img_car_img_front);
        mCarTail = (ImageView) findViewById(R.id.img_car_img_tail);
        mCarLeft= (ImageView) findViewById(R.id.img_car_img_left);
        mCarRight= (ImageView) findViewById(R.id.img_car_img_right);
        mCarYearCheck= (ImageView) findViewById(R.id.img_car_year_check);
        mCarDriverLicence1= (ImageView) findViewById(R.id.img_car_driver_licence_1);
        mCarDriverLicence2= (ImageView) findViewById(R.id.img_car_driver_licence_2);

        mCarFront.setOnClickListener(listener);
        mCarTail.setOnClickListener(listener);
        mCarLeft.setOnClickListener(listener);
        mCarRight.setOnClickListener(listener);
        mCarYearCheck.setOnClickListener(listener);
        mCarDriverLicence1.setOnClickListener(listener);
        mCarDriverLicence2.setOnClickListener(listener);

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int request = 0;
            switch (v.getId()){
                case R.id.img_car_img_front:
                    request = REQUEST_FOR_CAR_FRONT;
                    break;
                case R.id.img_car_img_tail:
                    request = REQUEST_FOR_CAR_TAIL;
                    break;
                case R.id.img_car_img_left:
                    request = REQUEST_FOR_CAR_LEFT;
                    break;
                case R.id.img_car_img_right:
                    request = REQUEST_FOR_CAR_RIGHT;
                    break;
                case R.id.img_car_year_check:
                    request = REQUEST_FOR_CAR_YEAR_CHECK;
                    break;
                case R.id.img_car_driver_licence_1:
                    request = REQUEST_FOR_CAR_DRIVER_LICENCE_1;
                    break;
                case R.id.img_car_driver_licence_2:
                    request = REQUEST_FOR_CAR_DRIVER_LICENCE_2;
                    break;
            }
            startActivityForResult(new Intent(CarEditActivity.this, PicContentActivity.class),request);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_edit_commit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Object o = data.getExtras().get("fileUri");
            if (o != null && o instanceof Uri) {
                Message msg = new Message();
                msg.obj = o;
                msg.what = requestCode;
                myHandler.sendMessage(msg);
            }
        }
    }


    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            Uri o = (Uri) msg.obj;
            switch (what){
                case REQUEST_FOR_CAR_FRONT:
                    mCarFront.setImageURI(o);
                    break;
                case REQUEST_FOR_CAR_TAIL:
                    mCarTail.setImageURI(o);
                    break;
                case REQUEST_FOR_CAR_LEFT:
                    mCarLeft.setImageURI(o);
                    break;
                case REQUEST_FOR_CAR_RIGHT:
                    mCarRight.setImageURI(o);
                    break;
                case REQUEST_FOR_CAR_YEAR_CHECK:
                    mCarYearCheck.setImageURI(o);
                    break;
                case REQUEST_FOR_CAR_DRIVER_LICENCE_1:
                    mCarDriverLicence1.setImageURI(o);
                    break;
                case REQUEST_FOR_CAR_DRIVER_LICENCE_2:
                    mCarDriverLicence2.setImageURI(o);
                    break;
            }
        }
    };
}
