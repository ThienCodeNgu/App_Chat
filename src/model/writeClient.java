package Model;

import View.homeFrame;


public class WriteClient extends Thread {
	private String sms;
	private String name;

	public WriteClient(String sms, String name) {
		this.sms = sms;
		this.name = name;
	}

	public void run() {
		try {
			ClientThread.dos.writeUTF("text");
			ClientThread.dos.writeUTF(name + ": " + sms);
			ClientThread.dos.flush();
			homeFrame.modelListChat.addElement(name + ": " + sms);
			homeFrame.modelListChat.addElement("   ");
		} catch (Exception e) {
			homeFrame.modelListChat.addElement("Không thể gửi tin nhắn!");
		}
	}
}
