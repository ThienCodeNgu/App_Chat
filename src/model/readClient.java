package model;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import view.clientFrame;

public class readClient extends Thread {
	private Socket socket;
public readClient (Socket  socket) {
	this.socket = socket;
}

	public void run() {
		DataInputStream	dis = null;
		try {
		dis = new DataInputStream(socket.getInputStream());
		while (true) {
			String sms = dis.readUTF();
			clientFrame.model.addElement(sms);
		}
		} catch (Exception e) {
			try {
				dis.close();
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}
}
