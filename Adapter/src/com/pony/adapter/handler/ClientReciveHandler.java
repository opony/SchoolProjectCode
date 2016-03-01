package com.pony.adapter.handler;

import com.pony.adapter.client.BaseClient;

public class ClientReciveHandler implements IReciveMsgHandler {
	
	private BaseClient client = null;
	
	public void setClient(BaseClient client){
		this.client = client;
	}
	
	@Override
	public String reciveMsg(String msg) throws Exception {
		this.client.sendToServer(msg);
		return "";
	}

}
