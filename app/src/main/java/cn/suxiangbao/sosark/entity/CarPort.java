package cn.suxiangbao.sosark.entity;


/**
 * Created by Administrator on 2017/1/2.
 */
public class CarPort {
    private Long id ;
    private String name;
    private Long owner;
    private String comment;
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
