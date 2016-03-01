package com.pony.thread;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.pony.queue.*;
public class UploadMsgThread implements Runnable {
	Socket serverSocket = null;
	private static String[] srvIPs = new String[]{"192.168.60.1:9010","192.168.60.1:9011"};
	private int currSrvIdx = 1;
	private BufferedOutputStream out = null;
	private BufferedInputStream input = null;
	
	private static boolean SRV_CONNECTED = true;
	boolean isConnected = false;
	@Override
	public void run() {
		String msg = "";
		boolean lastSendSucc = true;
		
		byte[] b = new byte[1024];
        String data = "";
        
        int length;
        
		
        //out.write("Send From Client ".getBytes());
		while(true){
			try {
				while(isConnected != SRV_CONNECTED){
					isConnected = connectToServer();
					Thread.sleep(1000);
				}
				
				while(ServerQueue.SendToSrvQueLength() > 0){
					
					try {
						if(lastSendSucc){
							lastSendSucc = false;
							msg = ServerQueue.pollSendToSrvQue();
						}
						System.out.println("upload msg : " + msg);
						System.out.println("server socket : " + serverSocket.isConnected());
						
						if(input.read() == -1 )
							System.out.println("disconnect : ");
						out.write(msg.getBytes());
						lastSendSucc = true;
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			} catch (IOException e) {
				if(serverSocket != null){
					try {
						serverSocket.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					isConnected = false;
				}
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
		}

	}
	
	private boolean connectToServer(){
		String host;
		int port;
		String[] temp;
		if(currSrvIdx == 0){
			currSrvIdx = 1;
		}else
		{
			currSrvIdx = 0;
		}
		
		temp = srvIPs[currSrvIdx].split(":");
		host = temp[0];
		port = Integer.parseInt(temp[1]);
		System.out.println("Connect To Server[" + currSrvIdx + "]: " + srvIPs[currSrvIdx]);
		try {
			serverSocket = new Socket( host, port );
			out = new BufferedOutputStream(serverSocket.getOutputStream());
			input = new BufferedInputStream( serverSocket.getInputStream() );
			return true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return false;
	}

}
