package cn.suxiangbao.sosark;

import android.content.Intent;
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
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import cn.suxiangbao.sosark.entity.Car;
import cn.suxiangbao.sosark.entity.Park;
import cn.suxiangbao.sosark.util.JsonArrayRequest;
import cn.suxiangbao.sosark.innerview.ParkDetailActivity;

import static cn.suxiangbao.sosark.config.ServerUrl.URL_PERSON_CARS_LIST;

public class ParkActivity extends BaseActivity {

    private static final String TAG = ParkActivity.class.getCanonicalName();
    private NormalRecyclerViewAdapter adapter ;
    private List<Park> parks ;
    private RecyclerView mParkList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park);
        init();
    }

    private void init(){
        initView();
        loadData();
    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        parks = Lists.newArrayList();
        mParkList = (RecyclerView) findViewById(R.id.list_park);
        mParkList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NormalRecyclerViewAdapter();
        mParkList.setAdapter(adapter);
    }


    private void loadData(){

        //TODO
        Map<String,String> params = Maps.newHashMap();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL_PERSON_CARS_LIST, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.i(TAG,response.toString());
                Gson gson = new Gson();
                List<Park> list =  gson.fromJson(response.toString(), new TypeToken<List<Car>>() {}.getType());
                parks.clear();
                Collections.copy(parks,list);
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


    private class NormalRecyclerViewAdapter extends RecyclerView.Adapter<ParkActivity.NormalRecyclerViewAdapter.NormalTextViewHolder> {
        private final LayoutInflater mLayoutInflater;

        public NormalRecyclerViewAdapter() {
            mLayoutInflater = LayoutInflater.from(ParkActivity.this);
        }

        @Override
        public ParkActivity.NormalRecyclerViewAdapter.NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ParkActivity.NormalRecyclerViewAdapter.NormalTextViewHolder(mLayoutInflater.inflate(R.layout.item_park, parent, false));
        }

        @Override
        public void onBindViewHolder(final ParkActivity.NormalRecyclerViewAdapter.NormalTextViewHolder holder, int position) {
            Park park = parks.get(position);
            //TODO
        }

        @Override
        public int getItemCount() {
            return parks == null ? 0 : parks.size();
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
                        Intent i = new Intent(ParkActivity.this, ParkDetailActivity.class);
                        startActivity(i);
                    }
                });
            }
        }
    }
}
