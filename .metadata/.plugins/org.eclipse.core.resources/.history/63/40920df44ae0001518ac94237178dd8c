package com.pony.agent;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.pony.handler.SyncAgentReciveHander;
import com.pony.server.CollectionServer.ServerRole;

public class InternalSyncAgent {
			
	private static Logger logger = LogManager.getLogger("SyncAgent");
	
	private static final int PRIMARY_PORT = 10001;
//	private ExecutorService threadExecutor = Executors.newCachedThreadPool();
	
	private static ISyncAgent syncAgent;
	private static InternalSyncAgent instance;
	private String host = "192.168.60.1";
//	private ServerRole role;
	public InternalSyncAgent() throws FileNotFoundException, IOException{
		DOMConfigurator.configure("C:/LogConfig.xml");
	}
	
	public static InternalSyncAgent GetInstance(){
		if(instance == null)
			try {
				instance = new InternalSyncAgent();
			} catch (FileNotFoundException e) {
				logger.error("Log config file not found.",e);
			} catch (IOException e) {
				logger.error("Load Log config file error.",e);
			}
		return instance;
	}
	public void startAgent(ServerRole role){
		logger.info("Start agent : " + role.toString());
//		this.role = role;
		ExecutorService threadExecutor = Executors.newCachedThreadPool();
		if(ServerRole.Primary == role){
			logger.info("Listen port : " + PRIMARY_PORT);
			syncAgent = new SyncAgentServer(PRIMARY_PORT, new SyncAgentReciveHander() );
			threadExecutor.execute((SyncAgentServer)syncAgent);
		}else{
			logger.info("Connect to : " + host + ":" + PRIMARY_PORT );
			syncAgent = new SyncAgentClient(host, PRIMARY_PORT, new SyncAgentReciveHander());
			threadExecutor.execute((SyncAgentClient)syncAgent);
		}
	}
	
	public void syncMsgRequest(String msg) throws IOException{
		
		StringBuffer transMsg = new StringBuffer();
		
		transMsg.append("<Trans><Name>").append(SyncAgentReciveHander.DATA).append("</Name>")
			.append("<List>").append(msg).append("</List></Trans>");
		
		logger.debug("Sync Msg Request : " + transMsg);
		
		syncAgent.sendMsg(transMsg.toString());
//		if(ServerRole.Primary == role){
//			primAgent.sendMsg(transMsg.toString());
//		}
	}
	
	public void sendClearRequest() throws IOException{
		StringBuffer transMsg = new StringBuffer();
		transMsg.append("<Trans><Name>").append(SyncAgentReciveHander.CLEAR).append("</Name></Trans>");
		
		logger.debug("Send Clear Request : " + transMsg);
		
		syncAgent.sendMsg(transMsg.toString());
//		if(ServerRole.Primary == role){
//			primAgent.sendMsg(transMsg.toString());
//		}
		
	}
	
	public void sendGetAllDataRequest() throws IOException{
		StringBuffer transMsg = new StringBuffer();
		transMsg.append("<Trans><Name>").append(SyncAgentReciveHander.GET_ALL).append("</Name></Trans>");
		System.out.println("agent send : " + transMsg);
		
		logger.debug("Send Get All Data Request : " + transMsg);
		
		syncAgent.sendMsg(transMsg.toString());
//		if(ServerRole.Primary == role){
//			primAgent.sendMsg(transMsg.toString());
//		}
	}
	
	
}
