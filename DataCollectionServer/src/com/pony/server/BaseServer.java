package com.pony.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.pony.handler.IReciveMsgHandler;

public class BaseServer implements Runnable {
	
	int port;
	Socket clientSocket = null;
	ServerSocket serverSocket = null;
	BufferedInputStream input = null;
    BufferedOutputStream output = null;
    IReciveMsgHandler reciveMsgHandler = null;
    boolean init = true;
    boolean _isConnected = false;
	public BaseServer(int port, IReciveMsgHandler reciveMsgHandler){
		this.port = port;
		this.reciveMsgHandler = reciveMsgHandler;
		
		
	}
	
	@Override
	public void run() {
		
		try{
			while(true){
				try {
					
					listenerPort();
					_isConnected = true;
					if(init){
						this.reciveMsgHandler.init();
						init = false;
					}
					startReciveData();
				} catch (Exception e) {
					_isConnected = false;
					e.printStackTrace();
					
					close();
				}
				
			}
		}finally{
			close();
		}
		
		
	}
	
	public void close(){
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
	
	public void listenerPort(){
		try {
			serverSocket = new ServerSocket(port);
			
			String className = this.getClass().getName();
			className = className.substring(className.lastIndexOf('.') + 1);
			System.out.println(className + " listen port : " + serverSocket.getLocalPort());
			
			clientSocket = serverSocket.accept();
			System.out.println("Client connected : " + clientSocket.getLocalAddress());
			input = new BufferedInputStream( clientSocket.getInputStream() );
			output = new BufferedOutputStream(clientSocket.getOutputStream());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void startReciveData() throws Exception{
		
		byte[] buff = new byte[1024];
        String data = "";
        int length;
		while(true){
			
			while ((length = input.read(buff)) > 0){
        		data += new String(buff, 0, length);
        		System.out.println("recive data : " + data);
        		if(data.contains("</EOF>")){
        			//data = EnServerQueue(data);
//        			output.write("</EOF>".getBytes());
//        			output.flush();
        			data = reciveMsgHandler.reciveMsg(data);
        			
        		}
        	}
		}
	}
	
	public void sendMsg(String msg) throws IOException{
		msg += "</EOF>";
		
		if(_isConnected){
			output.write(msg.getBytes());
			output.flush();
		}
		
		
	}
	
}
