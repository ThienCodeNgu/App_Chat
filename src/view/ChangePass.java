package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Connect.Connect;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JButton;

public class ChangePass extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private String username;
	private Connect con;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ChangePass(String username) {
		this.setTitle("Change password");
		setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\java2\\DolphinChat\\Images\\dolphin.png"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 268, 156);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 10, 236, 30);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton change = new JButton("Đổi mật khẩu");
		change.setBounds(10, 74, 236, 21);
		change.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (textField.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Nhập mật khẩu mới!");
					}else {
						con = new Connect();
						int kq = con.changePass(textField.getText(), username );
						if (kq == 1) {
							JOptionPane.showMessageDialog(null, "Đổi mật khẩu thành công!");
							dispose();
						}else {
							JOptionPane.showMessageDialog(null, "Đổi mật khẩu thất bại");
						}
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(change);
		
		
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}
}
