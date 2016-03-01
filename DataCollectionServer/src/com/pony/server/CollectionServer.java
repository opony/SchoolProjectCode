package com.pony.server;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import com.pony.agent.InternalSyncAgent;
import com.pony.handler.CollectionReciveHandler;
import com.pony.handler.IReciveMsgHandler;




public class CollectionServer extends BaseServer{
	
	public CollectionServer(ServerRole servRole, int port, IReciveMsgHandler reciveMsgHandler) {
		super(port, reciveMsgHandler);
		InternalSyncAgent.GetInstance().startAgent(servRole);
	}

	public enum ServerRole {
	    Primary, Secondary
	} 
	
	public static final int LISTEN_PORT = 9010;
	
	/**
     * @param args
     */
    public static void main( String[] args )
    {
    	
    	try {
    		
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    	
    	ExecutorService threadExecutor = Executors.newCachedThreadPool();
    	if(args[0].equals("Primary")){
    		threadExecutor.execute(new CollectionServer(ServerRole.Primary, LISTEN_PORT, new CollectionReciveHandler("Primary Server")));
    	}
    		
    	while(true){}
    }
    
    
//    public static void main( String[] args )
//    {
//    	if(args[0].equals("client")){
//    		
//    		InternalSyncAgent.GetInstance().startAgent(ServerRole.Secondary);
//    		try {
//				Thread.sleep(2000);
//				InternalSyncAgent.GetInstance().syncMsgRequest("<e>e</e>");
//				Thread.sleep(2000);
//				InternalSyncAgent.GetInstance().sendClearRequest();
//				
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//    		
//    	}else{
//    		InternalSyncAgent.GetInstance().startAgent(ServerRole.Primary);
//    		
//    	}
//    	
//    }

}