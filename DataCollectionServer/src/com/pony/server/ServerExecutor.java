package com.pony.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.dom4j.Document;

import com.pony.config.ConfigAttr;
import com.pony.config.ConfigParser;
import com.pony.dao.SescDataDao;
import com.pony.handler.CollectionReciveHandler;
import com.pony.loader.XmlLoader;
import com.pony.server.CollectionServer.ServerRole;

public class ServerExecutor {

	public static void main(String[] args) {
		ExecutorService threadExecutor = Executors.newCachedThreadPool();
    	
    	try {
    		Document doc = XmlLoader.loadFile(args[0]);
    		ConfigAttr configAttr = ConfigParser.getConfigAttr(doc);
    		SescDataDao.dbUrl = configAttr.dbUrl;
    		if(configAttr.role.equals("Primary")){
        		threadExecutor.execute(new CollectionServer(ServerRole.Primary,configAttr.primaryHost, configAttr.listenPort, new CollectionReciveHandler("Primary Server")));
        	}else if(configAttr.role.equals("Secondary")){
        		threadExecutor.execute(new CollectionServer(ServerRole.Secondary,configAttr.primaryHost, configAttr.listenPort, new CollectionReciveHandler("Secondary Server")));
        	}

        	while(true){}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	finally{
    		threadExecutor.shutdown();
    		
    	}

	}

}
