package com.pony.loader;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

public class XmlLoader {
	public static Document loadFile(String filePath) throws Exception{
		
		File fXmlFile = new File(filePath);
		SAXReader reader = new SAXReader();
		Document document = reader.read(fXmlFile);
    	
		return document;
	}
}