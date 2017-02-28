package cn.suxiangbao.sosark;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.suxiangbao.sosark.entity.Car;
import cn.suxiangbao.sosark.entity.RetMsgObj;
import cn.suxiangbao.sosark.util.JsonObjectRequest;

import static cn.suxiangbao.sosark.config.RetCodeConfig.SUCCESS;
import static cn.suxiangbao.sosark.config.RetCodeConfig.UNLOGIN;
import static cn.suxiangbao.sosark.config.ServerUrl.URL_PERSON_CARS_LIST;


/**
 * 个人汽车列表页
 */
public class CarActivity extends BaseActivity {

    private List<Car> cars;
    private RecyclerView mRecyclerView;
    private static final String TAG = CarActivity.class.getCanonicalName();
    private NormalRecyclerViewAdapter adapter = null;

    private FloatingActionButton fab ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_car);
    }

    private void init(){
        initView();
        loadData();
    }

    private void initView(){
        toolbarSetting();
        cars = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
        adapter = new NormalRecyclerViewAdapter();
        mRecyclerView.setAdapter(adapter);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CarActivity.this,CarEditActivity.class);
                startActivity(i);
            }
        });
    }

    private void loadData(){
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, URL_PERSON_CARS_LIST, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG,response.toString());
                RetMsgObj<List<Car>> ret =  mGson.fromJson(response.toString(), new TypeToken<RetMsgObj<List<Car>>>() {}.getType());

                if (ret.getCode() == SUCCESS){

                    if (ret.getData() == null || ret.getData().isEmpty()){
                        Toast.makeText(CarActivity.this,"你当前没有车辆，请添加",Toast.LENGTH_SHORT).show();
                        cars.clear();
                        adapter.notifyDataSetChanged();
                    }else{
                        cars.clear();
                        Collections.copy(cars,ret.getData());
                        adapter.notifyDataSetChanged();
                    }

                }else if (ret.getCode() == UNLOGIN) {
                    Intent i = new Intent(CarActivity.this,LoginActivity.class);
                    startActivity(i);
                }else{

                    Toast.makeText(CarActivity.this,ret.getMsg()==null?ret.getMsg():"",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CarActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                Log.e(TAG,error.getMessage());
            }
        },null);
        getmQueue().add(jsonArrayRequest);
    }

    private class NormalRecyclerViewAdapter extends RecyclerView.Adapter<NormalRecyclerViewAdapter.NormalTextViewHolder> {
        private final LayoutInflater mLayoutInflater;

        public NormalRecyclerViewAdapter() {
            mLayoutInflater = LayoutInflater.from(CarActivity.this);
        }

        @Override
        public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NormalTextViewHolder(mLayoutInflater.inflate(R.layout.item_car, parent, false));
        }

        @Override
        public void onBindViewHolder(final NormalTextViewHolder holder, int position) {
            Car car = cars.get(position);
            String bandAndVersion = car.getBrand()+" " + car.getVersion();
            holder.licencePlate.setText(car.getLicencePlate());
            holder.bandAndVersion.setText(bandAndVersion);
            float width = getResources().getDimension(R.dimen.item_car_icon_width);
            float heigh = getResources().getDimension(R.dimen.item_car_icon_height);
            loadImage(car.getIcon(),holder.icon, ImageView.ScaleType.FIT_XY,(int )width,(int)heigh);
        }

        @Override
        public int getItemCount() {
            return cars == null ? 0 : cars.size();
        }

        public class NormalTextViewHolder extends RecyclerView.ViewHolder {
            ImageView icon;
            TextView bandAndVersion;
            TextView licencePlate;

            NormalTextViewHolder(View view) {
                super(view);
                icon = (ImageView) view.findViewById(R.id.icon_car);
                bandAndVersion = (TextView) view.findViewById(R.id.txt_band_and_version);
                licencePlate = (TextView) view.findViewById(R.id.txt_licence_plate);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(CarActivity.this, CarDetailActivity.class);
                        startActivity(i);
                    }
                });
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();

    }
}
