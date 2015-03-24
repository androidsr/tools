package com.sirui.connect.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.sirui.common.Constants;

public class UDPClient {
	
	public static void init(){
		try {
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName("255.255.255.255");
			
			DatagramPacket sendPacket = new DatagramPacket(new byte[1], 1, IPAddress,Constants.PORT);
			clientSocket.send(sendPacket);
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}