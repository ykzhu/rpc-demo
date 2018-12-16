package com.ykzhu.rpc.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

import com.ykzhu.rpc.ServiceProtocol;

/**
 * 
 *
 */
class MyRunnable implements Runnable {

    private Socket socket;

    private final int MAX_PACKAGE_SIZE = 10240;
    
    public MyRunnable(Socket socket) {
        this.socket = socket;
    }

    public void run() {

    	InputStream is = null;
    	OutputStream os = null;
    	
        try{
        	is = socket.getInputStream();
        	os = socket.getOutputStream();
        	
            byte[] data = new byte[MAX_PACKAGE_SIZE];
            int len = is.read(data);

            ServiceProtocol.ProtocolModel model = ServiceProtocol.protocol
                .decode(Arrays.copyOfRange(data, 0, len), ServiceProtocol.ProtocolModel.class);
            //调用处理器处理来自客户端的请求
            Object object = ServiceProcessor.processor.process(model);
            os.write(ServiceProtocol.protocol.encode(object));
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	// close socket
        	try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       }
    }
}