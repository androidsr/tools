package com.sirui.connect.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TextIOClient {
	
	static Socket client;
	
	public static String sendConnect(String host,int port,String msg) {
		StringBuffer sb = new StringBuffer();
		try {
			client = new Socket(host, port);
			client.setSoTimeout(120 * 1000);
			client.setSoLinger(true, 100);
			
			OutputStream out = client.getOutputStream();
			InputStream in = client.getInputStream();
			out.write(msg.getBytes());
			out.flush();
			
			byte [] b = new byte[1024];
			int len = 0;
			while((len =in.read(b)) != -1){
				sb.append(new String(b,0,len));
				if(in.available()  == 0)
					break;
			}
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				close();
			} catch (IOException e) {
			}
		}
		
		return sb.toString();
	}
	
	private static void close() throws IOException{
		if(!client.isClosed()){
			client.shutdownInput();
			client.shutdownOutput();
			client.close();
		}
	}
}
