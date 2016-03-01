package com.pony.agent;

import java.io.IOException;

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
}
