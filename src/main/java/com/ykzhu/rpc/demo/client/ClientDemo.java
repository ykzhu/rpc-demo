package com.ykzhu.rpc.demo.client;

import com.ykzhu.rpc.clinet.ServiceProxyClient;
import com.ykzhu.rpc.demo.server.RpcService;

/**
 * 客户端发起一次服务调用
 *
 */
public class ClientDemo {

    public static void main(String[] args) {
        System.out.println("----------start invoke----------------");
        //模拟取代了spring获取服务bean实例
        //客户端调用的远程服务接口都被“客户端的代理（见类ServiceProxyClient）所代理，在代理类中执行了rpc过程”
        RpcService service = ServiceProxyClient.getInstance(RpcService.class); 
        //调用服务
        System.out.println(service.sayHi("RPC World"));
        System.out.println("----------end invoke----------------");
    }
}
