package cn.suxiangbao.sosark;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.suxiangbao.sosark.entity.Message;
import cn.suxiangbao.sosark.util.JsonArrayRequest;

import static cn.suxiangbao.sosark.config.ServerUrl.URL_PERSON_CARS_LIST;

public class MsgActivity extends BaseActivity {

    private static final String TAG = MsgActivity.class.getCanonicalName();
    private List<Message> messages;
    private RecyclerView msgList;
    private NormalRecyclerViewAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        init();
    }

    private void init(){
        toolbarSetting();
        initView();
        loadData();
    }

    private void initView(){
        messages = new ArrayList<>();
        msgList = (RecyclerView) findViewById(R.id.list_msg);
        adapter = new NormalRecyclerViewAdapter();
        msgList.setLayoutManager(new LinearLayoutManager(this));
        msgList.setAdapter(adapter);
    }

    private void loadData(){
        Map<String,String> params = new HashMap<>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL_PERSON_CARS_LIST, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.i(TAG,response.toString());
                Gson gson = new Gson();
                List<Message> list =  gson.fromJson(response.toString(), new TypeToken<List<Message>>() {}.getType());
                messages.clear();
                Collections.copy(messages,list);
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



    private class NormalRecyclerViewAdapter extends RecyclerView.Adapter<MsgActivity.NormalRecyclerViewAdapter.NormalTextViewHolder> {
        private final LayoutInflater mLayoutInflater;

        public NormalRecyclerViewAdapter() {
            mLayoutInflater = LayoutInflater.from(MsgActivity.this);
        }

        @Override
        public MsgActivity.NormalRecyclerViewAdapter.NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MsgActivity.NormalRecyclerViewAdapter.NormalTextViewHolder(mLayoutInflater.inflate(R.layout.item_msg, parent, false));
        }

        @Override
        public void onBindViewHolder(final MsgActivity.NormalRecyclerViewAdapter.NormalTextViewHolder holder, int position) {
            Message message= messages.get(position);
            String title = message.getTitle();
            Boolean readed  = message.getReaded();
            Date time  = message.getTime();
            String msg = message.getMsg();
            String icon = message.getIcon();
            if (title == null){
                title = getResources().getString(R.string.default_title);
            }
            if (readed==null){
                readed = false;
            }
            if (time == null){
                time = new Date();
            }
            if (msg == null){
                msg = "";
            }
            loadImage(icon,holder.icon);
            holder.msg.setText(msg);
            holder.tag.setText(readed?"":"new");
            holder.time.setText(SimpleDateFormat.getDateInstance().format(time));
            holder.title.setText(title);
        }

        @Override
        public int getItemCount() {
            return messages == null ? 0 : messages.size();
        }

        public class NormalTextViewHolder extends RecyclerView.ViewHolder {
            ImageView icon;
            TextView title;
            TextView time ;
            TextView msg;
            TextView tag;

            NormalTextViewHolder(View view) {
                super(view);
                icon = (ImageView) view.findViewById(R.id.icon_msg);
                time = (TextView) view.findViewById(R.id.txt_msg_time);
                title = (TextView) view.findViewById(R.id.txt_msg_title);
                msg = (TextView) view.findViewById(R.id.txt_msg_content);
                tag = (TextView) view.findViewById(R.id.txt_msg_tag);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent i = new Intent(MsgActivity.this, CarDetailActivity.class);
//                        startActivity(i);
                    }
                });
            }
        }
    }
}
