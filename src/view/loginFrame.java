package view;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ConnectDB.Connect;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;

public class loginFrame extends JFrame {
    
	private JPanel contentPane;
	private final JLabel lblAvtUser = new JLabel("");
	private JTextField textField;
	private JPasswordField passwordField;
	private JButton btnNewButton;
	private JLabel lbliconPass_1;
	private JPasswordField passwordField_1;
	private JLabel lblPort;
	private JTextField txtport;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginFrame frame = new loginFrame();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public loginFrame() {
		this.setTitle("BOX CHAT");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setSize(402, 359);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitlLogin = new JLabel("LOGIN");
		lblTitlLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitlLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitlLogin.setBounds(2, 36, 386, 42);
		contentPane.add(lblTitlLogin);
		
		lblAvtUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblAvtUser.setBounds(27, 88, 42, 42);
		lblAvtUser.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(loginFrame.class.getResource("user2.png"))));
		contentPane.add(lblAvtUser);
		
		textField = new JTextField();
		textField.setBounds(115, 88, 239, 42);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lbliconPass = new JLabel("");
		lbliconPass.setHorizontalAlignment(SwingConstants.CENTER);
		lbliconPass.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(loginFrame.class.getResource("pass-icon.png"))));
		lbliconPass.setBounds(27, 165, 42, 42);
		contentPane.add(lbliconPass);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(115, 164, 239, 43);
		contentPane.add(passwordField);
		
		btnNewButton = new JButton("LOGIN");
		btnNewButton.setBackground(new Color(255, 0, 0));
		btnNewButton.setBounds(143, 291, 100, 21);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dangnhap();
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(btnNewButton);
		
		JButton btnSignup = new JButton("SIGN UP");
		btnSignup.setBackground(new Color(0, 255, 255));
		btnSignup.setBounds(300, 10, 85, 21);
		btnSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new signupFrame();
				dispose();
			}
		});
		contentPane.add(btnSignup);
		
		lblPort = new JLabel("");
		lblPort.setHorizontalAlignment(SwingConstants.CENTER);
		lblPort.setBounds(27, 238, 42, 42);
		lblPort.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(loginFrame.class.getResource("connect.png"))));
		contentPane.add(lblPort);
		
		txtport = new JTextField("1111");
		txtport.setBounds(115, 238, 239, 43);
		contentPane.add(txtport);
		
		URL url = loginFrame.class.getResource("messenger-icon.png");
    	Image img = Toolkit.getDefaultToolkit().createImage(url);
    	this.setIconImage(img);
    	this.setResizable(false);
    	this.setLocationRelativeTo(null);
    	this.setVisible(true);

	}
	public void dangnhap () throws UnknownHostException, IOException {
		Connect con;
		try {
			con = new Connect();
			int kq = con.login(textField.getText(), passwordField.getText());
			
			if (kq == 1) {
				JOptionPane.showMessageDialog(null, "Đăng nhập thành công");
				dispose();
				new clientFrame(con.getName(textField.getText()), Integer.parseInt(txtport.getText()));
			} 
			else if (kq == 0 ) {
				JOptionPane.showMessageDialog(null, "Thông tin đăng nhập không chính xác");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
}
	
	
}
