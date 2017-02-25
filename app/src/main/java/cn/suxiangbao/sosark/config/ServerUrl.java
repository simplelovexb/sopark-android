package cn.suxiangbao.sosark.config;

/**
 * Created by Administrator on 2017/1/23.
 */

public interface ServerUrl {
    String BASE_REMOTE_URL = "http://172.26.40.2:8000/chapter16/";
    String URL_PERSON_CARS_LIST = BASE_REMOTE_URL +"";
    String URL_PERSON_CAR_DETAIL = BASE_REMOTE_URL+ "";
    String URL_GEO_NEAR = BASE_REMOTE_URL + "anon/getTemplateNearData";
    String URL_SIGN_UP = BASE_REMOTE_URL+ "anon/signUp";
    String URL_LOGIN = BASE_REMOTE_URL + "login";
    String URL_IS_LOGIN = BASE_REMOTE_URL +"anon/isLogin";
    String URL_FIND_SELF_INFO = BASE_REMOTE_URL + "user/findSelfInfo";

}
