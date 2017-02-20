package cn.suxiangbao.sosark.util;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

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
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        mListener.onResponse(response);

    }

}