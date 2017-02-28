package cn.suxiangbao.sosark.util;

/**
 * Created by sxb on 2017/2/26.
 */

public enum ActType {
    /**
     * 旋转
     */
    ROTATE("rotate"),
    /**
     * 裁剪
     */
    CROP("crop"),
    /**
     * 缩放
     */
    RESIZE("resize");

    private String value;

    private ActType(String value) {
        this.value=value;
    }
    public String getValue() {
        return value;
    }
}