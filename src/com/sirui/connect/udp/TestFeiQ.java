package com.sirui.connect.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;

public class TestFeiQ {
	public static void main(String[] args) {
		DatagramSocket socket;
		InetAddress address;

		long IPMSG_SENDMSG = 0x00000020;

		String SENDER = "TEST";
		String HOST = "Localhost";
		String MSG_CONTENT = "Hello World1212!";
		try {
			socket = new DatagramSocket();
			address = InetAddress.getByName("192.168.0.58");// 发送给消息的地址

			byte[] buffer = ("1:" + new Date().getTime() + ":" + SENDER + ":"+ HOST + ":" + IPMSG_SENDMSG + ":" + MSG_CONTENT).getBytes("gbk");

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length,address, 2425);
			socket.send(packet); // 发送报文

			packet = new DatagramPacket(buffer, buffer.length);
			socket.receive(packet);// 接收回应

			String message = new String(packet.getData()); // 得到报文信息

			System.out.println(message); // 显示对方返回的信息
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		}

	}

}
