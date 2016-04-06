package com.pony.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
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
    public String pingIp = "";
    public boolean startPing = false;
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
					startPing = true;
					_isConnected = true;
					if(init){
						this.reciveMsgHandler.init();
						init = false;
					}
					System.out.println("Start recive.");
					startReciveData();
				} catch (Exception e) {
					_isConnected = false;
					startPing = false;
					System.out.println("server run exception.");
					
					close();
				}
				
			}
		}finally{
			close();
		}
		
		
	}
	
	public void close(){
		System.out.println("start close connection");
		if(clientSocket != null)
			try {
				clientSocket.close();
				clientSocket = null;
			} catch (Exception ex) {
				System.out.println("clientSocket close ex : ");
				ex.printStackTrace();
			}
		
		if(input != null)
			try {
				input.close();
				input = null;
			} catch (IOException ex) {
				System.out.println("close input ex");
				ex.printStackTrace();
			}
		
		if(output != null)
			try {
				
				output.close();
				output = null;
			} catch (IOException ex) {
				System.out.println("close output ex");
				ex.printStackTrace();
			}
		
		if(serverSocket != null)
			try {
				serverSocket.close();
				serverSocket = null;
			} catch (IOException ex) {
				System.out.println("close serverSocket ex");
				ex.printStackTrace();
			}
		System.out.println("close connection done");
	}
	
	public void listenerPort() throws Exception{
		try {
			System.out.println("server start listen : " + port);
			serverSocket = new ServerSocket(port);
			
			String className = this.getClass().getName();
			className = className.substring(className.lastIndexOf('.') + 1);
			//System.out.println("bbb : " + Inet4Address.getLocalHost().getHostAddress());
			System.out.println(className + " listen : " + Inet4Address.getLocalHost().getHostAddress() + ":" + serverSocket.getLocalPort());
			
			clientSocket = serverSocket.accept();
			System.out.println("Client connected : " + clientSocket.getRemoteSocketAddress());
			pingIp = clientSocket.getRemoteSocketAddress().toString();
			pingIp = pingIp.split(":")[0].substring(1);
//			pingIp = pingIp.substring(1);
			input = new BufferedInputStream( clientSocket.getInputStream() );
			output = new BufferedOutputStream(clientSocket.getOutputStream());
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	private void startReciveData() throws Exception{
		
		byte[] buff = new byte[4096];
        String data = "";
        int length;
		while(true){
			
			while ((length = input.read(buff)) > 0){
        		data += new String(buff, 0, length);
//        		System.out.println("recive data : " + data);
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
