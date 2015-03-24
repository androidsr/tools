package com.sirui.connect.tcp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import com.sirui.ui.Message;

public class FileIOClient {
	
	static Socket client;
	
	public static void sendConnect(String host,int port,File file) {
		try {
			String path = file.getAbsolutePath();
			client = new Socket(host, port);
			client.setSoTimeout(120 * 1000);
			client.setSoLinger(true, 100);
			OutputStream out = client.getOutputStream();
			InputStream in = client.getInputStream();
			String fileName = path.substring(path.lastIndexOf("/")+1);
			FileInputStream fileInput = new FileInputStream(file);
			out.write(String.format("%04d", fileName.length()).getBytes());
			out.write(fileName.getBytes());
			int len;
			byte [] buffer = new byte[1024];
			while((len = fileInput.read(buffer)) != -1){
				out.write(buffer,0,len);
			}
			out.flush();
			BufferedReader buf = new BufferedReader(new InputStreamReader(in));
			if(buf.readLine().equals("SUCCESS")){
				Message.successMsg("发送成功。");
			}
			out.close();
			buf.close();
			fileInput.close();
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				close();
			} catch (IOException e) {
			}
		}
	}
	
	private static void close() throws IOException{
		if(!client.isClosed()){
			client.shutdownInput();
			client.shutdownOutput();
			client.close();
		}
	}
}
