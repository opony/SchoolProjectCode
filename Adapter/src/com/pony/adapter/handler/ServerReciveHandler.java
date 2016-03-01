package com.pony.adapter.handler;

import com.pony.adapter.queue.MsgQueue;

public class ServerReciveHandler implements IReciveMsgHandler {

	@Override
	public String reciveMsg(String data) throws Exception {
		String msg;
		int eofEndIdx = 0;
		while(data.contains("</EOF>")){
			eofEndIdx = data.indexOf("</EOF>");
			msg = data.substring(0,eofEndIdx);
			
			MsgQueue.putMsg(msg);
			
			data = data.substring(eofEndIdx + 6, data.length());
		}
				
		return data;
		
	}

}
