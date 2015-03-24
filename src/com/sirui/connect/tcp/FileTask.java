package com.sirui.connect.tcp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.sirui.common.Constants;

public class FileTask implements Runnable {
	
	private Socket socket;
	public FileTask(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			handleSocket();
		} catch (Exception e) {
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void handleSocket() {
		try {
			InputStream input = socket.getInputStream();
			
			byte [] buffer = new byte[4];
			int len;
			input.read(buffer);
			byte [] b = new byte[Integer.valueOf(new String(buffer))];
			len = input.read(b);
			String name = new String(b,0,len);
			File file = new File(Constants.PATH+File.separator+name);
			if(!file.exists())
				file.createNewFile();
			FileOutputStream fileOut = new FileOutputStream(file);
			buffer = new byte[1024];
			while((len = input.read(buffer)) != -1){
				fileOut.write(buffer,0,len);
				if(input.available() <= 0)
					break;
			}
			fileOut.flush();
			fileOut.close();
			
			OutputStream out = socket.getOutputStream();
			out.write("SUCCESS\n".getBytes());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				this.close();
			} catch (IOException e) {
			}
		}
	}
	
	private void close() throws IOException{
		if(!socket.isClosed()){
			socket.shutdownInput();
			socket.shutdownOutput();
			socket.close();
		}
	}
}
