package com.pony.adapter.config;

import java.util.Arrays;

import org.dom4j.Document;
import org.dom4j.Node;

public class ConfigXmlParser {
	public static ConfigAttr getConfigAttr(Document doc){
		ConfigAttr configAttr = new ConfigAttr();
		
		Node hostsNode = doc.selectSingleNode("//Config/Hosts");
		configAttr.hosts = hostsNode.getText().split(",");
		
		Node listsNode = doc.selectSingleNode("//Config/ListenPorts");
		String[] strs = listsNode.getText().split(",");
		configAttr.listenPorts = Arrays.asList(strs).stream().mapToInt(Integer::parseInt).toArray();
		
		return configAttr;
				
	}
}
