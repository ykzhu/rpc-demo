package com.ykzhu.rpc.server;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.ykzhu.rpc.ServiceProtocol;

/**
 * 服务端接口方法定位处理器。作为一个组件，显然不应该在业务代码中嵌入一些非业务逻辑。
 * processor会根据序列化完了之后的请求数据来定位具体的处理逻辑，然后调用对应的业务代码来处理获取返回结果。
 */
public class ServiceProcessor {

    public static final ServiceProcessor processor = new ServiceProcessor();

    private static final ConcurrentMap<String, Object> PROCESSOR_INSTANCE_MAP = new ConcurrentHashMap<String, Object>();


    public boolean publish(Class clazz, Object obj) {
        return PROCESSOR_INSTANCE_MAP.putIfAbsent(clazz.getName(), obj) != null;
    }

    public Object process(ServiceProtocol.ProtocolModel model) {
        try {
            Class clazz = Class.forName(model.getClazz());

            Class[] types = new Class[model.getArgTypes().length];
            for (int i = 0; i < types.length; i++) {
                types[i] = Class.forName(model.getArgTypes()[i]);
            }

            Method method = clazz.getMethod(model.getMethod(), types);

            Object obj = PROCESSOR_INSTANCE_MAP.get(model.getClazz());
            if (obj == null) {
                return null;
            }

            return method.invoke(obj, model.getArgs());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}