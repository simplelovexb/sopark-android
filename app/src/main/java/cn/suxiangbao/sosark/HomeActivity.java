package cn.suxiangbao.sosark;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuInflater;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.suxiangbao.sosark.entity.GeoPoint;
import cn.suxiangbao.sosark.util.JsonArrayRequest;

import static cn.suxiangbao.sosark.config.ServerUrl.URL_GEO_NEAR;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener , LocationSource ,AMapLocationListener {
    private ImageView mIcon;
    private TextView mNick;
    private TextView mIdentifySign;
    private NavigationView navigationView = null;
    private View navHeader = null;
    private static final String TAG = HomeActivity.class.getCanonicalName();
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private MapView mMapView = null;
    private UiSettings mUiSettings;//定义一个UiSettings对象
    private AMap aMap;
    private Map<String,Object> geoRequestParms;
    private static final String LONGITUDE = "longitude";
    private static final String LATITUDE = "latitude";
    private static final String DISTANCE = "distance";
    private Integer distance = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init(savedInstanceState);
    }


    private void init(Bundle savedInstanceState){
        initBaseView();
        initMap(savedInstanceState);
        initNav();
    }

    private void initBaseView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void initNav(){
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navHeader = navigationView.getHeaderView(0);
        navHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,UserInfoActivity.class);
                startActivity(i);
            }
        });
        mIcon = (ImageView) navHeader.findViewById(R.id.icon_user);
        mNick = (TextView) navHeader.findViewById(R.id.txt_nick);
        mIdentifySign = (TextView) navHeader.findViewById(R.id.txt_identify_sign);

//        //TODO
//        loadImage(userInfo.getIcon(),mIcon);
//        String nick = userInfo.getNick();
//        String identifySign = userInfo.getIdentifySign();
//        if (StringUtils.isEmpty(nick)|| StringUtils.isBlank(nick)){
//            nick = getResources().getString(R.string.default_nick);
//        }
//        if (StringUtils.isEmpty(identifySign)|| StringUtils.isBlank(identifySign)){
//            identifySign = getResources().getString(R.string.default_identify_sign);
//        }
//
//        mNick.setText(nick);
//        mIdentifySign.setText(identifySign);

    }

    private void initMap(Bundle savedInstanceState) {

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.setCameraDistance(17F);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        aMap = mMapView.getMap();
        // 设置定位监听
        aMap.setLocationSource(this);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        // 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(
                BitmapDescriptorFactory.fromResource(R.mipmap.gps_point));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        // 自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(0);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.showIndoorMap(true);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        aMap.setLocationSource(this);//通过aMap对象设置定位数据源的监听
        mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setMyLocationButtonEnabled(true); //显示默认的定位按钮
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setScaleControlsEnabled(true);
        aMap.setMyLocationEnabled(true);// 可触发定位并显示当前位置

    }
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);//在菜单中找到对应控件的item
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(HomeActivity.this,"sdada.",Toast.LENGTH_SHORT).show();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        Log.d("Tag", "menu create");
        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {//设置打开关闭动作监听
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(HomeActivity.this, "onExpand", Toast.LENGTH_LONG).show();
                return true;
            }
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(HomeActivity.this, "Collapse", Toast.LENGTH_LONG).show();
                return true;
            }
        });
        Cursor cursor = this.getTestCursor();
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.item_suggestion, cursor, new String[] { "tb_name" },
                new int[] { R.id.textview });
        searchView.setSuggestionsAdapter(adapter);
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent i = new Intent();
        if (id == R.id.nav_park) {
            i.setClass(HomeActivity.this,ParkActivity.class);
        } else if (id == R.id.nav_car) {
            i.setClass(HomeActivity.this,CarActivity.class);
        } else if (id == R.id.nav_collection) {
            i.setClass(HomeActivity.this,CollectionActivity.class);
        } else if (id == R.id.nav_wallet) {
            i.setClass(HomeActivity.this,WalletActivity.class);
        } else if (id == R.id.nav_msg) {
            i.setClass(HomeActivity.this,MsgActivity.class);
        } else if (id == R.id.nav_manage) {
            i.setClass(HomeActivity.this,ManageActivity.class);
        }
        startActivity(i);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            Log.i(getClass().getSimpleName(),"location change");
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                if (geoRequestParms == null ){
                    geoRequestParms = new HashMap<>();
                }
                geoRequestParms.put(LONGITUDE,aMapLocation.getLongitude()+"");
                geoRequestParms.put(LATITUDE,aMapLocation.getLatitude()+"");
                geoRequestParms.put(DISTANCE,distance+"");

                JsonArrayRequest jsonObjectPostRequest=new JsonArrayRequest(Request.Method.POST,URL_GEO_NEAR, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i(TAG,response.toString());

                        Gson gson = new Gson();
                        List<GeoPoint> list  = gson.fromJson(response.toString(),new TypeToken<GeoPoint>(){}.getType());
                        marker(list);
                    }
                }, new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG,"获取失败"+error.getMessage());

                    }}, geoRequestParms);
                mQueue.add(jsonObjectPostRequest);

            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode()+ ": " + aMapLocation.getErrorInfo();
                Log.e(TAG,errText);
            }
        }
    }

    private void marker(List<GeoPoint> parks){
        Log.i(TAG,"marker");
        int i = 0;
        if (parks == null){
            Log.i(TAG,"park is null");
            return;
        }
        for (GeoPoint geoPoint : parks){
            LatLng latLng = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(latLng);
            markerOption.title("车位" + i++).snippet("幸福小区");
            markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
            markerOption.draggable(true);//设置Marker可拖动
// 将Marker设置为贴地显示，可以双指下拉地图查看效果
            markerOption.setFlat(true);//设置marker平贴地图效果
            Marker marker = aMap.addMarker(markerOption);

        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        Log.d(TAG,"定位开始");
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            mLocationOption.setOnceLocation(true);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }



    public Cursor getTestCursor() {

        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                this.getFilesDir() + "/my.db3", null);

        Cursor cursor = null;
        try {

            String insertSql = "insert into tb_test values (null,?,?)";

            db.execSQL(insertSql, new Object[] { "aa", 1 });

            db.execSQL(insertSql, new Object[] { "ab", 2 });

            db.execSQL(insertSql, new Object[] { "ac", 3 });

            db.execSQL(insertSql, new Object[] { "ad", 4 });

            db.execSQL(insertSql, new Object[] { "ae", 5 });

            String querySql = "select * from tb_test";

            cursor = db.rawQuery(querySql, null);

        } catch (Exception e) {

            String sql = "create table tb_test (_id integer primary key autoincrement,tb_name varchar(20),tb_age integer)";

            db.execSQL(sql);

            String insertSql = "insert into tb_test values (null,?,?)";

            db.execSQL(insertSql, new Object[] { "aa", 1 });

            db.execSQL(insertSql, new Object[] { "ab", 2 });

            db.execSQL(insertSql, new Object[] { "ac", 3 });

            db.execSQL(insertSql, new Object[] { "ad", 4 });

            db.execSQL(insertSql, new Object[] { "ae", 5 });

            String querySql = "select * from tb_test";

            cursor = db.rawQuery(querySql, null);
        }

        return cursor;
    }
//    class MyCursorAdapter extends CursorAdapter {
//        Context  context=null;
//        int viewResId;
//        public MyCursorAdapter(Context context, int resource, Cursor cursor) {
//            super(context,cursor);
//            viewResId=resource;
//        }
//        public View newView(Context context, Cursor cursor, ViewGroup parent) {
//
//            TextView view =null;
//            LayoutInflater vi = null;
//            vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view =(TextView)vi.inflate(viewResId, parent, false);
//            //v =(TextView)vi.inflate(textViewResourceId,null);
//            Log.i("hubin","newView"+view);
//            return view;
//        }
//        @Override
//        public void bindView(View view, Context context, Cursor cursor) {
//            Log.i("hubin","bind"+view);
//            TextView nameView = (TextView) view;
//            // Set the name
//            nameView.setText(cursor
//                    .getString(cursor.getColumnIndex("DISPLAY_NAME")));
//        }
//    }

}
