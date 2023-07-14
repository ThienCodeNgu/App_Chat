package Model;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import View.ServerFrame;
import View.homeFrame;

public class ReadClient extends Thread {

	private Socket socket;
	private JPanel panel;
	public static ArrayList<MyFile> myFiles = new ArrayList<>();
	public static DataInputStream dis;

	public ReadClient(Socket socket, JPanel panel) {
		this.socket = socket;
		this.panel = panel;
	}

	@Override
	public void run() {

		try {
			int fileID = 0;
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			while (true) {
					String type = dis.readUTF();
				if (type.equals("Online users")) {
					String[] users = dis.readUTF().split(",");
					homeFrame.modelListAccountOnline.clear();
					for (String user : users) {
						homeFrame.modelListAccountOnline.addElement(user);
					}
				} else if (type.equals("text")) {
					String sms = dis.readUTF();
					homeFrame.modelListChat.addElement(sms);
					homeFrame.modelListChat.addElement("   ");
				} else if (type.equals("Emoji")) {
					String sms = dis.readUTF();
					String name = dis.readUTF();
					homeFrame.modelListChat.addElement(name);
					homeFrame.modelListChat.addElement(new ImageIcon(sms));

				} else if (type.equals("file")) {
					String name = dis.readUTF();
					String nameFile = dis.readUTF();

					int fileNameLength = dis.readInt();
					if (fileNameLength > 0) {
						byte[] fileNameBytes = new byte[fileNameLength];
						dis.readFully(fileNameBytes, 0, fileNameBytes.length);
						String fileName = new String(fileNameBytes);

						int fileContentLength = dis.readInt();

						if (fileContentLength > 0) {
							byte[] fileContentBytes = new byte[fileContentLength];
							dis.readFully(fileContentBytes, 0, fileContentLength);


							myFiles.add(new MyFile(fileID, fileName, fileContentBytes, getfileExtension(fileName)));
							fileID++;
						}

					}
					homeFrame.modelListFile.clear();
					for (MyFile file : myFiles) {
						homeFrame.modelListFile.addElement(file.getName());
					}
					homeFrame.modelListChat.addElement(name+" đã gửi file: "+nameFile);
					
					
				}
				else if (type.equals("log")) {
					System.out.println("ok");
					String[] userAfterLog = dis.readUTF().split(",");
					homeFrame.modelListAccountOnline.clear();
					for (String user : userAfterLog) {
						homeFrame.modelListAccountOnline.addElement(user);
					}
					
					
					socket.close();
					
					break;
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getfileExtension(String fileName) {
		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			return fileName.substring(i + 1);
		} else {
			return "No extention found";
		}
	}

	public void displayFileArr() {
		for (MyFile file : myFiles) {
			System.out.println(file.getName());
		}
	}
}
