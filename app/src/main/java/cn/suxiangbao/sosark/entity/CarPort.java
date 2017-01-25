package cn.suxiangbao.sosark.entity;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/1/2.
 * 车位实体类
 */
public class CarPort {
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
    @SerializedName(value = "coordinate", alternate = {"point", "geoPoint"})
    private GeoPoint coordinate;

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

    public GeoPoint getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(GeoPoint coordinate) {
        this.coordinate = coordinate;
    }

    public String toString() {
        return "CarPort{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", comment='" + comment + '\'' +
                ", coordinate=" + coordinate +
                '}';
    }

}
