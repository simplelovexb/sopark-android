package cn.suxiangbao.sosark.pic;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import cn.suxiangbao.sosark.entity.UserInfo;
import cn.suxiangbao.sosark.pic.activity.basic.ActivityStack;


/**
 * 版权所有：XXX有限公司
 *
 * KevinApplication
 *
 * @author zhou.wenkai ,Created on 2015-6-14 12:44:01
 * 		   Major Function：<b>KevinApplication</b>
 *
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class KevinApplication extends Application {

    protected static KevinApplication kevinApplication = null;
    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sid";
    /** 上下文 */
    protected Context mContext          = null;
    /** Activity 栈 */
    public ActivityStack mActivityStack = null;
    public UserInfo userInfo;
    private RequestQueue mQueue;
    private Gson mGson ;
    public Boolean  isLogin;
    private SharedPreferences preferences;


    @Override
    public void onCreate() {
        super.onCreate();
        // 由于Application类本身已经单例，所以直接按以下处理即可
        kevinApplication = this;
        mContext = getApplicationContext();     // 获取上下文
        mActivityStack = new ActivityStack();   // 初始化Activity 栈
        mQueue = Volley.newRequestQueue(mContext);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mGson = new GsonBuilder().registerTypeAdapter(Date.class,
                new UtilDateSerializer()).registerTypeAdapter(Calendar.class,
                new UtilCalendarSerializer()).registerTypeAdapter(GregorianCalendar.class,
                new UtilCalendarSerializer()).setDateFormat(DateFormat.LONG).setPrettyPrinting()
                .create();
        initConfiguration();
    }

    public RequestQueue getmQueue() {
        return mQueue;
    }



    /**
     * 获取当前类实例对象
     * @return
     */
    public static KevinApplication getInstance(){
        return kevinApplication;
    }

    /**
     * @description 初始化配置文件
     *
     * @return void
     * @date 2015-5-22 10:44:48
     */
    private void initConfiguration() {

    }

    public Gson getmGson() {
        return mGson;
    }



    /**
     * 检查返回的Response header中有没有session
     * @param responseHeaders Response Headers.
     */
    public final void checkSessionCookie(Map<String, String> responseHeaders) {
        if (responseHeaders.containsKey(SET_COOKIE_KEY)
                && responseHeaders.get(SET_COOKIE_KEY).startsWith(SESSION_COOKIE)) {
            String cookie = responseHeaders.get(SET_COOKIE_KEY);
            if (cookie.length() > 0) {
                String[] splitCookie = cookie.split(";");
                String[] splitSessionId = splitCookie[0].split("=");
                cookie = splitSessionId[1];
                SharedPreferences.Editor prefEditor = preferences.edit();
                prefEditor.putString(SESSION_COOKIE, cookie);
                prefEditor.commit();
            }
        }
    }

    /**
     * 添加session到Request header中
     */
    public final void addSessionCookie(Map<String, String> requestHeaders) {
        String sessionId = preferences.getString(SESSION_COOKIE, "");
        if (sessionId.length() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(SESSION_COOKIE);
            builder.append("=");
            builder.append(sessionId);
            if (requestHeaders.containsKey(COOKIE_KEY)) {
                builder.append("; ");
                builder.append(requestHeaders.get(COOKIE_KEY));
            }
            requestHeaders.put(COOKIE_KEY, builder.toString());
        }
    }

    public String getSid (){
        String sessionId = preferences.getString(SESSION_COOKIE, "");
        return sessionId;
    }


    private static class UtilDateSerializer implements JsonSerializer<Date>,JsonDeserializer<Date> {

        @Override
        public JsonElement serialize(Date date, Type type,
                                     JsonSerializationContext context) {
            return new JsonPrimitive(date.getTime());
        }

        @Override
        public Date deserialize(JsonElement element, Type type, JsonDeserializationContext context)
                throws JsonParseException {
            return new Date(element.getAsJsonPrimitive().getAsLong());
        }

    }

    private static class UtilCalendarSerializer implements JsonSerializer<Calendar>,JsonDeserializer<Calendar> {

        @Override
        public JsonElement serialize(Calendar cal, Type type,
                                     JsonSerializationContext context) {
            return new JsonPrimitive(Long.valueOf(cal.getTimeInMillis()));
        }

        @Override
        public Calendar deserialize(JsonElement element, Type type,
                                    JsonDeserializationContext context) throws JsonParseException {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(element.getAsJsonPrimitive().getAsLong());
            return cal;
        }

    }


    public  <T> Map objToMap(T t){
        if (t == null){
            return null;
        }
        String userStr = mGson.toJson(t);
        Map<String ,Object> params = mGson.fromJson(userStr,new TypeToken<Map>(){}.getType());

        return params;
    }
}
