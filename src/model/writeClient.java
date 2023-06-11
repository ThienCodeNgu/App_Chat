package model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import view.clientFrame;

public class writeClient extends Thread {
	private Socket socket;
	private String sms;
	private String name;
public writeClient (Socket socket, String sms, String name) {
	this.socket = socket;
	this.sms = sms;
	this.name = name;
}
	public void run() {
		DataOutputStream dos = null;
		try {
			dos = new DataOutputStream(socket.getOutputStream());
			
				dos.writeUTF(name+": "+sms);
				clientFrame.model.addElement(name+": "+sms);
			    
		} catch (Exception e) {
			clientFrame.model.addElement("Server is stopped");
		}
	}
}
