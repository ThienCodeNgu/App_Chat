package View;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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

import Connect.Connect;

public class signup extends JFrame {

	private JPanel contentPane;
	private JTextField SignupUserText;
	private JPasswordField SignupPassText;
	public static Connect con;
	private JTextField signupFullName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					signup frame = new signup();
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
	public signup() {
		this.setTitle("Dolphin Chat");
		setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\java2\\DolphinChat\\Images\\dolphin.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 447);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel LabelTitle = new JLabel("Đăng Ký");
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

		SignupUserText = new JTextField();
		SignupUserText.setFont(new Font("Tahoma", Font.PLAIN, 18));
		SignupUserText.setBounds(147, 129, 279, 51);
		contentPane.add(SignupUserText);
		SignupUserText.setColumns(10);

		SignupPassText = new JPasswordField();
		SignupPassText.setFont(new Font("Tahoma", Font.PLAIN, 18));
		SignupPassText.setBounds(147, 205, 279, 55);
		contentPane.add(SignupPassText);

		signupFullName = new JTextField();
		signupFullName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		signupFullName.setColumns(10);
		signupFullName.setBounds(147, 289, 279, 51);
		contentPane.add(signupFullName);

		JLabel LabelUserIcon_1 = new JLabel("");
		LabelUserIcon_1.setIcon(new ImageIcon("D:\\java2\\DolphinChat\\Images\\id-card.png"));
		LabelUserIcon_1.setHorizontalAlignment(SwingConstants.CENTER);
		LabelUserIcon_1.setBounds(44, 285, 58, 55);
		contentPane.add(LabelUserIcon_1);

		JButton SignupBtn = new JButton("Đăng Ký");
		SignupBtn.setBackground(SystemColor.activeCaption);
		SignupBtn.setFont(new Font("Tahoma", Font.BOLD, 15));
		SignupBtn.setBounds(163, 367, 121, 33);
		SignupBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signup();
			}
		});
		contentPane.add(SignupBtn);

		JButton LoginBtn = new JButton("Đăng Nhập");
		LoginBtn.setBackground(SystemColor.activeCaption);
		LoginBtn.setFont(new Font("Tahoma", Font.BOLD, 15));
		LoginBtn.setBounds(305, 10, 121, 33);
		contentPane.add(LoginBtn);
		LoginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();

			}
		});

		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void signup() {
		try {
			con = new Connect();
			int signupCheck = con.signup(SignupUserText.getText(), signupFullName.getText(), SignupPassText.getText());
			if (signupCheck == 1) {
				JOptionPane.showMessageDialog(null, "Đăng ký thành công!");
				this.dispose();
				new login();
			} else {
				JOptionPane.showMessageDialog(null, "Đăng ký không thành công, Tên đăng nhập đã tồn tại!");
			}

		} catch (SQLException e) {
			System.out.println("Lỗi đăng ký!!!");
		}
	}

	public void login() {
		this.dispose();
		new login();
	}

}
