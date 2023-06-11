package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import model.serverThread;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;

public class serverFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private serverThread sv ;
	public static JLabel lblipAddress;
	private JLabel lblStatus;
	public static JLabel lblNumber;
	public static DefaultListModel model;
	public static JList list;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					serverFrame frame = new serverFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public serverFrame() {
		this.setTitle("Dolphin-chat   SERVER");
		model = new DefaultListModel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 498);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbllogo = new JLabel("");
		lbllogo.setHorizontalAlignment(SwingConstants.CENTER);
		lbllogo.setBounds(0, 0, 180, 322);
		lbllogo.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(serverFrame.class.getResource("dolphin-big.png"))));
		contentPane.add(lbllogo);
		
		JLabel lblNewLabel_1 = new JLabel("PORT:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(10, 332, 71, 21);
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField("1111");
		textField.setBounds(103, 335, 77, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		list = new JList(model);
//		list.setBackground(Color.green);
		list.setBounds(190, 10, 486, 312);
		contentPane.add(list);
		
		JButton startbtn = new JButton("START");
		startbtn.setBackground(new Color(0, 255, 0));
		startbtn.setBounds(253, 333, 112, 23);
		startbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sv.listSK = new ArrayList<>();
				sv = new serverThread(Integer.parseInt(textField.getText()));
				sv.start();
				getIpAddress();
				lblStatus.setText("ON");
				lblStatus.setForeground(Color.green);
				
			}
		});
		contentPane.add(startbtn);
		
		JButton btnStop = new JButton("STOP");
		btnStop.setBackground(new Color(255, 0, 0));
		btnStop.setBounds(520, 333, 112, 23);
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					sv.dung();
					lblStatus.setText("OFF");
					lblStatus.setForeground(Color.red);
			}
		});
		contentPane.add(btnStop);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 373, 666, 78);
		contentPane.add(panel);
		panel.setBackground(new Color(0,206,209));
		
		
		Border title = BorderFactory.createTitledBorder("Server Information");
		
		panel.setBorder(title);
		panel.setLayout(null);
		
		JLabel lblsttTitle = new JLabel("Status:");
		lblsttTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblsttTitle.setBounds(10, 34, 78, 18);
		panel.add(lblsttTitle);
		
		JLabel lblIPtitle = new JLabel("IP Address:");
		lblIPtitle.setBounds(223, 32, 82, 21);
		panel.add(lblIPtitle);
		lblIPtitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblNCtitle = new JLabel("Number client:");
		lblNCtitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNCtitle.setBounds(444, 31, 100, 21);
		panel.add(lblNCtitle);
		
		lblipAddress = new JLabel("");
		lblipAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblipAddress.setBounds(315, 31, 106, 21);
		panel.add(lblipAddress);
		
		lblStatus = new JLabel("OFF");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblStatus.setForeground(Color.red);
		lblStatus.setBounds(98, 32, 106, 21);
		panel.add(lblStatus);
		
		lblNumber = new JLabel("0");
		lblNumber.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNumber.setBounds(550, 32, 106, 21);
		panel.add(lblNumber);
		
		
		URL url = serverFrame.class.getResource("dolphin.png");
    	Image img = Toolkit.getDefaultToolkit().createImage(url);
    	this.setIconImage(img);
    	this.setResizable(false);
    	this.setLocationRelativeTo(null);
	}
	public void getIpAddress() {
		try {
			InetAddress localhost = InetAddress.getLocalHost();
			String address = localhost.getHostAddress();
			lblipAddress.setText(address);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
