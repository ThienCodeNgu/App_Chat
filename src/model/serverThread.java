package Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import View.ServerFrame;
import View.homeFrame;

public class ServerThread extends Thread {

	public static ServerSocket svsocket;
	public static Socket socket;
	private int port;
	public static ArrayList<Socket> listSK;
	public static ArrayList<Account> listAccount;
    private DataOutputStream dos;
	public ServerThread(int port) {
		this.port = port;


	}

	public void run() {
		ServerFrame.ServerModel.addElement("Máy chủ đang chạy trên cổng " + port);
		try {
			svsocket = new ServerSocket(port);

			while (true) {
				
				socket = svsocket.accept();
				ServerFrame.ServerModel.addElement("Đã kết nối với: " + socket);
				listSK.add(socket);
				int num = listSK.size();
				ServerFrame.NumConnect.setText(num + "");
				

				DataInputStream dis = new DataInputStream(socket.getInputStream());
				String nameSocket = dis.readUTF();
				Account acc = new Account(nameSocket, socket.getPort());
				listAccount.add(acc);
				
				ReadServer rsv = new ReadServer(socket);
				rsv.start();
				
			}

		} catch (IOException e) {

		}

	}

	public static void stopServer() throws IOException {

		if (svsocket != null) {
			svsocket.close();

		}

	}
	public static void updateNum() {
		int num = listSK.size();
		ServerFrame.NumConnect.setText(num + "");
	}
	

}
