package cn.suxiangbao.sosark;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.util.Date;
import java.util.Map;

import cn.suxiangbao.sosark.entity.UserInfo;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        Gson gson = new Gson();
        UserInfo userInfo = new UserInfo();
        userInfo.setUid(12345L);
        userInfo.setCreateDate(new Date());
        userInfo.setEmail("870903228@qq.com");
        Map<String,String> stringMap = userToMap(userInfo,gson);
        System.out.println(stringMap);
    }

    public  <T> Map userToMap(T t, Gson gson){
        if (t == null||gson == null){
            return null;
        }
        String userStr = gson.toJson(t);
        Map<String ,String> params = gson.fromJson(userStr,new TypeToken<Map<String,String>>(){}.getType());
        return params;
    }
}