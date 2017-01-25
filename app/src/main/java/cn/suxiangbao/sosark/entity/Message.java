package cn.suxiangbao.sosark.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by sxb on 2017/1/25.
 * 消息实体类
 */

public class Message {
    /**
     * 消息ID
     */
    @SerializedName(value = "mid",alternate = {"pushId","id"})
    private Long mid;
    /**
     * 发送者id
     */
    @SerializedName(value = "sendId",alternate = {"send_id"})
    private Long sendId;
    /**
     * 接收者id
     */
    @SerializedName(value = "receiveId",alternate = {"receive_id"})
    private Long receiveId;
    /**
     * 消息标题
     */
    @SerializedName(value = "title")
    private String title;
    /**
     * 消息内容
     */
    @SerializedName(value = "msg")
    private String msg;
    /**
     * 消息的发送时间
     */
    @SerializedName(value = "time",alternate = {"createTime"})
    private Date time;
    /**
     * 消息是否已读
     */
    @SerializedName(value = "isReaded",alternate = {"status"})
    private Boolean isReaded ;
    /**
     * 设备id
     */
    @SerializedName(value = "deviceId",alternate = {"did"})
    private String deviceId;

    /**
     * 消息配图
     */
    @SerializedName(value = "icon",alternate = {"img"})
    private String icon;


    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public Long getSendId() {
        return sendId;
    }

    public void setSendId(Long sendId) {
        this.sendId = sendId;
    }

    public Long getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Long receiveId) {
        this.receiveId = receiveId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Boolean getReaded() {
        return isReaded;
    }

    public void setReaded(Boolean readed) {
        isReaded = readed;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Message{" +
                "mid=" + mid +
                ", sendId=" + sendId +
                ", receiveId=" + receiveId +
                ", title='" + title + '\'' +
                ", msg='" + msg + '\'' +
                ", time=" + time +
                ", isReaded=" + isReaded +
                ", deviceId='" + deviceId + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        return mid != null ? mid.equals(message.mid) : message.mid == null;

    }

    @Override
    public int hashCode() {
        return mid != null ? mid.hashCode() : 0;
    }
}
