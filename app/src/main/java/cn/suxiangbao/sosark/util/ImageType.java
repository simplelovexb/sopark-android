package cn.suxiangbao.sosark.util;

/**
 * Created by sxb on 2017/2/26.
 */

public enum ImageType {
    /**
     * JPG格式
     */
    JPG("image/jpg"),
    /**
     * PNG格式
     */
    PNG("image/png"),
    /**
     * GIF格式
     */
    GIF("image/gif"),
    /**
     * JPEG格式
     */
    JPEG("image/jpeg");

    private String value;

    private ImageType(String value) {
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}