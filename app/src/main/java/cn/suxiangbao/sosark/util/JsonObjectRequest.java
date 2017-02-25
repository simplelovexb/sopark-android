package cn.suxiangbao.sosark.util;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cn.suxiangbao.sosark.pic.KevinApplication;

public  class JsonObjectRequest extends Request<JSONObject>{
    private Map<String,String> mMap;
    private Listener<JSONObject>  mListener;

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {

        return mMap;
    }


    public JsonObjectRequest(int method ,String url, Listener<JSONObject> listener, ErrorListener errorListener, Map map) {
        super(method, url, errorListener);
        mListener=listener;
        mMap=map;

    }



    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response)
    {
        KevinApplication.getInstance().checkSessionCookie(response.headers);
        String parsed;
        JSONObject jb =null;
        try
        {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            jb = new JSONObject(parsed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.success(jb, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        mListener.onResponse(response);

    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError
    {
        Map<String, String> headers = super.getHeaders();

        if (headers == null
                || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<>();
        }

        KevinApplication.getInstance().addSessionCookie(headers);

        return headers;
    }
}