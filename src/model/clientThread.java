package model;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class clientThread extends Thread {
	private String host;
	private int port;
	public static Socket socket;
	public clientThread(String host, int port) {	
		this.host = host;
		this.port = port;
	}


	public void run() {
		try {
			socket = new Socket(host, port);
			readClient r = new readClient(socket);
			r.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
