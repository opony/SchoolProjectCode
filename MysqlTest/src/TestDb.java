import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

public class TestDb {

//	public static void main(String[] args) {
//		Connection conDB = null;
//		Statement stmt = null;
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			String url ="jdbc:mysql://localhost:3306/world";
//			
//			
//			String user="root";
//			String password="po0110";
//			conDB = DriverManager.getConnection(url, user, password);
//			stmt = conDB.createStatement();
//			
//			String sql = "select * from city where id = 1";
//			
//			stmt.executeQuery(sql);
//			ResultSet rs = stmt.getResultSet();
//			while(rs.next()){
//				String str = rs.getString("NAME");
//				System.out.println(str);
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally{
//			
//			try {
//				stmt.close();
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			
//			try {
//				conDB.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//	}
	
//	public static void main(String[] args) {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
//		StringBuilder xml = new StringBuilder();
//		xml.append("<SESC>")
//			.append("<Timestamp>2016-02-02 12:00:56.780</Timestamp>")
//			.append("<TOOL_ID>TOOL1</TOOL_ID>")
//			.append("<List>")
//			.append("	<DataItem>")
//			.append("		<Name>DataItem1</Name>")
//			.append("		<Value>10.1</Value>")
//			.append("	</DataItem>")
//			.append("	<DataItem>")
//			.append("		<Name>DataItem2</Name>")
//			.append("		<Value>12.1</Value>")
//			.append("	</DataItem>")
//			.append("</List>")
//			.append("</SESC>");
//		
//		
//		try {
//			Document document = DocumentHelper.parseText(xml.toString());
//			Node timeNode = document.selectSingleNode( "//SESC/Timestamp" );
//			Date parsedDate = dateFormat.parse(timeNode.getText());
//			Timestamp timestamp = new Timestamp(parsedDate.getTime());
//			String toolID = document.selectSingleNode( "//SESC/TOOL_ID" ).getText();
//			
//			Node listNode = document.selectSingleNode( "//SESC/List" );
//			for ( Iterator<Element> i = ((Element) listNode).elementIterator(); i.hasNext(); ) {
//				Element element = i.next();
//				String name = element.selectSingleNode("./Name").getText();
//				double value = Double.parseDouble(element.selectSingleNode("./Value").getText());
//				System.out.println(name);
//			}
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	
	/*
	 * ping sample*/
//	public static void main(String[] args) {
//	    InetAddress hostname;
//		try {
//			hostname = InetAddress.getByName("192.168.218.1");
//			Boolean a=hostname.isReachable(2000);
//			System.out.println(a);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    
//
//	}
	
	
//	public static void main(String[] args) {
//	    
//		try {
//
//			AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
//			String host = "localhost";
//			int port = 8001;
//			InetSocketAddress sAddr = new InetSocketAddress(host, port);
//		    server.bind(sAddr);
//		    System.out.format("Server is listening at %s%n", sAddr);
//		    Attachment attach = new Attachment();
//		    attach.server = server;
//		    server.accept(attach, new ConnectionHandler());
//		    Thread.currentThread().join();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    
//
//	}
	
	
	public static void main(String[] args) {
	    
		try {

			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    

	}
	
	
	

}
