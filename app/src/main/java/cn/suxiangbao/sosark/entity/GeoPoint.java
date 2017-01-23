package cn.suxiangbao.sosark.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/1/2.
 * 地理位置
 */
public class GeoPoint {

    @SerializedName(value = "longitude")
    private double longitude;
    @SerializedName(value = "latitude")
    private double latitude;

    public GeoPoint() {
    }

    public GeoPoint(double longitude, double latitude) {

        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "GeoPoint{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeoPoint geoPoint = (GeoPoint) o;

        if (Double.compare(geoPoint.longitude, longitude) != 0) return false;
        return Double.compare(geoPoint.latitude, latitude) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(longitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
