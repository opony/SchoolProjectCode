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

import com.mysql.jdbc.PreparedStatement;

import vo.SescData;

public class TestDb {
	private static SimpleDateFormat df = new SimpleDateFormat("SSS");
	private static SimpleDateFormat dfFull = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
//			batchInsert();
			insertDB();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    

	}
	
	public static void batchInsert() throws Exception{
		Connection conDB = null;
		PreparedStatement ps = null;
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			String url ="jdbc:mysql://192.168.45.131:4306/world?rewriteBatchedStatements=true";
			
			
			String user="root";
			String password="Pony0110";
			conDB = DriverManager.getConnection(url, user, password);
			String sql = "insert into SESC_DATA(SERVER_NAME, TIMESTAMP, MS, TOOL_ID, DATA_ITEM1, DATA_ITEM2, " +
					"DATA_ITEM3, DATA_ITEM4, DATA_ITEM5, DATA_ITEM6, DATA_ITEM7, DATA_ITEM8, DATA_ITEM9, DATA_ITEM10, INSERT_TIME) " +
					"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())";
			ps = (PreparedStatement) conDB.prepareStatement(sql);
			SescData sescData = new SescData();
			sescData.serverName = "test1";
			sescData.time  = new Timestamp(System.currentTimeMillis());
			sescData.toolID = "tool1";
			long st = System.currentTimeMillis();
			Timestamp stTime = new Timestamp(System.currentTimeMillis());
			for(int idx =1; idx<=50000; idx++){
				int i = 1;
				ps.setString(i++, sescData.serverName);
				ps.setTimestamp(i++, sescData.time);
				ps.setString(i++, idx + "");				
				ps.setString(i++, sescData.toolID);
				ps.setDouble(i++, 1 );
				ps.setDouble(i++, 2 );
				ps.setDouble(i++, 3 );
				ps.setDouble(i++, 4 );
				ps.setDouble(i++, 5 );
				ps.setDouble(i++, 6 );
				ps.setDouble(i++, 7 );
				ps.setDouble(i++, 8 );
				ps.setDouble(i++, 9 );
				ps.setDouble(i++, 10 );
				
				ps.addBatch();
				if((idx % 1000) == 0){
					ps.executeBatch();
					ps.clearBatch();
				}
			}
			Timestamp endTime = new Timestamp(System.currentTimeMillis());
			
			long end = System.currentTimeMillis();
			Timestamp result = new Timestamp(end - st);
			System.out.println("b");
			
			
//			ResultSet rs = stmt.getResultSet();
//			while(rs.next()){
//				String str = rs.getString("NAME");
//				System.out.println(str);
//			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				ps.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				conDB.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public static void insertDB() throws Exception{
		Connection conDB = null;
		Statement stmt = null;
		try {
			SimpleDateFormat dfFull = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Class.forName("com.mysql.jdbc.Driver");
			String url ="jdbc:mysql://192.168.218.128:4306/world?rewriteBatchedStatements=true";
			
			
			String user="root";
			String password="Pony0110";
			conDB = DriverManager.getConnection(url, user, password);
			
			SescData sescData = new SescData();
			sescData.serverName = "test12";
			sescData.time  = new Timestamp(System.currentTimeMillis());
			sescData.toolID = "tool1";
			long st = System.currentTimeMillis();
			
			for(int i =0; i<2; i++){
				stmt = conDB.createStatement();
				String sql = "insert into SESC_DATA (SERVER_NAME, TIMESTAMP, MS, TOOL_ID, DATA_ITEM1, DATA_ITEM2, " +
						"DATA_ITEM3, DATA_ITEM4, DATA_ITEM5, DATA_ITEM6, DATA_ITEM7, DATA_ITEM8, DATA_ITEM9, DATA_ITEM10, INSERT_TIME) " +
						"VALUES('" + sescData.serverName + "','" + dfFull.format(sescData.time) + "','" + i + "','" + sescData.toolID + "'" +
								"," + sescData.datas[0] + ","+sescData.datas[1]+"," +
								sescData.datas[2]+ "," + sescData.datas[3] + "," + sescData.datas[4] +
										","+ sescData.datas[5] +"," + sescData.datas[6] + "," + sescData.datas[7] + ","+ sescData.datas[8] +"," + sescData.datas[9] + ",now())";
				//stmt.executeQuery(sql);
				System.out.println("insert db");
				stmt.execute(sql);
				stmt.close();
			}
			
			
			long end = System.currentTimeMillis();
			Timestamp result = new Timestamp(end - st);
			System.out.println("b");
			
			
//			ResultSet rs = stmt.getResultSet();
//			while(rs.next()){
//				String str = rs.getString("NAME");
//				System.out.println(str);
//			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				stmt.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				conDB.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	

}
