package com.pony.docker;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pony.utils.JsonConvert;
import com.pony.vo.DockerContainer;

public class Test {

	public static void main(String[] args) {
		String id = "17f63679ef18";
		try {
			String conterInfos = RemoteApi.listContainer();
			DockerContainer[] conts = JsonConvert.toDcokerContainer(conterInfos);
			
//			JSONArray objs = new JSONArray(conterInfos);
//			for(int i = 0 ; i<objs.length(); i++){
//				System.out.println(objs.getJSONObject(i).getString("Id")); 
//			}
//			String conterID = obj.getString("Id");
			
//			System.out.println(RemoteApi.listContainer());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
