package com.ykzhu.rpc.demo.server;

/**
 * 服务实现
 */
public class RpcServiceImpl implements RpcService {
    public String sayHi(String name) {
        return "Hello," + name;
    }
}