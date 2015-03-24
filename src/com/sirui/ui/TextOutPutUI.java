package com.sirui.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

import com.sirui.common.Constants;
import com.sirui.connect.tcp.TextIOClient;

public class TextOutPutUI extends JDialog implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JTextArea oldTxt, newTxt;
    private Color color = new Color(192, 192, 192);
    private Border lineBorder = BorderFactory.createLineBorder(color);
    private int height = 25;
    private JTextField ip,port;
    private JButton subTxt,close;
    private JLabel ipL,portL;
    
	public TextOutPutUI() {
		setSize(600, 400);
		setResizable(false);
		setModal(true);
		setLayout(new BorderLayout(5, 5));
		setLocationRelativeTo(getOwner());
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		addTop();
		addCenter();
		addBottom();
		setVisible(true);
	}
	
	public void addTop(){
		JPanel mPanel = new JPanel();
		
		mPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 1, 1));
		ipL = new JLabel("连接地址 : ");
		ipL.setPreferredSize(new Dimension(70, height));
		
		ip = new JTextField();
		ip.setText("localhost");
		ip.setPreferredSize(new Dimension(140, height));
		
		portL = new JLabel("连接端口 : ");
		portL.setPreferredSize(new Dimension(70, height));
		
		port = new JTextField();
		port.setText(Constants.PORT+"");
		port.setPreferredSize(new Dimension(70, height));
		
		close = new JButton();
		close.setPreferredSize(new Dimension(50, height));
		close.setText("关闭");
		
		mPanel.add(ipL);
		mPanel.add(ip);
		mPanel.add(portL);
		mPanel.add(port);
		mPanel.add(close);
		mPanel.setBorder(lineBorder);
		getContentPane().add(mPanel, BorderLayout.NORTH);
		close.addActionListener(this);
	}
	
	private void addCenter(){
		JPanel mPanel = new JPanel();
		mPanel.setLayout(new BorderLayout(5,5));
		mPanel.setPreferredSize(new Dimension(220,180));
		
		oldTxt = new JTextArea();
		JScrollPane oldSc = new JScrollPane(oldTxt);
		oldSc.setPreferredSize(new Dimension(220,180));
		mPanel.add(oldSc,BorderLayout.CENTER);
		
		getContentPane().add(mPanel, BorderLayout.CENTER);
	}
	
	private void addBottom() {
		JPanel mPanel = new JPanel();
		mPanel.setLayout(new BorderLayout(5,5));
		mPanel.setPreferredSize(new Dimension(220,180));
		newTxt = new JTextArea();
		JScrollPane newSc = new JScrollPane(newTxt);
		//newSc.setPreferredSize(new Dimension(220,180));
		
		subTxt = new JButton();
		subTxt.setPreferredSize(new Dimension(50, height));
		subTxt.setText("发送");
		subTxt.setBorder(lineBorder);
		
		mPanel.add(newSc,BorderLayout.CENTER);
		mPanel.add(subTxt,BorderLayout.SOUTH);
		subTxt.addActionListener(this);
		getContentPane().add(mPanel, BorderLayout.SOUTH);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String key = e.getActionCommand();
		
		if (key.equals(subTxt.getText())){
			new Thread(new Runnable() {
				@Override
				public void run() {			
					String msg = TextIOClient.sendConnect(ip.getText(), Integer.parseInt(port.getText()), oldTxt.getText());
					newTxt.setText(msg);
				}
			}).start();
		} else if(key.equals(close.getText())){
			this.dispose();
		}
	}
	
}
