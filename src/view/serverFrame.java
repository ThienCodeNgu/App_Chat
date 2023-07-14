package View;

import java.awt.EventQueue;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import Model.Account;
import Model.LoginServer;
import Model.ServerThread;
import Model.player_thread;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.awt.SystemColor;
import java.awt.Color;

public class ServerFrame extends JFrame {

	private JPanel contentPane;
	private JTextField portText;
	private JLabel labelStatus;
	private JLabel labelIPAdress;
	private JButton btnStop;
	private JButton StartBtn;
	public static JLabel NumConnect;
	public static JList ServerList;
	public static DefaultListModel ServerModel;
	private ServerThread svThread;
	private LoginServer lgSV;
	private login login;
	public int voice_port = 8888;

	public SourceDataLine audio_out;

	public static boolean test;
	
	public static boolean calling = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerFrame frame = new ServerFrame();
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
	public ServerFrame() {
		this.setTitle("Dolphin Chat");
		setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\java2\\DolphinChat\\Images\\dolphin.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 825, 533);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		ServerModel = new DefaultListModel<>();

		JLabel labelLogo = new JLabel("");
		labelLogo.setIcon(new ImageIcon("D:\\java2\\DolphinChat\\Images\\dolphin-logo.png"));
		labelLogo.setHorizontalAlignment(SwingConstants.CENTER);
		labelLogo.setBounds(10, 10, 267, 305);
		contentPane.add(labelLogo);

		ServerList = new JList(ServerModel);
		JScrollPane sc = new JScrollPane(ServerList);
		sc.setBounds(287, 10, 514, 305);
		contentPane.add(sc);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBounds(10, 392, 791, 94);
		contentPane.add(panel);
		TitledBorder title = BorderFactory.createTitledBorder("Thông tin máy chủ");
		panel.setBorder(title);
		panel.setLayout(null);

		JLabel labelPort = new JLabel("Cổng:");
		labelPort.setFont(new Font("Tahoma", Font.PLAIN, 20));
		labelPort.setBounds(10, 337, 68, 35);
		contentPane.add(labelPort);

		NumConnect = new JLabel();
		NumConnect.setHorizontalAlignment(SwingConstants.CENTER);
		NumConnect.setFont(new Font("Tahoma", Font.PLAIN, 18));
		NumConnect.setBounds(682, 31, 53, 35);
		panel.add(NumConnect);

		portText = new JTextField("1111");
		portText.setFont(new Font("Tahoma", Font.PLAIN, 15));
		portText.setBounds(88, 337, 105, 35);
		contentPane.add(portText);
		portText.setColumns(10);

		StartBtn = new JButton("Mở");
		StartBtn.setForeground(new Color(0, 0, 205));
		StartBtn.setBackground(new Color(255, 255, 255));
		StartBtn.setBounds(334, 334, 159, 38);
		StartBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		contentPane.add(StartBtn);

		btnStop = new JButton("Đóng");
		btnStop.setEnabled(false);
		btnStop.setForeground(new Color(255, 0, 0));
		btnStop.setBackground(new Color(255, 255, 255));
		btnStop.setBounds(607, 334, 159, 38);
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					test = false;
					ServerThread.stopServer();
					labelStatus.setText("OFF");
					labelStatus.setForeground(Color.red);
					labelIPAdress.setText("");
					btnStop.setEnabled(false);
					StartBtn.setEnabled(true);
					svThread.interrupt();
					ServerModel.addElement("Máy chủ đã đóng");
					calling = false;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		contentPane.add(btnStop);

		JLabel labelStatusText = new JLabel("Trạng thái:");
		labelStatusText.setFont(new Font("Tahoma", Font.PLAIN, 18));
		labelStatusText.setBounds(25, 31, 97, 35);
		panel.add(labelStatusText);

		labelStatus = new JLabel("OFF");
		labelStatus.setHorizontalAlignment(SwingConstants.CENTER);
		labelStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));
		labelStatus.setBounds(138, 31, 53, 35);
		labelStatus.setForeground(Color.red);
		panel.add(labelStatus);

		JLabel IPAdressText = new JLabel("Địa chỉ IP:");
		IPAdressText.setFont(new Font("Tahoma", Font.PLAIN, 18));
		IPAdressText.setBounds(259, 31, 97, 35);
		panel.add(IPAdressText);

		labelIPAdress = new JLabel("");
		labelIPAdress.setFont(new Font("Tahoma", Font.PLAIN, 18));
		labelIPAdress.setBounds(359, 31, 154, 35);
		panel.add(labelIPAdress);

		JLabel ConnectNumText = new JLabel("Số kết nối:");
		ConnectNumText.setFont(new Font("Tahoma", Font.PLAIN, 18));
		ConnectNumText.setBounds(561, 31, 97, 35);
		panel.add(ConnectNumText);

	}

	public void getIpAddress() {
		try {
			InetAddress localhost = InetAddress.getLocalHost();
			String address = localhost.getHostAddress();
			labelIPAdress.setText(address);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void start() {
		svThread.listSK = new ArrayList<>();
		svThread.listAccount = new ArrayList<>();
		svThread = new ServerThread(Integer.parseInt(portText.getText()));
		svThread.start();
		labelStatus.setText("ON");
		labelStatus.setForeground(Color.GREEN);
		getIpAddress();
		init_audio();
		lgSV.listLogin = new ArrayList<>();
		lgSV = new LoginServer(2004);
		lgSV.start();
		btnStop.setEnabled(true);
		StartBtn.setEnabled(false);
	}
	
	public static AudioFormat getAudioFormat() {
		float sampleRate = 8000.0F;
		int sampleSizeInbits = 16;
		int channel = 2;
		boolean signed = true;
		boolean bigEndian = false;
		return new AudioFormat(sampleRate, sampleSizeInbits, channel, signed, bigEndian);
	}
	
	public void init_audio() {

		try {

			AudioFormat format = getAudioFormat();
			DataLine.Info info_out = new DataLine.Info(SourceDataLine.class, format);
			if (!AudioSystem.isLineSupported(info_out)) {
				System.out.println("Không hỗ trợ");
				System.exit(0);
			}
			audio_out = (SourceDataLine) AudioSystem.getLine(info_out);
			audio_out.open(format);
			audio_out.start();
			
			player_thread p = new player_thread();
			p.din = new DatagramSocket(voice_port);
			p.audio_out = audio_out;
			calling = true;
			p.start();
			StartBtn.setEnabled(false);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
