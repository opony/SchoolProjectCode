
package com.pony.handler;


import java.io.IOException;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.pony.agent.ISyncAgent;
import com.pony.agent.InternalSyncAgent;
import com.pony.queue.MsgQueue;

//import com.pony.queue.ServerQueue;

public class SyncAgentReciveHander implements IReciveMsgHandler {
	public static final String GET_ALL = "Get All";
	public static final String CLEAR = "Clear";
	public static final String DATA = "Data";
	
	private ISyncAgent syncAgent;
	
	
	@Override
	public String reciveMsg(String data) throws Exception {
		
		String msg;
		int eofEndIdx = 0;
		while(data.contains("</EOF>")){
			eofEndIdx = data.indexOf("</EOF>");
			msg = data.substring(0,eofEndIdx);
//			System.out.println("enqueue msg : " + msg);
			parseMsg(msg);
			data = data.substring(eofEndIdx + 6, data.length());
		}
		
		System.out.println("return data : " + data);
		
		return data;
		
	}
	
	private void parseMsg(String msg) throws Exception{
		Document document = DocumentHelper.parseText(msg);
		Node nameNode = document.selectSingleNode( "//Trans/Name" );
		String transName = nameNode.getText();
		System.out.println("parse trans name : " + transName);
		if(transName.equals(GET_ALL)){
			System.out.println("recive [Get ALL] trans.");
			postAllData();
		}else if(transName.equals(CLEAR)){
			System.out.println("recive [Clear] trans.");
			clearQueue();
		}else if(transName.equals(DATA)){
			System.out.println("recive [Data] trans.");
			Node listNode = document.selectSingleNode( "//Trans/List" );
			
			for ( Iterator<Element> i = ((Element) listNode).elementIterator(); i.hasNext(); ) {
				Element element = i.next();
				System.out.println(element.asXML());
				MsgQueue.putMsg(element.asXML());
			}
		}
			
		
		
	}
	
	private void postAllData() throws IOException{
		System.out.println("post all data count : " + MsgQueue.msgQueueLength());
		
		if(MsgQueue.msgQueueLength() == 0){
			
			return;
		} 
		MsgQueue.lock();
		
		String msg = null;
//		String transMsg = null;
		StringBuffer transMsg = new StringBuffer();
		transMsg.append("<Trans>")
			.append("<Name>").append(DATA).append("</Name>")
			.append("<List>");
		Iterator<String> msgIter = MsgQueue.getIterator();
		while(msgIter.hasNext()){
			msg = msgIter.next();
			transMsg.append(msg);
		}
		
		transMsg.append("</List></Trans>");
		
		syncAgent.sendMsg(transMsg.toString());
//		output.write(transMsg.toString().getBytes());
		MsgQueue.unLock();
	}
	
	private void clearQueue(){
		MsgQueue.clear();
	}

	
	public void setSyncAgent(ISyncAgent syncAgent) {
		// TODO Auto-generated method stub
		this.syncAgent = syncAgent;
	}

	@Override
	public void init() {
		try {
			InternalSyncAgent.getInstance().sendGetAllDataRequest();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
