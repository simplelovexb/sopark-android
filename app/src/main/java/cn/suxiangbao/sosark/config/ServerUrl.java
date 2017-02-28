package cn.suxiangbao.sosark.config;

/**
 * Created by Administrator on 2017/1/23.
 */

public interface ServerUrl {
    String BASE_REMOTE_URL = "http://139.199.171.188:8000/sopark/";
    String URL_PERSON_CARS_LIST = BASE_REMOTE_URL +"car/findByUid";
    String URL_PERSON_CARPORT_LIST = BASE_REMOTE_URL +"carPort/findByUid";
    String URL_PERSON_CAR_DETAIL = BASE_REMOTE_URL+ "/findById";
    String URL_GEO_NEAR = BASE_REMOTE_URL + "anon/getTemplateNearData";
    String URL_SIGN_UP = BASE_REMOTE_URL+ "anon/signUp";
    String URL_LOGIN = BASE_REMOTE_URL + "login";
    String URL_IS_LOGIN = BASE_REMOTE_URL +"anon/isLogin";
    String URL_FIND_SELF_INFO = BASE_REMOTE_URL + "user/findSelfInfo";
    String URL_ADD_CAR_PORT = BASE_REMOTE_URL+"carPort/add";
    String URL_IMG_UPLOAD = BASE_REMOTE_URL +"img/upload";
    String URL_UPDATE_INFO = BASE_REMOTE_URL +"user/update";
    String URL_LOGOUT = BASE_REMOTE_URL + "anon/logout";
}
