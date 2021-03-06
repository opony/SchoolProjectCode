package com.pony.adapter.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

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
    int lastCount = 0;
    String tempMsg = "";
    InetAddress hostname = null;
    
    
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
				System.out.println("Start recive .");
				startRecive();
				
			} catch (Exception e) {
				
				e.printStackTrace();
				
				try {
					if(input != null){
						input.close();
						input = null;
					}
						
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
				try {
					if(output != null){
						output.close();
						output = null;
					}
						
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
				try {
					if(socket != null){
						socket.close();
						socket = null;
					}
						
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
			}
		}
	}
	
	private void startRecive() throws Exception{
				
		while(true){
			
			Boolean a=hostname.isReachable(500);
			if(a == false)
				throw new Exception("disconnected");
			
			while(MsgQueue.msgQueueLength() > 0){
				
				a=hostname.isReachable(500);
				if(a == false)
					throw new Exception("disconnected");
				
				tempMsg = MsgQueue.takeMsg() + "</EOF>";
				
				
//				System.out.println("send");
				this.reciveMsgHandler.reciveMsg(tempMsg);
			}
			Thread.sleep(100);
			
		}
		
//		InetAddress hostname = InetAddress.getByName("192.168.218.1");
//		Boolean a=hostname.isReachable(2000);
//		System.out.println(a);
	}
	
	
//	private void startRecive() throws Exception{
//		lastCount = 0;
//		if(!tempMsg.equals("")){
//			System.out.println("tempMsg is not null then send");
//			this.reciveMsgHandler.reciveMsg(tempMsg);
//			tempMsg = "";
//		}
//		
//		while(true){
//			
//			
//			while(MsgQueue.msgQueueLength() > 0){
//				int count = input.available();
//				System.out.println("read : " + count);
//				System.out.println("last count : " + lastCount);
//				
//				if(lastCount > 0 ){
//					if(count - lastCount != 0){
//						
//						tempMsg = MsgQueue.takeMsg() + "</EOF>";
//						System.out.println("send");
//						this.reciveMsgHandler.reciveMsg(tempMsg);
//						
//						
//					}else if (count - lastCount == 0){
//						throw new Exception("disconnected");
//					}
//				}else{
//					tempMsg = MsgQueue.takeMsg() + "</EOF>";
//					System.out.println("send");
//					this.reciveMsgHandler.reciveMsg(tempMsg);
//					
//				}
//				
//				
//				lastCount = count;
//				
//				
//			}
//		}
//	}
//	private void checkResponse() throws Exception{
//		byte[] buff = new byte[1024];
//        String data = "";
//        int length;
//        while ((length = input.read(buff)) > 0){
//    		data += new String(buff, 0, length);
//    		System.out.println("recive data : " + data);
//    		if(data.equals("</EOF>")){
//    			tempMsg = "";
//    			return;
//    		}
//    	}
//	}
	
	private void connectToServer() throws Exception{
		String[] tokens = null;
		int port;
		String host;
		try {
			System.out.println("Try to connect : " + hostIps[currHostIDx]);
			tokens = hostIps[currHostIDx].split(":");
			currHostIDx = currHostIDx == 0 ? 1 : 0;
			
			host = tokens[0];
			port = Integer.parseInt(tokens[1]);
			
			socket = new Socket(host,port);
			hostname = InetAddress.getByName(host);
			
			input = new BufferedInputStream(socket.getInputStream());
			output = new BufferedOutputStream(socket.getOutputStream());
			System.out.println("Connected.");
			
			
		} catch (Exception e) {
			System.out.println("Connect Fail.");
			//e.printStackTrace();
			
			throw e;
		}
	}
	
	public void sendToServer(String msg) throws IOException{
		
		output.write(msg.getBytes());
		output.flush();
	}

}
