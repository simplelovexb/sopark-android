package cn.suxiangbao.sosark;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import cn.suxiangbao.sosark.entity.Car;
import cn.suxiangbao.sosark.util.JsonArrayRequest;
import cn.suxiangbao.sosark.view.CarDetailActivity;

import static cn.suxiangbao.sosark.config.ServerUrl.URL_PERSON_CARS_LIST;


/**
 * 个人汽车列表页
 */
public class CarActivity extends CheckPermissionsActivity {

    private List<Car> cars;
    private RecyclerView mRecyclerView;
    private static final String TAG = CarActivity.class.getCanonicalName();
    NormalRecyclerViewAdapter adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cars = Lists.newArrayList();
        mRecyclerView = (RecyclerView) findViewById(R.id.list_car);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//这里用线性显示 类似于listview
        adapter = new NormalRecyclerViewAdapter();
        mRecyclerView.setAdapter(adapter);
        loadData();
    }

    private void loadData(){
        Map<String,String> params = Maps.newHashMap();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL_PERSON_CARS_LIST, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.i(TAG,response.toString());
                Gson gson = new Gson();
                List<Car> list =  gson.fromJson(response.toString(), new TypeToken<List<Car>>() {}.getType());
                cars.clear();
                Collections.copy(cars,list);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,error.getMessage());
            }
        },params);
        mQueue.add(jsonArrayRequest);
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
            float heigh = getResources().getDimension(R.dimen.item_car_icon_heigh);
            ImageRequest request = new ImageRequest(car.getIcon(),
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            holder.icon.setImageBitmap(response);
                        }
                    }, (int) width, (int) heigh, ImageView.ScaleType.FIT_XY, Bitmap.Config.RGB_565,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            holder.icon.setImageResource(R.mipmap.car);
                            Log.e(TAG,error.getMessage());
                        }
                    });
            mQueue.add(request);
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
}
