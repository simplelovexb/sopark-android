package cn.suxiangbao.sosark;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.suxiangbao.sosark.config.SystemConf;
import cn.suxiangbao.sosark.entity.Car;
import cn.suxiangbao.sosark.entity.CarPort;
import cn.suxiangbao.sosark.entity.RetMsgObj;
import cn.suxiangbao.sosark.entity.UserInfo;
import cn.suxiangbao.sosark.listener.UserInfoUpdateListener;
import cn.suxiangbao.sosark.pic.KevinApplication;
import cn.suxiangbao.sosark.pic.activity.PicContentActivity;
import cn.suxiangbao.sosark.util.ImageType;
import cn.suxiangbao.sosark.util.ImageUploadTask;
import cn.suxiangbao.sosark.util.JsonObjectRequest;
import cn.suxiangbao.sosark.util.UploadImage;

import static cn.suxiangbao.sosark.config.RetCodeConfig.SUCCESS;
import static cn.suxiangbao.sosark.config.RetCodeConfig.UNLOGIN;
import static cn.suxiangbao.sosark.config.ServerUrl.URL_ADD_CAR_PORT;
import static cn.suxiangbao.sosark.config.ServerUrl.URL_IMG_UPLOAD;

public class ParkEditActivity extends BaseActivity {

    private ImageView mImgParkSnapShot = null;
    private AutoCompleteTextView mParkName = null;
    private AutoCompleteTextView mParkDescription = null;
    private ImageView mImgPark1 = null;
    private ImageView mImgPark2 = null;
    public static final int REQUEST_FOR_PIC1 = 1;
    public static final int REQUEST_FOR_PIC2 = 2;
    private CarPort carport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_park_edit);
    }

    private void init(){
        carport = new CarPort();
        toolbarSetting();
        initView();
    }

    private void initView() {
        mImgParkSnapShot = (ImageView) findViewById(R.id.icon_park_snapshot);
        mImgPark1 = (ImageView) findViewById(R.id.img_park_1);
        mImgPark2 = (ImageView) findViewById(R.id.img_park_2);
        mParkName = (AutoCompleteTextView) findViewById(R.id.park_name);
        mParkDescription = (AutoCompleteTextView) findViewById(R.id.park_description);
        mImgPark1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ParkEditActivity.this, PicContentActivity.class);
                startActivityForResult(i,REQUEST_FOR_PIC1);
            }
        });
        mImgPark2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ParkEditActivity.this, PicContentActivity.class);
                startActivityForResult(i,REQUEST_FOR_PIC2);
            }
        });



    }


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
                carport.setName(mParkName.getText().toString());
                carport.setComment(mParkDescription.getText().toString());

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL_ADD_CAR_PORT, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        RetMsgObj ret = mGson.fromJson(response.toString(),RetMsgObj.class);
                        if (ret.getCode() == SUCCESS){
                            Toast.makeText(getApplicationContext(),"添加成功",Toast.LENGTH_SHORT).show();
                            finish();

                        }else if (ret.getCode() == UNLOGIN){
                            setLogin(false);
                            Toast.makeText(getApplicationContext(),ret.getMsg(),Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),ret.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                },objToMap(carport));
                getmQueue().add(request);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            switch (requestCode)
            {
                case REQUEST_FOR_PIC1:
                    Object o = data.getExtras().get("fileUri");
                    if (o !=null && o instanceof Uri){
                        Message msg = new Message();
                        msg.what = REQUEST_FOR_PIC1;
                        msg.obj = o;
                        myHandler.sendMessage(msg);
                        uploadImage((Uri) o, new ImageUploadTask.PostExecuteListener() {
                            @Override
                            public void listener(String fileUrl, String result) {
                                System.out.print(result);
                                Toast.makeText(ParkEditActivity.this,result,Toast.LENGTH_SHORT).show();
                                if (result.equals("上传成功")){
                                    carport.setPic1(fileUrl);
                                }
                            }
                        });
                    }
                    break;
                case REQUEST_FOR_PIC2:
                    o = data.getExtras().get("fileUri");
                    if (o !=null && o instanceof Uri){
                        Message msg = new Message();
                        msg.what = REQUEST_FOR_PIC2;
                        msg.obj = o;
                        myHandler.sendMessage(msg);
                        uploadImage((Uri) o, new ImageUploadTask.PostExecuteListener() {
                            @Override
                            public void listener(String fileUrl, String result) {
                                Toast.makeText(ParkEditActivity.this,result,Toast.LENGTH_SHORT).show();
                                if (result.equals("上传成功")){
                                    carport.setPic2(fileUrl);
                                }
                            }
                        });
                    }
                    break;
            }
        }
    }


    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            final int what = msg.what;
            if (what == REQUEST_FOR_PIC1){
                Uri uri = (Uri) msg.obj;
                mImgPark1.setImageURI(uri);
            }else if (what == REQUEST_FOR_PIC2){
                Uri uri = (Uri)msg.obj;
                mImgPark2.setImageURI(uri);
            }
        }
    };

    private void uploadImage (Uri uri ,ImageUploadTask.PostExecuteListener listener){
        UploadImage uploadImage = new UploadImage(URL_IMG_UPLOAD, SystemConf.UPLOAD_IMG_TIME_OUT);
        try {

            uploadImage.setData("usericon.png",getImageByte(uri), ImageType.PNG,KevinApplication.getInstance().getSid());
            ImageUploadTask task = new ImageUploadTask(uploadImage,listener);
            task.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    byte[]getImageByte(Uri uri){
        File file = new File(uri.getPath());
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
