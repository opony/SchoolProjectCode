package com.pony.agent;

import java.io.IOException;
import java.net.InetAddress;

import com.pony.handler.IReciveMsgHandler;
import com.pony.handler.SyncAgentReciveHander;
import com.pony.server.BaseServer;

public class SyncAgentServer extends BaseServer implements ISyncAgent {

	public SyncAgentServer(int port, IReciveMsgHandler reciveMsgHandler) {
		super(port, reciveMsgHandler);
		((SyncAgentReciveHander)reciveMsgHandler).setSyncAgent(this);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void sendMsg(String msg) throws IOException{
		super.sendMsg(msg);
	}

	@Override
	public void checkConnection() {
		InetAddress hostname = null;
		
		while(true){
			try {
				if(this.startPing){
//					System.out.println("ping IP : " + this.pingIp);
					hostname = InetAddress.getByName(this.pingIp);
					Boolean a=hostname.isReachable(1000);
					if(a == false)
						throw new Exception("connection fail.");
				}
			} catch (Exception e) {
				this.close();
			}
			
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}

}
