package com.sirui.ui;

import javax.swing.JOptionPane;

public class Message {

	public static void errorMsg(String msg) {
		JOptionPane.showMessageDialog(null, msg, "错误",
				JOptionPane.ERROR_MESSAGE);
	}

	public static void successMsg(String msg) {
		JOptionPane.showMessageDialog(null, msg, "提示",
				JOptionPane.INFORMATION_MESSAGE);
	}
	

	public static boolean alertDialog(String msg){
		int ret = JOptionPane.showConfirmDialog(null, msg,"提示",JOptionPane.YES_NO_OPTION);
		if(ret == JOptionPane.YES_OPTION)
			return true;
		else 
			return false;
	}
}
