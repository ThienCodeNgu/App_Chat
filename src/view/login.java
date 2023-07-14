package View;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Connect.Connect;
import Model.Account;
import Model.LoginServer;
import Model.ServerThread;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.SystemColor;
import java.awt.Color;

public class login extends JFrame {

	private JPanel contentPane;
	private static JTextField LoginUserText;
	private static JPasswordField loginPassText;
	public static Connect con;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login frame = new login();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public login() {
		this.setTitle("Dolphin Chat");
		setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\java2\\DolphinChat\\Images\\dolphin.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 381);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel LabelTitle = new JLabel("Đăng Nhập");
		LabelTitle.setFont(new Font("Tahoma", Font.BOLD, 25));
		LabelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		LabelTitle.setBounds(10, 60, 436, 33);
		contentPane.add(LabelTitle);

		JLabel LabelUserIcon = new JLabel("");
		LabelUserIcon.setHorizontalAlignment(SwingConstants.CENTER);
		LabelUserIcon.setIcon(new ImageIcon("D:\\java2\\DolphinChat\\Images\\user2.png"));
		LabelUserIcon.setBounds(44, 125, 58, 55);
		contentPane.add(LabelUserIcon);

		JLabel LabelPassIcon = new JLabel("");
		LabelPassIcon.setIcon(new ImageIcon("D:\\java2\\DolphinChat\\Images\\padlock.png"));
		LabelPassIcon.setHorizontalAlignment(SwingConstants.CENTER);
		LabelPassIcon.setBounds(44, 205, 58, 55);
		contentPane.add(LabelPassIcon);

		LoginUserText = new JTextField();
		LoginUserText.setFont(new Font("Tahoma", Font.PLAIN, 18));
		LoginUserText.setBounds(147, 129, 279, 51);
		contentPane.add(LoginUserText);
		LoginUserText.setColumns(10);

		loginPassText = new JPasswordField();
		loginPassText.setFont(new Font("Tahoma", Font.PLAIN, 18));
		loginPassText.setBounds(147, 205, 279, 55);
		contentPane.add(loginPassText);

		JButton LoginBtn = new JButton("Đăng Nhập");
		LoginBtn.setBackground(SystemColor.activeCaption);
		LoginBtn.setFont(new Font("Tahoma", Font.BOLD, 15));
		LoginBtn.setBounds(163, 289, 121, 33);
		LoginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginClient();
			}
		});
		contentPane.add(LoginBtn);

		JButton SignupBtn = new JButton("Đăng Ký");
		SignupBtn.setBackground(SystemColor.activeCaption);
		SignupBtn.setFont(new Font("Tahoma", Font.BOLD, 15));
		SignupBtn.setBounds(305, 10, 121, 33);
		SignupBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signup();
			}
		});
		contentPane.add(SignupBtn);

		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public  void login() {
		try {
			con = new Connect();

			int loginCheck = con.login(LoginUserText.getText(), loginPassText.getText());
			if (loginCheck == 1) {
				JOptionPane.showMessageDialog(null, "Đăng nhập thành công !!");
				String name = con.getName(LoginUserText.getText());
				new homeFrame(name, 1111,LoginUserText.getText());
				this.dispose();
				

			} else {
				JOptionPane.showMessageDialog(null, "Tên đăng nhập hoặc mặt khẩu không chính xác");
				
				
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void signup() {
		this.dispose();
		new signup();
	}
	
	public void loginClient () {
		if (LoginUserText.getText().equals("") || loginPassText.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Vui lòng điền đủ thông tin!!");
		}else {
			try {
				Socket socket = new Socket("localhost", 2004);
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				dos.writeUTF(LoginUserText.getText());
				dos.flush();
				
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				String kq = dis.readUTF();
				if (kq.equals("yes")) {
					login();
				}else if (kq.equals("no")) {
					JOptionPane.showMessageDialog(null, "Tài khoản đã được đăng nhập!");
				}
				
			} catch (IOException e) {
				// TODO: handle exception
			}
		}
		
	}

}
