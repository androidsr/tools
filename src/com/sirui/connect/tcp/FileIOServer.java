package com.sirui.connect.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.sirui.ui.Message;

public class FileIOServer {
	private static ServerSocket server;
	
	public static void init(int port) throws IOException {  
		server = new ServerSocket(port);
		while (true) {
			Socket socket = server.accept();
			if(Message.alertDialog(socket.getInetAddress().getCanonicalHostName()+"给你发送了文件，是否确认接收？")){				
				new Thread(new FileTask(socket)).start();  
			}
		}
	}
	
	public static void close(){
		try {
			if(!server.isClosed())
				server.close();
		} catch (IOException e) {
		}
	}
	
}
