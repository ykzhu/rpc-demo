package com.ykzhu.rpc.demo.server;

/**
 * 服务端的服务接口（需打包给客户端使用者声明服务用）
 */
public interface RpcService {
    String sayHi(String name);
}
