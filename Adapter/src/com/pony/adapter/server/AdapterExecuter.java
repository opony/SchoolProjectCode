package com.pony.adapter.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.dom4j.Document;

import com.pony.adapter.client.BaseClient;
import com.pony.adapter.config.ConfigAttr;
import com.pony.adapter.config.ConfigXmlParser;
import com.pony.adapter.handler.ClientReciveHandler;
import com.pony.adapter.handler.ServerReciveHandler;
import com.pony.loader.XmlLoader;

public class AdapterExecuter {
//	private static int[] LISTEN_PORTS = new int[]{10010,10011};
	public static void main(String[] args) {
		
		
		
		ExecutorService threadExecutor = Executors.newCachedThreadPool();
		try {
			Document doc = XmlLoader.loadFile(args[0]);
			ConfigAttr configAttr = ConfigXmlParser.getConfigAttr(doc);
			
			for(int port : configAttr.listenPorts ){
				threadExecutor.execute(new BaseServer(port, new ServerReciveHandler()));
			}
			//String[] hostIps = new String[]{"localhost:10020","localhost:10021"};
			threadExecutor.execute(new BaseClient(configAttr.hosts, new ClientReciveHandler()));
//			while(true){}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			threadExecutor.shutdown();
		}
		
		
		

	}

}
