package view;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.CustomListCellRenderer;
import model.clientThread;
import model.readClient;
import model.writeClient;

import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;

public class clientFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtSend;
	public static JList list;
	public static DefaultListModel model;

	
	public clientFrame(String name, int port) {
		this.setTitle(name);
		model = new DefaultListModel();
		start(port);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		list = new JList(model);
		list.setBounds(21, 10, 655, 314);
		contentPane.add(list);
		list.setCellRenderer(new CustomListCellRenderer(name));
		
		
		txtSend = new JTextField();
		txtSend.setBounds(21, 334, 560, 19);
		contentPane.add(txtSend);
		txtSend.setColumns(10);
		
		JButton btnSend = new JButton("send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send(name);
			}
		});
		btnSend.setBounds(591, 334, 85, 21);
		contentPane.add(btnSend);
		URL url = loginFrame.class.getResource("messenger-icon.png");
    	Image img = Toolkit.getDefaultToolkit().createImage(url);
    	this.setIconImage(img);
		this.setVisible(true);
		this.setResizable(false);
	}
	public void start (int port) {
		clientThread cl = new clientThread("localhost", port);
		cl.start();
	}
	public void send(String name) {
		writeClient w = new writeClient(clientThread.socket, txtSend.getText(),name);
		w.start();
	}
	public void colorList (String name) {
		while(true) {
			
		}
	}
}
