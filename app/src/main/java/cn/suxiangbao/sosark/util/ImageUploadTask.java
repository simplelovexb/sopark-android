package cn.suxiangbao.sosark.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import cn.suxiangbao.sosark.R;
import cn.suxiangbao.sosark.entity.RetMsgObj;
import cn.suxiangbao.sosark.pic.KevinApplication;

import static cn.suxiangbao.sosark.config.RetCodeConfig.SUCCESS;
import static cn.suxiangbao.sosark.config.RetCodeConfig.UNLOGIN;

/**
 * Created by sxb on 2017/2/26.
 */

public class ImageUploadTask extends AsyncTask<String, Integer, String> {

    private UploadImage uploadImage;
    private PostExecuteListener mListener;
    private String fileUrl;
    public ImageUploadTask(UploadImage uploadImage,PostExecuteListener listener) {
        this.uploadImage = uploadImage;
        this.mListener = listener;
    }

    @Override
    protected void onPostExecute(String result) {
        //最终结果的显示
        mListener.listener(fileUrl,result);
    }

    @Override
    protected void onPreExecute() {
        //开始前的准备工作
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        //显示进度
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            String string = uploadImage.upload();
            String msg;
            RetMsgObj<String> ret = KevinApplication.getInstance().getmGson().fromJson(string,new TypeToken<RetMsgObj<String>>(){}.getType());
            if (ret.getCode() == SUCCESS){
                msg =  "上传成功";
                fileUrl = ret.getData();
            }else if (ret.getCode() == UNLOGIN){
                msg = ret.getMsg();
                KevinApplication.getInstance().isLogin = false;
            }else{
                msg = "上传失败";
            }
            Log.i("IMG UPLOAD","response str = "+string);
            return msg;
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败";
        }
    }

    public interface PostExecuteListener{
        void listener(String fileUrl,String result);
    }
}