package com.pony.adapter.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.pony.adapter.handler.IReciveMsgHandler;


public class BaseServer implements Runnable{
	int port;
	Socket clientSocket = null;
	ServerSocket serverSocket = null;
	BufferedInputStream input = null;
    BufferedOutputStream output = null;
    IReciveMsgHandler reciveMsgHandler = null;
    boolean init = true;
	public BaseServer(int port, IReciveMsgHandler reciveMsgHandler){
		this.port = port;
		this.reciveMsgHandler = reciveMsgHandler;
	}
	@Override
	public void run() {
		try {
			while(true){
				try {
					listenerPort();
					startReciveData();
				} catch (Exception e) {
					e.printStackTrace();
					
					close();
				}
				
			}
		} finally {
			close();
		}
		
		
	}
	
	private void close(){
		if(input != null)
			try {
				input.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		
		if(output != null)
			try {
				output.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		
		try {
			serverSocket.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void listenerPort() throws Exception{
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Listen port : " + port);
			
			clientSocket = serverSocket.accept();
			System.out.println("Port : " + port + " connected client.");
			input = new BufferedInputStream( clientSocket.getInputStream() );
			output = new BufferedOutputStream(clientSocket.getOutputStream());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	
	private void startReciveData() throws Exception{
		
		byte[] buff = new byte[1024];
        String data = "";
        int length;
		while(true){
			
			while ((length = input.read(buff)) > 0){
        		data += new String(buff, 0, length);
//        		System.out.println("recive data : " + data);
        		if(data.contains("</EOF>")){
        			//data = EnServerQueue(data);
        			data = reciveMsgHandler.reciveMsg(data);
        		}
        	}
		}
	}
	
	
}
