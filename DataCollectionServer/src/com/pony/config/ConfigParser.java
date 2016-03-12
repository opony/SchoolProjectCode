package com.pony.config;

import org.dom4j.Document;


public class ConfigParser {
	public static ConfigAttr getConfigAttr(Document doc){
		ConfigAttr configAttr = new ConfigAttr();
		
		String role = doc.selectSingleNode("//Config/Role").getText();
		configAttr.role = role;
		
		String strPort = doc.selectSingleNode("//Config/ListenPort").getText();
		
		configAttr.listenPort = Integer.parseInt(strPort);
		
		configAttr.primaryHost = doc.selectSingleNode("//Config/PirmaryHost").getText();
		configAttr.dbUrl = doc.selectSingleNode("//Config/DBUrl").getText();
		return configAttr;
				
	}
}
