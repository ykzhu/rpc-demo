package com.ykzhu.rpc.clinet;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

/**
 * remote模块是提供服务端和客户端通信的功能。因此，在服务端需要起一个端口来监听外部的请求，在客户端则负责发送请求，接收响应数据。
 * 客户端通信组件，客户端和外部服务端数据交互时使用
 */
 public class ClientRemoter {

     public static final ClientRemoter client = new ClientRemoter();

     public byte[] getDataRemote(byte[] requestData) {

         try (Socket socket = new Socket()) {
             socket.connect(new InetSocketAddress("127.0.0.1", 9999));//要和服务发布端口对应
             socket.getOutputStream().write(requestData);
             socket.getOutputStream().flush();

             byte[] data = new byte[10240];
             int len = socket.getInputStream().read(data);

             return Arrays.copyOfRange(data, 0, len);
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
     }
 }