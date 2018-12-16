package com.ykzhu.rpc.server;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务端起一个端口监听服务，绑定到相关processor处理器上。
 * 所有的web容器都有类似这样的一个模块，类似“总管”，接待客户端的请求“客户”
 */
public class ServerRemoter {

    private static final ExecutorService executor =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void startServer(int port) throws Exception {

        final ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(port));
        System.out.println("-----------start server----------------");
        try {
            while (true) {
                final Socket socket = server.accept();
                executor.execute(new MyRunnable(socket));
            }
        } finally {
            server.close();
        }
    }
}
