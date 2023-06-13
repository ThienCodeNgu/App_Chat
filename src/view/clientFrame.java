package view;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
		setBounds(100, 100, 700, 456);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		list = new JList(model);
		JScrollPane scroll = new JScrollPane(list);
		scroll.setBounds(21, 10, 655, 314);
		contentPane.add(scroll);
		list.setCellRenderer(new CustomListCellRenderer(name));
		
		
		txtSend = new JTextField();
		txtSend.setBounds(21, 390, 560, 19);
		contentPane.add(txtSend);
		txtSend.setColumns(10);
		
		JButton btnSend = new JButton("send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send(name);
			}
		});
		btnSend.setBounds(591, 389, 85, 21);
		contentPane.add(btnSend);
		
		txtSend.addKeyListener(new KeyAdapter() {
			 public void keyPressed(KeyEvent e) {
	                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	                    // Thực hiện những câu lệnh tương tự như khi click vào button
	                	btnSend.doClick();
	                }
	            }
		});
		
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
		txtSend.setText("");
	}
}
