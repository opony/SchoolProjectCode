package com.pony.agent;

import java.io.IOException;

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

}
