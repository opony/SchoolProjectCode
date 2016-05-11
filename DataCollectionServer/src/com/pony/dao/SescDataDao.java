package com.pony.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.mysql.jdbc.PreparedStatement;
import com.pony.vo.SescData;

public class SescDataDao {
	private static final String TABLE_NAME = "SESC_DATA";
	private static Logger logger = LogManager.getLogger("CollectionServer");
	public static String dbUrl = "jdbc:mysql://localhost:3306/ProjectDB";
	private static SimpleDateFormat df = new SimpleDateFormat("SSS");
	private static SimpleDateFormat dfFull = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static Connection conNorDB = null;
//	private static Statement stmt = null;
	public static void getConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//String url ="jdbc:mysql://localhost:3306/ProjectDB";
			String user="root";
			String password="Pony0110";
			conNorDB = DriverManager.getConnection(dbUrl, user, password);
//			stmt = conNorDB.createStatement();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void batchInsert(ArrayList<SescData> sescDataList){
//		Connection conDB = null;
		PreparedStatement ps = null;
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//String url ="jdbc:mysql://localhost:3306/ProjectDB";
			String user="root";
			String password="Pony0110";
			conNorDB = DriverManager.getConnection(dbUrl, user, password);
			String sql = "insert into " + TABLE_NAME + "(SERVER_NAME, TIMESTAMP, MS, TOOL_ID, DATA_ITEM1, DATA_ITEM2, " +
						"DATA_ITEM3, DATA_ITEM4, DATA_ITEM5, DATA_ITEM6, DATA_ITEM7, DATA_ITEM8, DATA_ITEM9, DATA_ITEM10, INSERT_TIME) " +
						"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())";
			ps = (PreparedStatement) conNorDB.prepareStatement(sql);
			for(SescData sescData : sescDataList){
				int i = 1;
				ps.setString(i++, sescData.serverName);
				ps.setTimestamp(i++, sescData.time);
				ps.setString(i++, df.format(sescData.time));				
				ps.setString(i++, sescData.toolID);
				ps.setDouble(i++, sescData.datas[0] );
				ps.setDouble(i++, sescData.datas[1] );
				ps.setDouble(i++, sescData.datas[2] );
				ps.setDouble(i++, sescData.datas[3] );
				ps.setDouble(i++, sescData.datas[4] );
				ps.setDouble(i++, sescData.datas[5] );
				ps.setDouble(i++, sescData.datas[6] );
				ps.setDouble(i++, sescData.datas[7] );
				ps.setDouble(i++, sescData.datas[8] );
				ps.setDouble(i++, sescData.datas[9] );
				
				ps.addBatch();
				
			}
			
			ps.executeBatch();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Insert db fail . ", e);
		}finally{
			try {
				if(ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
//			try {
//				if(conDB != null)
//					conDB.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
		}
	}
	
	
	public static void insert(SescData sescData) throws SQLException{
		
		
		Statement stmt = null;
		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			//String url ="jdbc:mysql://localhost:3306/ProjectDB";
//			String user="root";
//			String password="Pony0110";
//			conDB = DriverManager.getConnection(dbUrl, user, password);
//			
			stmt = conNorDB.createStatement();
			String sql = "insert into SESC_DATA (SERVER_NAME, TIMESTAMP, MS, TOOL_ID, DATA_ITEM1, DATA_ITEM2, " +
					"DATA_ITEM3, DATA_ITEM4, DATA_ITEM5, DATA_ITEM6, DATA_ITEM7, DATA_ITEM8, DATA_ITEM9, DATA_ITEM10, INSERT_TIME) " +
					"VALUES('" + sescData.serverName + "','" + dfFull.format(sescData.time) + "','" + df.format(sescData.time) + "','" + sescData.toolID + "'" +
							"," + sescData.datas[0] + ","+sescData.datas[1]+"," +
							sescData.datas[2]+ "," + sescData.datas[3] + "," + sescData.datas[4] +
									","+ sescData.datas[5] +"," + sescData.datas[6] + "," + sescData.datas[7] + ","+ sescData.datas[8] +"," + sescData.datas[9] + ",now())";
			stmt.execute(sql);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Insert db fail . ", e);
		}finally{
			stmt.close();
		}
		
	}
}
