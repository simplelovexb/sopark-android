package cn.suxiangbao.sosark.entity;

import java.util.Collection;
import java.util.Map;

/**
 * Created by sxb on 2017/2/19.
 */
public class RetMsgObj {
    private int code;
    private String msg;
    private Integer size;

    public RetMsgObj(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public static RetMsgObj genMsgObj(int code) {
        return new ObjRetMsgObj(code, null, null);
    }

    public static RetMsgObj genMsgObj(int code, String msg) {
        return new ObjRetMsgObj(code, msg, null);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static RetMsgObj genMsgObj(int code, String msg, Object data) {
        if (data instanceof Collection) {
            return new CollRetMsgObj<>(code, msg, (Collection) data);
        } else if (data instanceof Map) {
            return new MapRetMsgObj<>(code, msg, (Map) data);
        } else {
            return new ObjRetMsgObj(code, msg, data);
        }
    }


    public static class ObjRetMsgObj extends RetMsgObj {
        private Object data;

        public ObjRetMsgObj(int code, String msg, Object data) {
            super(code, msg);
            this.data = data;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

    }

    public static class CollRetMsgObj<T> extends RetMsgObj {
        private Collection<T> data;

        public CollRetMsgObj(int code, String msg, Collection<T> data) {
            super(code, msg);
            this.data = data;
        }

        public Collection<T> getData() {
            return data;
        }

        public void setData(Collection<T> data) {
            this.data = data;
        }

    }

    public static class MapRetMsgObj<K, V> extends RetMsgObj {
        private Map<K, V> data;

        public MapRetMsgObj(int code, String msg, Map<K, V> data) {
            super(code, msg);
            this.data = data;
        }

        public Map<K, V> getData() {
            return data;
        }

        public void setData(Map<K, V> data) {
            this.data = data;
        }

    }
}
