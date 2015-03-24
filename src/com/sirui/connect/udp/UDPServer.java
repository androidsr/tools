package com.sirui.connect.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.sirui.common.Constants;

public class UDPServer {
	public static Set<String> set = new HashSet<String>();
	public static DatagramSocket serverSocket;
	public static void init(JComboBox<String> ip) {
		try {
			if(serverSocket == null)
				serverSocket = new DatagramSocket(Constants.PORT);
			else 
				return;
			byte[] receiveData = new byte[1024];
			while (true) {
				DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
				serverSocket.receive(receivePacket);
				InetAddress IPAddress = receivePacket.getAddress();
				set.add(IPAddress.getHostName());
				ip.setModel(new DefaultComboBoxModel(set.toArray()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}