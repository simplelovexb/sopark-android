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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.suxiangbao.sosark.entity.CarPort;
import cn.suxiangbao.sosark.entity.RetMsgObj;
import cn.suxiangbao.sosark.util.JsonObjectRequest;
import cn.suxiangbao.sosark.view.DividerItemDecoration;

import static cn.suxiangbao.sosark.config.RetCodeConfig.SUCCESS;
import static cn.suxiangbao.sosark.config.RetCodeConfig.UNLOGIN;
import static cn.suxiangbao.sosark.config.ServerUrl.URL_PERSON_CARPORT_LIST;

public class ParkActivity extends BaseActivity {

    private static final String TAG = ParkActivity.class.getCanonicalName();
    private NormalRecyclerViewAdapter adapter ;
    private List<CarPort> parks ;
    private RecyclerView mParkList;
    private FloatingActionButton fab ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_park);
    }

    private void init(){
        initView();
        loadData();
    }

    private void initView(){
        toolbarSetting();
        parks = new ArrayList<>();
        mParkList = (RecyclerView) findViewById(R.id.list);
        mParkList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NormalRecyclerViewAdapter();
        mParkList.setAdapter(adapter);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ParkActivity.this,ParkEditActivity.class);
                startActivity(i);
            }
        });


        mParkList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
    }


    private void loadData(){


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_PERSON_CARPORT_LIST, new Response.Listener<JSONObject>() {

            public void onResponse(JSONObject response) {
                Log.i(TAG,response.toString());
                RetMsgObj<List<CarPort>> ret =  mGson.fromJson(response.toString(), new TypeToken<RetMsgObj<List<CarPort>>>(){}.getType());
//
                if (ret.getCode() == SUCCESS){
                    if (ret.getData() == null || ret.getData().isEmpty()){
                        Toast.makeText(ParkActivity.this,"你当前没有车位",Toast.LENGTH_SHORT).show();
                        parks.clear();
                        adapter.notifyDataSetChanged();
                    }else{
                        parks.clear();
                        for (CarPort carPort :ret.getData()){
                            parks.add(carPort);
                        }
                        adapter.notifyDataSetChanged();
                    }

                }else {
                    if (ret.getCode() == UNLOGIN){
                        setLogin(false);
                    }
                    Toast.makeText(ParkActivity.this,ret.getMsg()!=null?ret.getMsg():"",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ParkActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                Log.e(TAG,error.getMessage());
            }
        },null);
        getmQueue().add(jsonObjectRequest);

    }


    private class NormalRecyclerViewAdapter extends RecyclerView.Adapter<ParkActivity.NormalRecyclerViewAdapter.NormalTextViewHolder> {
        private final LayoutInflater mLayoutInflater;

        public NormalRecyclerViewAdapter() {
            mLayoutInflater = LayoutInflater.from(ParkActivity.this);
        }

        @Override
        public ParkActivity.NormalRecyclerViewAdapter.NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ParkActivity.NormalRecyclerViewAdapter.NormalTextViewHolder(mLayoutInflater.inflate(R.layout.item_park, null, false));
        }

        @Override
        public void onBindViewHolder(final ParkActivity.NormalRecyclerViewAdapter.NormalTextViewHolder holder, int position) {
            CarPort park = parks.get(position);
            holder.parkName.setText(park.getName());
            loadImage(park.getPic1(),holder.icon);
            holder.parkComment.setText(park.getComment());
            holder.setTag(park);
            //TODO
        }

        @Override
        public int getItemCount() {
            return parks == null ? 0 : parks.size();
        }

        public class NormalTextViewHolder extends RecyclerView.ViewHolder {
            ImageView icon;
            TextView parkName;
            TextView parkComment;
            View mView;
            NormalTextViewHolder(View view) {
                super(view);
                this.mView = view;
                icon = (ImageView) view.findViewById(R.id.icon_park);
                parkName = (TextView) view.findViewById(R.id.txt_park_name);
                parkComment = (TextView) view.findViewById(R.id.txt_park_comment);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(ParkActivity.this, ParkDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", (Serializable) v.getTag());
                        i.putExtras(bundle);
                        startActivity(i);
                    }
                });
            }

            public void setTag(CarPort c){
                mView.setTag(c);
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();

    }
}
