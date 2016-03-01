package com.pony.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.mysql.jdbc.PreparedStatement;
import com.pony.vo.SescData;

public class SescDataDao {
	private static final String TABLE_NAME = "SESC_DATA";
	private static Logger logger = LogManager.getLogger("CollectionServer");
	
	public static void Insert(ArrayList<SescData> sescDataList){
		Connection conDB = null;
		PreparedStatement ps = null;
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url ="jdbc:mysql://localhost:3306/world";
			String user="root";
			String password="po0110";
			conDB = DriverManager.getConnection(url, user, password);
			String sql = "insert into " + TABLE_NAME + "(SERVER_NAME, TIMESTAMP, TOOL_ID, DATA_ITEM1, DATA_ITEM2, " +
						"DATA_ITEM3, DATA_ITEM4, DATA_ITEM5, DATA_ITEM6, DATA_ITEM7, DATA_ITEM8, DATA_ITEM9, DATA_ITEM10) " +
						"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = (PreparedStatement) conDB.prepareStatement(sql);
			for(SescData sescData : sescDataList){
				int i = 0;
				ps.setString(i++, sescData.serverName);
				ps.setTimestamp(i++, sescData.time);
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
				ps.setDouble(i++, sescData.datas[0] );
				ps.addBatch();
				
			}
			
			ps.executeBatch();
			
		} catch (Exception e) {
			logger.error("Insert db fail . ", e);
		}finally{
			try {
				if(ps != null)
					ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				if(conDB != null)
					conDB.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}