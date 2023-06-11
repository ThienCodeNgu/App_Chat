package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.SQLException;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ConnectDB.Connect;

public class signupFrame extends JFrame {

	private JPanel contentPane;
	private JLabel lblAvtUser;
	private JTextField textField;
	private JPasswordField passwordField;
	private JButton btnNewButton;
	public Connect con ;
	private JLabel lbliconPass_1;
	private JPasswordField passwordField_1;
	private JTextField txtFullname;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					signupFrame frame = new signupFrame();
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
	public signupFrame() {
		this.setTitle("BOX CHAT");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setSize(402, 362);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitlLogin = new JLabel("SIGN UP");
		lblTitlLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitlLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitlLogin.setBounds(2, 36, 386, 42);
		contentPane.add(lblTitlLogin);
		
		lblAvtUser = new JLabel();
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
		
		btnNewButton = new JButton("SIGN UP");
		btnNewButton.setBounds(142, 294, 100, 21);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signUP(textField.getText(),txtFullname.getText(),passwordField.getText());
			}
		});
		contentPane.add(btnNewButton);
		
		lbliconPass_1 = new JLabel("");
		lbliconPass_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbliconPass_1.setBounds(27, 244, 42, 42);
		contentPane.add(lbliconPass_1);
		
		txtFullname = new JTextField();
		txtFullname.setBounds(115, 244, 239, 43);
		contentPane.add(txtFullname);
		
		
		
		URL url = loginFrame.class.getResource("messenger-icon.png");
    	Image img = Toolkit.getDefaultToolkit().createImage(url);
    	this.setIconImage(img);
    	this.setResizable(false);
    	this.setLocationRelativeTo(null);
    	this.setVisible(true);
	}
    public void signUP(String username, String fullname , String pass) {

    try {
		con = new Connect();
		int check = con.checkUsername(textField.getText());
		if (check==1) {
			JOptionPane.showMessageDialog(null, "Tên đăng nhập đã tồn tại");
		}
		else {
			
			int signup = con.signup(username, fullname, pass);
			if (signup ==1) {
				JOptionPane.showMessageDialog(null, "Đăng ký thành công!");
				dispose();
				new loginFrame();
			}
			else {
				JOptionPane.showMessageDialog(null, "Đăng ký không thành công!");
			}
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
    
    }
}
