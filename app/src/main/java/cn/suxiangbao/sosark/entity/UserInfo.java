package cn.suxiangbao.sosark.entity;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/23.
 * 用户信息类
 */

public class UserInfo {
    @SerializedName(value = "uid" ,alternate = {"id","_id"})
    private Long uid;
    @SerializedName(value = "username",alternate = {"uName"})
    private String username;
    @SerializedName(value = "nick")
    private String nick;
    @SerializedName(value = "email",alternate = {"e_mail"})
    private String email;
    @SerializedName(value = "icon",alternate = {"img"})
    private String icon;
    @SerializedName(value = "identifySign")
    private String identifySign;
    @SerializedName(value = "realName",alternate = "real_name")
    private String realName;
    /**
     * 身份的认证类型
     */
    @SerializedName(value = "authType",alternate = {"status"})
    private int authType;
    @SerializedName(value = "phoneNum")
    private String phoneNum;
    /**
     * 身份证照片 包含正反面
     */
    @SerializedName(value = "idCards")
    private List<String> idCards;
    @SerializedName(value = "createDate")
    private Date createDate;
    /**
     * 驾驶证照片
     */
    @SerializedName(value = "driverLicenses")
    private List<String> driverLicenses ;
    /**
     * 邮箱是否通过验证
     */
    @SerializedName(value = "emailAuth")
    private Boolean emailAuth ;
    /**
     * 手机是否通过验证
     */
    @SerializedName(value = "phoneAuth")
    private Boolean phoneAuth;

    private Uri localIconPath;


    /**
     * PASS 通过认证
     * UNPASS 未通过认证
     * NO_AUTH 认证不通过
     * AUTH_ING 认证中
     */
    public enum AuthType  {

        PASS(1), UNPASS(2), NO_AUTH(3), AUTH_ING(4);

        AuthType(int value) {
            this.value = value;
        }
        int value;

        public int getValue() {
            return value;
        }
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIdentifySign() {
        return identifySign;
    }

    public void setIdentifySign(String identifySign) {
        this.identifySign = identifySign;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getAuthType() {
        return authType;
    }

    public void setAuthType(int authType) {
        this.authType = authType;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public List<String> getIdCards() {
        return idCards;
    }

    public void setIdCards(List<String> idCards) {
        this.idCards = idCards;
    }

    public List<String> getDriverLicenses() {
        return driverLicenses;
    }

    public void setDriverLicenses(List<String> driverLicenses) {
        this.driverLicenses = driverLicenses;
    }

    public Boolean getEmailAuth() {
        return emailAuth;
    }

    public void setEmailAuth(Boolean emailAuth) {
        this.emailAuth = emailAuth;
    }

    public Boolean getPhoneAuth() {
        return phoneAuth;
    }

    public void setPhoneAuth(Boolean phoneAuth) {
        this.phoneAuth = phoneAuth;
    }

    public Uri getLocalIconPath() {
        return localIconPath;
    }

    public void setLocalIconPath(Uri localIconPath) {
        this.localIconPath = localIconPath;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", nick='" + nick + '\'' +
                ", email='" + email + '\'' +
                ", icon='" + icon + '\'' +
                ", identifySign='" + identifySign + '\'' +
                ", realName='" + realName + '\'' +
                ", authType=" + authType +
                ", phoneNum='" + phoneNum + '\'' +
                ", idCards=" + idCards +
                ", driverLicenses=" + driverLicenses +
                ", emailAuth=" + emailAuth +
                ", phoneAuth=" + phoneAuth +
                ", localIconPath=" + localIconPath +
                ", createDate="+createDate+
                '}';
    }


}
