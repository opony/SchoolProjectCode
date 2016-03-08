package com.pony.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.pony.handler.IReciveMsgHandler;

public class BaseClient implements Runnable {
	
	int port;
	String host;
	BufferedInputStream input = null;
    BufferedOutputStream output = null;
    IReciveMsgHandler reciveMsgHandler = null;
    boolean init = true;
    boolean _isConnected = false;
    
    private static Logger logger = LogManager.getRootLogger();
    		
	public BaseClient(String host, int port, IReciveMsgHandler reciveMsgHandler){
		this.host = host;
		this.port = port;
		this.reciveMsgHandler = reciveMsgHandler;
	}
	
	@Override
	public void run() {
		
		
		while(true){
			try {
				logger.info("Start connect to server.");
				connectToServer();
				_isConnected = true;
				logger.info("Start recive data.");
				startReciveData();
				
			} catch (Exception e) {
				_isConnected = false;
				logger.error("client exception .",e);
			}finally{
				if(input != null)
					try {
						input.close();
						input = null;
					} catch (Exception e2) {
					}
				
				if(output != null)
					try {
						output.close();
						output = null;
					} catch (Exception e2) {
					}
			}
			
			
			
		}
		
		
	}
	
	@SuppressWarnings("resource")
	private void connectToServer() throws Exception{
		while(true){
			Socket socket = null;
			try {
				logger.debug("conntect to [" + host + ":" + port + "].");
				socket = new Socket( host, port);
				input = new BufferedInputStream(socket.getInputStream());
				output = new BufferedOutputStream(socket.getOutputStream());
				logger.debug("conntected .");
				if(init){
					logger.info("client initial");
					this.reciveMsgHandler.init();
					init = false;
				}
				
				return;
			} catch (Exception e) {
				logger.error("conntect to server [" + host + ":" + port + "] fail.", e);
				if(socket != null)
					try {
						socket.close();
					} catch (Exception e2) {
						
					}
				
			}
			
			Thread.sleep(1000);
			logger.info("retry to connect server");
		}
		
	}
	
	private void startReciveData() throws Exception{
		
		byte[] buff = new byte[4096];
        String data = "";
        int length;
		while(true){
			try {
				while ((length = input.read(buff)) > 0){
					
	        		data += new String(buff, 0, length);
	        		if(data.contains("</EOF>")){
	        			//data = EnServerQueue(data);
	        			data = reciveMsgHandler.reciveMsg(data);
	        		}
	        	}
			} catch (Exception e) {
				logger.error("Recive server message fail",e);
				throw e;
			}
			
		}
	}
	
	public void sendMsg(String msg) throws IOException{
		msg += "</EOF>";
		logger.debug("send to server msg : " + msg);
		if(_isConnected){
			output.write(msg.getBytes());
			output.flush();
		}
		
	}
	
	

}
