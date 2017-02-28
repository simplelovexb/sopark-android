package cn.suxiangbao.sosark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import cn.suxiangbao.sosark.R;
import cn.suxiangbao.sosark.entity.CarPort;
import cn.suxiangbao.sosark.entity.RetMsgObj;
import cn.suxiangbao.sosark.pic.activity.PicContentActivity;
import cn.suxiangbao.sosark.util.JsonObjectRequest;

import static cn.suxiangbao.sosark.ParkEditActivity.REQUEST_FOR_PIC1;
import static cn.suxiangbao.sosark.ParkEditActivity.REQUEST_FOR_PIC2;
import static cn.suxiangbao.sosark.config.RetCodeConfig.SUCCESS;
import static cn.suxiangbao.sosark.config.RetCodeConfig.UNLOGIN;
import static cn.suxiangbao.sosark.config.ServerUrl.URL_ADD_CAR_PORT;

public class ParkDetailActivity extends BaseActivity {
    private ImageView mImgParkSnapShot = null;
    private AutoCompleteTextView mParkName = null;
    private AutoCompleteTextView mParkDescription = null;
    private ImageView mImgPark1 = null;
    private ImageView mImgPark2 = null;
    private CarPort carPort ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Object o = getIntent().getExtras().getSerializable("data");
        carPort = (CarPort) o;
        Toast.makeText(getApplicationContext(),carPort.toString(),Toast.LENGTH_SHORT).show();
        init();
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_park_edit);
    }


    private void init(){
        toolbarSetting();
        initView();
        changeViewState(false);
    }

    private void initView() {
        mImgParkSnapShot = (ImageView) findViewById(R.id.icon_park_snapshot);
        mImgPark1 = (ImageView) findViewById(R.id.img_park_1);
        loadImage(carPort.getPic1(),mImgPark1, ImageView.ScaleType.FIT_XY,(int) getResources().getDimension(R.dimen.mdip_park_car_img_width),(int) getResources().getDimension(R.dimen.mdip_park_car_img_width));
        mImgPark2 = (ImageView) findViewById(R.id.img_park_2);
        loadImage(carPort.getPic2(),mImgPark2, ImageView.ScaleType.FIT_XY,(int) getResources().getDimension(R.dimen.mdip_park_car_img_width),(int) getResources().getDimension(R.dimen.mdip_park_car_img_width));
        mParkName = (AutoCompleteTextView) findViewById(R.id.park_name);
        mParkName.setText(carPort.getName());
        mParkDescription = (AutoCompleteTextView) findViewById(R.id.park_description);
        mParkDescription.setText(carPort.getComment());
        mImgPark1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ParkDetailActivity.this, PicContentActivity.class);
                startActivityForResult(i,REQUEST_FOR_PIC1);
            }
        });
        mImgPark2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ParkDetailActivity.this, PicContentActivity.class);
                startActivityForResult(i,REQUEST_FOR_PIC2);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        menu.getItem(0).setTitle(R.string.action_edit);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_edit_commit:
                    if (item.getTitle().equals(getResources().getString(R.string.action_edit_commit))){
                        item.setTitle(getResources().getString(R.string.action_edit));
                        changeViewState(false);
                    }else if (item.getTitle().equals(getResources().getString(R.string.action_edit))){
                        item.setTitle(getResources().getString(R.string.action_edit_commit));
                        changeViewState(true);

                    }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void changeViewState(boolean editable) {
        mImgPark1.setClickable(editable);
        mImgPark2.setClickable(editable);
        if (editable){
            mParkDescription.setInputType(InputType.TYPE_CLASS_TEXT);
            mParkName.setInputType(InputType.TYPE_CLASS_TEXT);
            setTitle(getResources().getString(R.string.title_activity_park_edit));
        }else{
            setTitle(getResources().getString(R.string.title_activity_park_detail));
            mParkDescription.setInputType(InputType.TYPE_NULL);
            mParkName.setInputType(InputType.TYPE_NULL);
        }
    }

}
