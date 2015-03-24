package com.sirui.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.sirui.common.Constants;
import com.sirui.connect.tcp.FileIOClient;
import com.sirui.connect.tcp.FileIOServer;
import com.sirui.connect.udp.UDPClient;
import com.sirui.connect.udp.UDPServer;

public class FileOutPutUI extends JDialog implements ActionListener{
	private static final long serialVersionUID = 1L;
    private Color color = new Color(192, 192, 192);
    private Border lineBorder = BorderFactory.createLineBorder(color);
    private int height = 25;
    private JTextField port,lPort;
    private JComboBox<String> ip;
    private JTextField fileDir;
    private JButton select,submit,close;
    private JLabel ipL,portL,lportL;
    private JFileChooser jfc;
    private JCheckBox box;
    
	public FileOutPutUI() {
		setSize(600, 120);
		setResizable(false);
		setModal(true);
		setLocationRelativeTo(getOwner());
		jfc = new JFileChooser();
		jfc.setCurrentDirectory(new File(Constants.PATH));
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		addTop();
		addCenter();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				UDPServer.init(ip);
			}
		}).start();
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				UDPClient.init();
				ip.setModel(new DefaultComboBoxModel(UDPServer.set.toArray()));
			}
		}, new Date(),30 * 1000);
		
		setVisible(true);
	}
	
	public void addTop(){
		JPanel mPanel = new JPanel();
		
		mPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 1, 1));
		ipL = new JLabel("连接地址 : ");
		ipL.setPreferredSize(new Dimension(70, height));
		
		ip = new JComboBox<String>();
		ip.setPreferredSize(new Dimension(110, height));
		
		portL = new JLabel("连接端口 : ");
		portL.setPreferredSize(new Dimension(70, height));
		
		port = new JTextField();
		port.setText(Constants.PORT+"");
		port.setPreferredSize(new Dimension(60, height));
		
		lportL = new JLabel("本地服务端口 : ");
		lportL.setPreferredSize(new Dimension(90, height));
		
		lPort= new JTextField();
		lPort.setText(Constants.PORT+"");
		lPort.setPreferredSize(new Dimension(60, height));
		
		box = new JCheckBox();
		box.setText("启动文件服务器");
		box.setPreferredSize(new Dimension(120, height));
		
		mPanel.add(ipL);
		mPanel.add(ip);
		mPanel.add(portL);
		mPanel.add(port);
		mPanel.add(lportL);
		mPanel.add(lPort);
		mPanel.add(box);
		box.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				
				final int status = e.getStateChange();
				new Thread(new Runnable() {
					@Override
					public void run() {
						if(status == 1){
							try {
								FileIOServer.init(Integer.parseInt(lPort.getText()));
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						} else if(status == 2) {
							FileIOServer.close();
						}
					}
				}).start();
			}
		});
		mPanel.setBorder(lineBorder);
		getContentPane().add(mPanel, BorderLayout.NORTH);
	}
	
	private void addCenter() {
		JPanel mPanel = new JPanel();
		mPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 1, 1));
		fileDir = new JTextField();
		fileDir.setPreferredSize(new Dimension(450, height));
		
		select = new JButton();
		select.setPreferredSize(new Dimension(50, height));
		select.setText("选择");
		
		submit = new JButton();
		submit.setPreferredSize(new Dimension(80, height));
		submit.setText("传送文件");
		
		close = new JButton();
		close.setPreferredSize(new Dimension(50, height));
		close.setText("关闭");
		
		mPanel.add(fileDir);
		mPanel.add(select);
		mPanel.add(submit);
		mPanel.add(close);
		
		select.addActionListener(this);
		submit.addActionListener(this);
		close.addActionListener(this);
		getContentPane().add(mPanel, BorderLayout.CENTER);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String key = e.getActionCommand();
		
		if(key.equals(select.getText())){
			jfc.showOpenDialog(this);
			
			File file=jfc.getSelectedFile();
			if(file != null)
				fileDir.setText(file.getAbsolutePath());
			
		} else if(key.equals(submit.getText())){
			new Thread(new Runnable() {
				@Override
				public void run() {
					FileIOClient.sendConnect(ip.getSelectedItem().toString(), Integer.parseInt(port.getText()), new File(fileDir.getText()));
				}
			}).start();
			
		} else if(key.equals(close.getText())){
			this.dispose();
		}
	}
	
}
