package com.pony.server;


import com.pony.agent.InternalSyncAgent;
import com.pony.handler.IReciveMsgHandler;


public class CollectionServer extends BaseServer{
	
	public CollectionServer(ServerRole servRole,String primaryHost,  int port, IReciveMsgHandler reciveMsgHandler) {
		super(port, reciveMsgHandler);
		InternalSyncAgent.getInstance().startAgent(servRole, primaryHost);
	}

	public enum ServerRole {
	    Primary, Secondary
	} 
	
//	public static final int LISTEN_PORT = 9010;
	
	/**
     * @param args
     */
//    public static void main( String[] args )
//    {
//    	ExecutorService threadExecutor = Executors.newCachedThreadPool();
//    	
//    	try {
//    		Document doc = XmlLoader.loadFile(args[0]);
//    		ConfigAttr configAttr = ConfigParser.getConfigAttr(doc);
//    		
//    		if(configAttr.role.equals("Primary")){
//        		threadExecutor.execute(new CollectionServer(ServerRole.Primary,"", configAttr.listenPort, new CollectionReciveHandler("Primary Server")));
//        	}else if(args[0].equals("Secondary")){
//        		threadExecutor.execute(new CollectionServer(ServerRole.Secondary,configAttr.primaryHost, configAttr.listenPort, new CollectionReciveHandler("Secondary Server")));
//        	}
//
//        	while(true){}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    	finally{
//    		threadExecutor.shutdown();
//    		
//    	}
//    	
//    	
//    	
//    	
//    }
    
    
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
