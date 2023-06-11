package model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import view.clientFrame;
import view.serverFrame;

public class serverThread extends Thread {
	public static ServerSocket svsocket;
	public static Socket socket;
	private int port;
	private volatile boolean isRunning;
	public  static ArrayList<Socket> listSK;
public serverThread(int port) {
	this.port = port;
}
public void run() {
	System.out.println("server is waiting");
	try {
		svsocket = new ServerSocket(port);
		isRunning = true;
		while (isRunning) {
			socket = svsocket.accept();
			System.out.println(socket);
			serverFrame.model.addElement("server was connected with: "+socket);
			listSK.add(socket);
			int number = listSK.size();
			serverFrame.lblNumber.setText(number+"");
			readServer  r = new readServer(socket);
			r.start();
		}
	} catch (IOException e) {
		
	}
	
}
public void dung () {
	isRunning = false;
		try {
			if (svsocket != null) {
			svsocket.close();
			serverFrame.model.addElement("server stopped");
			serverFrame.lblNumber.setText("0");
			serverFrame.lblipAddress.setText("");
			socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
