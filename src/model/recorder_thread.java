package Model;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.sound.sampled.TargetDataLine;

import View.homeFrame;

public class recorder_thread extends Thread {
	public TargetDataLine audio_in = null;
	public DatagramSocket dout;
	byte byte_buff[] = new byte[512];
	public InetAddress server_ip;
	public int port;

	@Override
	public void run() {
		int i = 0;
		while (homeFrame.calling) {

			try {
				audio_in.read(byte_buff, 0, byte_buff.length);
				DatagramPacket data = new DatagramPacket(byte_buff, byte_buff.length, server_ip, port);
				System.out.println("send #" + i++);
				dout.send(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		audio_in.close();
		audio_in.drain();
		System.out.println("Thread is stop");
	}
}
