package View;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Connect.Connect;
import Model.Account;
import Model.ClientThread;
import Model.IconClient;
import Model.MyFile;
import Model.ReadClient;
import Model.ServerThread;
import Model.SetColorListChat;
import Model.WriteClient;
import Model.customListCellRenderer;
import Model.player_thread;
import Model.recorder_thread;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.SystemColor;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class homeFrame extends JFrame {
    private Connect con;
	private JPanel contentPane;
	private static String name;
	public static JList listAccountOnline;
	public static JList listChat;
	public static JList listFile;
	public static DefaultListModel modelListAccountOnline;
	public static DefaultListModel modelListChat;
	public static DefaultListModel modelListFile;
	 private String username;

	private JTextField text;
	private JTextField filenameText;
	private JButton SendFileBtn;
	public static JButton sendBtn;

	public static int port;
	final File[] fileToSend;
	int fileId;
	private JPanel panelFileSended;
	
	public static int voice_port = 8888;
	

	static TargetDataLine audio_in;
	private JButton micBtn;
    	
	
	public static boolean calling = false;
	private static JButton btnNewButton;
	private static JButton btnNewButton_1;
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

	public homeFrame(String name, int port, String username) {

		fileToSend = new File[1];
		this.name = name;
		this.port = port;
		this.username = username;
		modelListAccountOnline = new DefaultListModel<>();
		modelListChat = new DefaultListModel<>();
		modelListFile = new DefaultListModel<>();
		start(port);

		this.setTitle("Dolphin Chat");
		setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\java2\\DolphinChat\\Images\\dolphin.png"));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1218, 810);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBounds(10, 10, 1184, 45);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel usernameICon = new JLabel("");
		usernameICon.setIcon(new ImageIcon("D:\\java2\\DolphinChat\\Images\\name.png"));
		usernameICon.setBounds(10, 0, 60, 45);
		panel.add(usernameICon);

		JLabel labelNameUser = new JLabel(name);
		labelNameUser.setForeground(SystemColor.text);
		labelNameUser.setVerticalAlignment(SwingConstants.BOTTOM);
		labelNameUser.setFont(new Font("Tahoma", Font.PLAIN, 25));
		labelNameUser.setBounds(80, 0, 697, 45);
		panel.add(labelNameUser);

		JButton logoutBtn = new JButton("");
		logoutBtn.setIcon(new ImageIcon("D:\\java2\\DolphinChat\\Images\\check-out.png"));
		logoutBtn.setBackground(SystemColor.activeCaption);
		logoutBtn.setBounds(1139, 0, 45, 45);
		logoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logout();
			}
		});
		panel.add(logoutBtn);

		JButton settingBtn = new JButton("");
		settingBtn.setBackground(SystemColor.activeCaption);
		settingBtn.setIcon(new ImageIcon("D:\\java2\\DolphinChat\\Images\\settings.png"));
		settingBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ChangePass(username);
			}
		});
		settingBtn.setBounds(1084, 0, 45, 45);
		panel.add(settingBtn);

		micBtn = new JButton("");
		micBtn.setBackground(SystemColor.activeCaption);
		micBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
		micBtn.setIcon(new ImageIcon("D:\\java2\\DolphinChat\\Images\\telephone.png"));
		micBtn.setBounds(1029, 0, 45, 45);
		micBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame fr = callFrame();
				fr.setVisible(true);
				
			}
		});
		panel.add(micBtn);

		JPanel panelChat = new JPanel();
		panelChat.setBounds(10, 133, 805, 530);
		contentPane.add(panelChat);
		TitledBorder titledBorder = BorderFactory.createTitledBorder("Chat");
		titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 18));
		titledBorder.setTitleJustification(TitledBorder.LEADING);
		panelChat.setBorder(titledBorder);
		panelChat.setLayout(null);

		listChat = new JList(modelListChat);
		JScrollPane scrollListChat = new JScrollPane(listChat);
		listChat.setCellRenderer(new SetColorListChat(name));
		scrollListChat.setBounds(10, 28, 785, 492);
		panelChat.add(scrollListChat);

		JPanel panelOnlineUser = new JPanel();
		panelOnlineUser.setBounds(847, 133, 347, 270);
		contentPane.add(panelOnlineUser);
		TitledBorder titleOnline = BorderFactory.createTitledBorder("Đang hoạt động");
		titleOnline.setTitleFont(new Font("Arial", Font.BOLD, 18));
		titleOnline.setTitleJustification(TitledBorder.LEADING);
		panelOnlineUser.setBorder(titleOnline);
		panelOnlineUser.setLayout(null);

		listAccountOnline = new JList(modelListAccountOnline);
		JScrollPane scrollListAccountOnline = new JScrollPane(listAccountOnline);
		listAccountOnline.setCellRenderer(new customListCellRenderer(name));
		scrollListAccountOnline.setBounds(10, 20, 327, 240);
		panelOnlineUser.add(scrollListAccountOnline);

		panelFileSended = new JPanel();
		panelFileSended.setBounds(847, 425, 347, 238);
		TitledBorder fileSendTitle = BorderFactory.createTitledBorder("File đã nhận");
		panelFileSended.setBorder(fileSendTitle);
		contentPane.add(panelFileSended);
		panelFileSended.setLayout(null);

		listFile = new JList(modelListFile);
		JScrollPane scrollListFile = new JScrollPane(listFile);
		scrollListFile.setBounds(10, 21, 327, 207);
		panelFileSended.add(scrollListFile);
		listFile.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {

			}

			public void mouseClicked(MouseEvent e) {
				System.out.println(listFile.getSelectedIndex());
				int listItemId = listFile.getSelectedIndex();
				for (MyFile file : ReadClient.myFiles) {
					if (file.getId() == listItemId) {
						int option = JOptionPane.showConfirmDialog(null, "Tải file " + file.getName() , "Tải File",
								JOptionPane.YES_NO_OPTION);

						if (option == JOptionPane.YES_OPTION) {
							File fileToDownLoad = new File(file.getName());
							try {
								FileOutputStream fos = new FileOutputStream("D:\\java2\\DolphinChat\\file\\"+fileToDownLoad);

								fos.write(file.getData());
								fos.close();
								
								JOptionPane.showMessageDialog(null, "Tải file thành công!");
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else if (option == JOptionPane.NO_OPTION) {
							
						} else if (option == JOptionPane.CLOSED_OPTION) {
							
						}

					}
				}
			}
		});

		JPanel panelSendFile = new JPanel();
		panelSendFile.setBounds(10, 65, 805, 58);
		contentPane.add(panelSendFile);
		panelSendFile.setLayout(null);

		filenameText = new JTextField();
		filenameText.setBounds(10, 10, 542, 38);
		panelSendFile.add(filenameText);
		filenameText.setColumns(10);

		JButton chooseFileBtn = new JButton("Chọn File");
		chooseFileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser filechooser = new JFileChooser();
				filechooser.setDialogTitle("Chọn file muốn gửi");

				if (filechooser.showOpenDialog(null) == filechooser.APPROVE_OPTION) {
					fileToSend[0] = filechooser.getSelectedFile();
					filenameText.setText(fileToSend[0].getName());
					SendFileBtn.setEnabled(true);
				}
			}
		});
		chooseFileBtn.setBounds(562, 9, 108, 38);
		panelSendFile.add(chooseFileBtn);

		SendFileBtn = new JButton("Gửi File");
		SendFileBtn.setBounds(680, 10, 115, 38);
		SendFileBtn.setEnabled(false);
		SendFileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileInputStream fis;
				try {
					fis = new FileInputStream(fileToSend[0].getAbsolutePath());
					String fileName = fileToSend[0].getName();
					byte[] fileNameBytes = fileName.getBytes();

					byte[] fileContentBytes = new byte[(int) fileToSend[0].length()];
					fis.read(fileContentBytes);

					ClientThread.dos.writeUTF("file");
					ClientThread.dos.writeUTF(name);
					ClientThread.dos.writeUTF(filenameText.getText());

					ClientThread.dos.writeInt(fileNameBytes.length);
					ClientThread.dos.write(fileNameBytes);

					ClientThread.dos.writeInt(fileContentBytes.length);
					ClientThread.dos.write(fileContentBytes);
					ClientThread.dos.flush();

					modelListChat.addElement(name + " đã gửi file: " + filenameText.getText());
					modelListChat.addElement("	");
					filenameText.setText("");
					SendFileBtn.setEnabled(false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		panelSendFile.add(SendFileBtn);

		text = new JTextField();
		text.setBounds(10, 735, 1067, 28);
		contentPane.add(text);
		text.setColumns(10);

		sendBtn = new JButton("Gửi");
		sendBtn.setBounds(1109, 735, 85, 29);
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendText(name);
			}
		});
		contentPane.add(sendBtn);

		JPanel panelJoinGr = new JPanel();
		panelJoinGr.setBounds(847, 65, 347, 58);
		contentPane.add(panelJoinGr);
		panelJoinGr.setLayout(null);
		
		JLabel labelLogo = new JLabel("");
		labelLogo.setIcon(new ImageIcon("D:\\java2\\DolphinChat\\Images\\dolphin-homeFrame.png"));
		labelLogo.setHorizontalAlignment(SwingConstants.CENTER);
		labelLogo.setBounds(0, 0, 347, 58);
		panelJoinGr.add(labelLogo);

		JLabel hahaIcon = new JLabel("");
		hahaIcon.setHorizontalAlignment(SwingConstants.CENTER);
		hahaIcon.setIcon(new ImageIcon("D:\\java2\\DolphinChat\\Images\\happy-face.png"));
		hahaIcon.setBounds(10, 673, 55, 55);
		hahaIcon.addMouseListener(new IconClient(hahaIcon.getIcon().toString(), name));
		contentPane.add(hahaIcon);

		JLabel thinkIcon = new JLabel("");
		thinkIcon.setIcon(new ImageIcon("D:\\java2\\DolphinChat\\Images\\thinking.png"));
		thinkIcon.setHorizontalAlignment(SwingConstants.CENTER);
		thinkIcon.setBounds(105, 673, 55, 55);
		thinkIcon.addMouseListener(new IconClient(thinkIcon.getIcon().toString(), name));
		contentPane.add(thinkIcon);

		JLabel sleepIcon = new JLabel("");
		sleepIcon.setIcon(new ImageIcon("D:\\java2\\DolphinChat\\Images\\sleeping.png"));
		sleepIcon.setHorizontalAlignment(SwingConstants.CENTER);
		sleepIcon.setBounds(210, 673, 55, 55);
		sleepIcon.addMouseListener(new IconClient(sleepIcon.getIcon().toString(), name));
		contentPane.add(sleepIcon);

		JLabel shockIcon = new JLabel("");
		shockIcon.setIcon(new ImageIcon("D:\\java2\\DolphinChat\\Images\\shocked.png"));
		shockIcon.setHorizontalAlignment(SwingConstants.CENTER);
		shockIcon.setBounds(315, 673, 55, 55);
		shockIcon.addMouseListener(new IconClient(shockIcon.getIcon().toString(), name));
		contentPane.add(shockIcon);

		JLabel likeMoney = new JLabel("");
		likeMoney.setIcon(new ImageIcon("D:\\java2\\DolphinChat\\Images\\likeMoney.png"));
		likeMoney.setHorizontalAlignment(SwingConstants.CENTER);
		likeMoney.setBounds(420, 673, 55, 55);
		likeMoney.addMouseListener(new IconClient(likeMoney.getIcon().toString(), name));
		contentPane.add(likeMoney);

		JLabel kissIcon = new JLabel("");
		kissIcon.setIcon(new ImageIcon("D:\\java2\\DolphinChat\\Images\\kiss.png"));
		kissIcon.setHorizontalAlignment(SwingConstants.CENTER);
		kissIcon.setBounds(525, 673, 55, 55);
		kissIcon.addMouseListener(new IconClient(kissIcon.getIcon().toString(), name));
		contentPane.add(kissIcon);

		JLabel cryIcon = new JLabel("");
		cryIcon.setIcon(new ImageIcon("D:\\java2\\DolphinChat\\Images\\crying.png"));
		cryIcon.setHorizontalAlignment(SwingConstants.CENTER);
		cryIcon.setBounds(630, 673, 55, 55);
		cryIcon.addMouseListener(new IconClient(cryIcon.getIcon().toString(), name));
		contentPane.add(cryIcon);

		JLabel coolIcon = new JLabel("");
		coolIcon.setIcon(new ImageIcon("D:\\java2\\DolphinChat\\Images\\cool.png"));
		coolIcon.setHorizontalAlignment(SwingConstants.CENTER);
		coolIcon.setBounds(735, 673, 55, 55);
		coolIcon.addMouseListener(new IconClient(coolIcon.getIcon().toString(), name));
		contentPane.add(coolIcon);

		JLabel angryIcon = new JLabel("");
		angryIcon.setIcon(new ImageIcon("D:\\java2\\DolphinChat\\Images\\angry.png"));
		angryIcon.setHorizontalAlignment(SwingConstants.CENTER);
		angryIcon.setBounds(840, 673, 55, 55);
		angryIcon.addMouseListener(new IconClient(angryIcon.getIcon().toString(), name));
		contentPane.add(angryIcon);

		JLabel loveIcon = new JLabel("");
		loveIcon.setIcon(new ImageIcon("D:\\java2\\DolphinChat\\Images\\love.png"));
		loveIcon.setHorizontalAlignment(SwingConstants.CENTER);
		loveIcon.setBounds(945, 673, 55, 55);
		loveIcon.addMouseListener(new IconClient(loveIcon.getIcon().toString(), name));
		contentPane.add(loveIcon);

		JLabel likeIcon = new JLabel("");
		likeIcon.setIcon(new ImageIcon("D:\\java2\\DolphinChat\\Images\\like.png"));
		likeIcon.setBackground(new Color(240, 240, 240));
		likeIcon.setHorizontalAlignment(SwingConstants.CENTER);
		likeIcon.setBounds(1050, 673, 55, 55);
		likeIcon.addMouseListener(new IconClient(likeIcon.getIcon().toString(), name));
		contentPane.add(likeIcon);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

	public void start(int port) {
		ClientThread clThread = new ClientThread("localhost", port, name, panelFileSended);
		clThread.start();
	}
	public void logout () {
		int log = JOptionPane.showConfirmDialog(null, "Đăng xuất"  , "Xác Nhận",
				JOptionPane.YES_NO_OPTION);
		if (log == JOptionPane.YES_OPTION) {
			
			try {
				ClientThread.dos.writeUTF("log");
				ClientThread.dos.writeUTF(name);
				ClientThread.dos.writeUTF(username);
				ClientThread.dos.flush();
				this.dispose();
				new login();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if (log == JOptionPane.NO_OPTION) {
			
		}
	}

	public void sendText(String name) {
		if (text.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Nhập tin nhắn trước khi gửi");
		} else {
			WriteClient wcl = new WriteClient(text.getText(), name);
			wcl.start();
			text.setText("");
		}

	}
	public static JFrame callFrame () {
		JFrame frame = new JFrame("Dolphin call");
		frame.setSize(400,400);
		frame.getContentPane().setLayout(null);
		
		btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon("D:\\java2\\DolphinChat\\Images\\mic.png"));
		btnNewButton.setBounds(31, 200, 85, 64);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				init_audio();
				btnNewButton_1.setEnabled(true);
				btnNewButton.setEnabled(false);
				
			}
		});
		frame.getContentPane().add(btnNewButton);
		
		btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon("D:\\java2\\DolphinChat\\Images\\mute.png"));
		btnNewButton_1.setBounds(264, 200, 85, 64);
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calling = false;
				btnNewButton_1.setEnabled(false);
				btnNewButton.setEnabled(true);
			}
		});
		frame.getContentPane().add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("DOLPHIN");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 10, 366, 84);
		frame.getContentPane().add(lblNewLabel);
		return frame;
	}
	
	public static AudioFormat getAudioFormat() {
		float sampleRate = 8000.0F;
		int sampleSizeInbits = 16;
		int channel = 2;
		boolean signed = true;
		boolean bigEndian = false;
		return new AudioFormat(sampleRate, sampleSizeInbits, channel, signed, bigEndian);
	}
	
	public static void init_audio() {

		try {
			AudioFormat format = getAudioFormat();
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			if (!AudioSystem.isLineSupported(info)) {
				System.out.println("Không hỗ trợ");
				System.exit(0);
			}
			audio_in = (TargetDataLine) AudioSystem.getLine(info);
			audio_in.open(format);
			audio_in.start();

			recorder_thread r = new recorder_thread();
			InetAddress inet = InetAddress.getByName("localhost");
			r.audio_in = audio_in;
			r.dout = new DatagramSocket();
			r.server_ip = inet;
			r.port = voice_port;

			calling = true;
			r.start();
			
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	

}
