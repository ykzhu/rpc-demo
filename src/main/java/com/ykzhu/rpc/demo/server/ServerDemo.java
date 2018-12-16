package com.ykzhu.rpc.demo.server;

import com.ykzhu.rpc.server.ServerRemoter;
import com.ykzhu.rpc.server.ServiceProcessor;

/**
 * 服务端测试main执行代码
 * 启动服务，同时发布注册服务
 */
public class ServerDemo {

    public static void main(String[] args) throws Exception {

        // 发布接口
        ServiceProcessor.processor.publish(RpcService.class,new RpcServiceImpl());

        // 启动server
        ServerRemoter remoter = new ServerRemoter();
        remoter.startServer(9999);

    }
}


