package com.pony.agent;

import java.io.IOException;
import java.net.InetAddress;

import com.pony.client.BaseClient;
import com.pony.handler.IReciveMsgHandler;
import com.pony.handler.SyncAgentReciveHander;
public class SyncAgentClient extends BaseClient implements ISyncAgent {

	public SyncAgentClient(String host, int port, IReciveMsgHandler reciveMsgHandler) {
		super(host, port, reciveMsgHandler);
		((SyncAgentReciveHander)reciveMsgHandler).setSyncAgent(this);
	}
	
	@Override
	public void sendMsg(String msg) throws IOException{
		super.sendMsg(msg);
	}

	@Override
	public void checkConnection(){
		InetAddress hostname = null;
		try {
			while(true){
				if(this.startPing){
					System.out.println("ping IP : " + this.host);
					hostname = InetAddress.getByName(this.host);
					Boolean a=hostname.isReachable(1000);
					if(a == false)
						throw new Exception("connection fail.");
				}
				
				Thread.sleep(500);
			}
			
		} catch (Exception e) {
			System.out.println("ping exception");
			this.close();
			InternalSyncAgent.getInstance().start();
		}
		
	}
}
