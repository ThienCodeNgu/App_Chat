package Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JPanel;

import View.homeFrame;

public class ClientThread extends Thread {
	private static String host;
	private static int port;
	public static Socket socket;
	public static DataOutputStream dos;
	public static String name;
	private JPanel panel;

	public ClientThread(String host, int port, String name, JPanel panel) {
		this.host = host;
		this.port = port;
		this.name = name;
		this.panel = panel;
	}

	public void run() {
		try {
			socket = new Socket(host, port);

			dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF(name);
			dos.writeUTF("Online users");
			
			ReadClient rcl = new ReadClient(socket, panel);
			rcl.start();
			
			
			 
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
