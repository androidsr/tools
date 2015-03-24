package com.sirui.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.Border;

import com.sirui.utils.ActionUtils;

public class MainUI extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JTextArea oldTxt, newTxt;
	private JMenuBar mb;
	private JMenu fmtMenu,connectMenu;
    private JMenuItem tableBean, beanGrid,tableGrid,sqlGrid, beanExtModel,tableExtModel;
    private JMenuItem connectFile,connectTxt;
    private Color color = new Color(192, 192, 192);
    private Border border = BorderFactory.createMatteBorder(5, 5, 5, 5, color);
    private Border lineBorder = BorderFactory.createLineBorder(color);
    
	public MainUI() {
		setTitle("工具");
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(getOwner());
		setLayout(new BorderLayout(5, 5));
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		addMenu();
		addLeft();
		addRight();
		setVisible(true);
	}
	
	public void addMenu() {
		
		mb = new JMenuBar();
        fmtMenu = new JMenu("文本转换");
        connectMenu = new JMenu("通讯功能");
        mb.setBorder(lineBorder);
        fmtMenu.setBorder(border);
        connectMenu.setBorder(border);
        
        tableBean = new JMenuItem("表>对象");
        tableGrid = new JMenuItem("表>Grid");
        sqlGrid = new JMenuItem("SQL>Grid");
        beanGrid = new JMenuItem("对象>Grid");
        beanExtModel = new JMenuItem("对象>Ext模型");
        tableExtModel = new JMenuItem("表>Ext模型");
        
        connectFile = new JMenuItem("文件传送");
        connectTxt = new JMenuItem("报文收发");
        
        fmtMenu.add(tableBean);
        fmtMenu.addSeparator();
        fmtMenu.add(tableGrid);
        fmtMenu.addSeparator();
        fmtMenu.add(sqlGrid);
        fmtMenu.addSeparator();
        fmtMenu.add(beanGrid);
        fmtMenu.addSeparator();
        fmtMenu.add(beanExtModel);
        fmtMenu.addSeparator();
        fmtMenu.add(tableExtModel);
        fmtMenu.addSeparator();
        
        connectMenu.add(connectFile);
        connectMenu.addSeparator();
        connectMenu.add(connectTxt);
        
        mb.add(fmtMenu);
        mb.add(connectMenu);
        setJMenuBar(mb);
        tableBean.addActionListener(this);
        tableGrid.addActionListener(this);
        beanGrid.addActionListener(this);
        beanExtModel.addActionListener(this);
        tableExtModel.addActionListener(this);
        sqlGrid.addActionListener(this);
        connectFile.addActionListener(this);
        connectTxt.addActionListener(this);
	}

	public void addLeft() {
		JPanel mPanel = new JPanel();
		mPanel.setLayout(new BorderLayout());
		oldTxt = new JTextArea();
		JScrollPane jp = new JScrollPane(oldTxt);
		jp.setPreferredSize(new Dimension(400, 0));
		mPanel.add(jp, BorderLayout.CENTER);
		getContentPane().add(mPanel, BorderLayout.CENTER);
	}

	public void addRight() {
		JPanel mPanel = new JPanel();
		mPanel.setLayout(new BorderLayout());
		newTxt = new JTextArea();
		JScrollPane jp = new JScrollPane(newTxt);
		jp.setPreferredSize(new Dimension(400, 0));
		mPanel.add(jp, BorderLayout.EAST);
		getContentPane().add(mPanel, BorderLayout.EAST);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String key = e.getActionCommand();
		if(key.equals(tableBean.getText())){
			newTxt.setText(ActionUtils.tableBean(oldTxt.getText()));
			
		} else if(key.equals(beanGrid.getText())){
			newTxt.setText(ActionUtils.beanGrid(oldTxt.getText()));
			
		} else if(key.equals(tableGrid.getText())){
			newTxt.setText(ActionUtils.tableGrid(oldTxt.getText()));
			
		}else if(key.equals(sqlGrid.getText())){
			newTxt.setText(ActionUtils.sqlGrid(oldTxt.getText()));
			
		} else if(key.equals(beanExtModel.getText())){
			newTxt.setText(ActionUtils.beanExtModel(oldTxt.getText()));
			
		} else if(key.equals(tableExtModel.getText())){
			newTxt.setText(ActionUtils.tableExtModel(oldTxt.getText()));
		} else if(key.equals(connectFile.getText())){
			new FileOutPutUI();
			
		} else if(key.equals(connectTxt.getText())){
			new TextOutPutUI();
			
		}
	}
	
}
