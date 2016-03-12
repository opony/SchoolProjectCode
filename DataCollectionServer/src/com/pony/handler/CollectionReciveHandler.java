package com.pony.handler;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.pony.agent.InternalSyncAgent;
import com.pony.dao.SescDataDao;
import com.pony.queue.MsgQueue;
import com.pony.vo.SescData;

public class CollectionReciveHandler implements IReciveMsgHandler {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
	String servName;
	public CollectionReciveHandler(String servName){
		this.servName = servName;
	}
	@Override
	public String reciveMsg(String data) throws Exception {
		String msg;
		int eofEndIdx = 0;
		while(data.contains("</EOF>")){
			eofEndIdx = data.indexOf("</EOF>");
			msg = data.substring(0,eofEndIdx);
			
			MsgQueue.putMsg(msg);
			InternalSyncAgent.GetInstance().syncMsgRequest(msg);
			data = data.substring(eofEndIdx + 6, data.length());
		}
		
		System.out.println("Data count : " + MsgQueue.msgQueueLength());
		if(MsgQueue.msgQueueLength() >= 1){
			insertDataToDb();
			
			InternalSyncAgent.GetInstance().sendClearRequest();
		}
		
		
		return data;
	}

	@Override
	public void init() {
		//nothing to do
	}
	
	private void insertDataToDb(){
		System.out.println("Insert db .");
		try {
			ArrayList<SescData> sescList = new ArrayList<SescData>();
			SescData sescData = null;
			
			while(MsgQueue.msgQueueLength() > 0){
				sescData = convertToSescData(MsgQueue.takeMsg());
				sescList.add(sescData);
			}
			
			SescDataDao.Insert(sescList);
			
		} catch (Exception e) {
			System.out.println("insert db fail" + e.getStackTrace());
		}
		
	}
	
	private SescData convertToSescData(String msg){
		SescData sescData = new SescData();
		sescData.serverName = this.servName;
		try {
			Document document = DocumentHelper.parseText(msg);
			Node timeNode = document.selectSingleNode( "//SESC/TIMESTAMP" );
			Date parsedDate = dateFormat.parse(timeNode.getText());
			sescData.time = new Timestamp(parsedDate.getTime());
			sescData.toolID = document.selectSingleNode( "//SESC/TOOL_ID" ).getText();
			
			Node listNode = document.selectSingleNode( "//SESC/List" );
			for ( Iterator<Element> i = ((Element) listNode).elementIterator(); i.hasNext(); ) {
				Element element = i.next();
				String itemName = element.selectSingleNode("./Name").getText();
				//int daIdx = Integer.parseInt(itemName.substring(9, itemName.length()));
				int daIdx = Integer.parseInt(itemName.substring(8));
				double value = Double.parseDouble(element.selectSingleNode("./Value").getText());
				sescData.datas[daIdx -1] = value;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sescData;
	}

}
