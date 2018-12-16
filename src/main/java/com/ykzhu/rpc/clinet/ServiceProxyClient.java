package com.ykzhu.rpc.clinet;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.ykzhu.rpc.ServiceProtocol;

/**
 * 客户端调用远程服务的代理客户端
 * 负责rpc过程：序列化请求，走网络到服务端，等待服务端执行真正的服务代码，然后服务端将结果通过网络返回给客户端代理，客户端代理再反序列化数据给调用方法
 *
 */
public class ServiceProxyClient {

	public static <T> T getInstance(Class<T> clazz) {
		return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, new ServiceProxy(clazz));
	}

	public static class ServiceProxy implements InvocationHandler {

		private Class clazz;

		public ServiceProxy(Class clazz) {
			this.clazz = clazz;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

			ServiceProtocol.ProtocolModel model = new ServiceProtocol.ProtocolModel();
			model.setClazz(clazz.getName());
			model.setMethod(method.getName());
			model.setArgs(args);

			String[] argType = new String[method.getParameterTypes().length];
			for (int i = 0; i < argType.length; i++) {
				argType[i] = method.getParameterTypes()[i].getName();
			}
			model.setArgTypes(argType);

			byte[] req = ServiceProtocol.protocol.encode(model);
			byte[] rsp = ClientRemoter.client.getDataRemote(req);
			return ServiceProtocol.protocol.decode(rsp, method.getReturnType());
		}
	}
}
