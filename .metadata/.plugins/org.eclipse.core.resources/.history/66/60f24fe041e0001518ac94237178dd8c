package com.pony.adapter.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.pony.adapter.handler.ClientReciveHandler;
import com.pony.adapter.handler.IReciveMsgHandler;
import com.pony.adapter.queue.MsgQueue;

public class BaseClient implements Runnable {

	private String[] hostIps = null;
	int currHostIDx = 0;
	Socket socket = null;
	BufferedInputStream input = null;
    BufferedOutputStream output = null;
    IReciveMsgHandler reciveMsgHandler = null;
    
	public BaseClient(String[] hostIps, ClientReciveHandler reciveMsgHandler){
		this.hostIps = hostIps;
		
		this.reciveMsgHandler = reciveMsgHandler;
		((ClientReciveHandler)this.reciveMsgHandler).setClient(this);
	}
	
	@Override
	public void run() {
		while(true){
			try {
				connectToServer();
				startRecive();
				
			} catch (Exception e) {
				
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
			}
		}
	}
	
	private void startRecive() throws Exception{
		while(true){
			input.read();
			while(MsgQueue.msgQueueLength() > 0){
				
				this.reciveMsgHandler.reciveMsg(MsgQueue.takeMsg());
			}
		}
	}
	
	private void connectToServer() throws Exception{
		String[] tokens = null;
		int port;
		String host;
		try {
			System.out.println("Try to connect : " + hostIps[currHostIDx]);
			tokens = hostIps[currHostIDx].split(":");
			currHostIDx++;
			
			host = tokens[0];
			port = Integer.parseInt(tokens[1]);
			
			socket = new Socket(host,port);
			input = new BufferedInputStream(socket.getInputStream());
			output = new BufferedOutputStream(socket.getOutputStream());
			System.out.println("Connected.");
			
			
		} catch (Exception e) {
			System.out.println("Connect Fail.");
			e.printStackTrace();
			
			throw e;
		}
	}
	
	public void sendToServer(String msg) throws IOException{
		
		output.write(msg.getBytes());
	}

}
