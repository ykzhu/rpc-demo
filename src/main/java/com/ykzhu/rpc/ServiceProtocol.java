package com.ykzhu.rpc;

import com.alibaba.fastjson.JSONObject;

/**
 * 使用JSON来序列化和反序列化RPC调用传递的数据
 * 由于JSON序列化组件比较弱，所以这边需要将执行调用方法相关的请求数据进行编码成ProtocolModel对象。
 *
 */
public class ServiceProtocol {

    public static final ServiceProtocol protocol = new ServiceProtocol();

    /**
     * 将对象序列化为字符串字节
     */
    public byte[] encode(Object o) {
        return JSONObject.toJSONBytes(o);
    }

    /**
     * 反序列化成对象
     */
    public <T> T decode(byte[] data, Class<T> clazz) {
        return JSONObject.parseObject(data, clazz);
    }

    /**
     * 服务请求的编解码模型
     */
    public static class ProtocolModel {
        private String clazz;
        private String method;
        private String[] argTypes;
        private Object[] args;
		public String getClazz() {
			return clazz;
		}
		public void setClazz(String clazz) {
			this.clazz = clazz;
		}
		public String getMethod() {
			return method;
		}
		public void setMethod(String method) {
			this.method = method;
		}
		public String[] getArgTypes() {
			return argTypes;
		}
		public void setArgTypes(String[] argTypes) {
			this.argTypes = argTypes;
		}
		public Object[] getArgs() {
			return args;
		}
		public void setArgs(Object[] args) {
			this.args = args;
		}

    }
}
