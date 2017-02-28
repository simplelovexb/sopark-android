package cn.suxiangbao.sosark.entity;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/1/2.
 * 车位实体类
 */
public class CarPort implements Serializable{
    @SerializedName(value = "id", alternate = {"_id", "pid"})
    private Long id ;
    @SerializedName(value = "name", alternate = {"pName", "p_name"})
    private String name;
    /**
     * 车位所有人 的id
     */
    @SerializedName(value = "belongId", alternate = {"owner", "uid"})
    private Long owner;
    /**
     * 对车位的简单描述
     */
    @SerializedName(value = "comment")
    private String comment;
    /**
     * 车位所在的全球经纬度地址
     */
    @SerializedName(value = "coordinate", alternate = {"point", "geoPoint","location"})
    private Double[] coordinate;

    private Integer status ;

    private Integer verify;

    @SerializedName(value = "pic1")
    private String pic1 ;

    @SerializedName(value = "pic2")
    private String pic2 ;

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getVerify() {
        return verify;
    }

    public void setVerify(Integer verify) {
        this.verify = verify;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double[] getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Double[] coordinate) {
        this.coordinate = coordinate;
    }



    @Override
    public String toString() {
        return "CarPort{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", comment='" + comment + '\'' +
                ", coordinate=" + Arrays.toString(coordinate) +
                ", status=" + status +
                ", verify=" + verify +
                '}';
    }

    public static class Field{
        public static final String FIELD_COORDINATE = "coordinate";
        public static final String FIELD_ID = "pid";
        public static final String FIELD_STATUS = "status";
        public static final String FIELD_OWNER = "owner";
        public static final String FIELD_NAME = "name";
        public static final String FIELD_COMMENT = "comment";
        public static final String FIELD_PICS = "pics";

    }

}
