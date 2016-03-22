package com.pony.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.pony.vo.DockerContainer;

public class JsonConvert {
	public static DockerContainer[] toDcokerContainer(String json){
		List<DockerContainer> conterList = new ArrayList<DockerContainer>();
		JSONArray objs = new JSONArray(json);
		
		DockerContainer conter = null;
		for(int i = 0 ; i<objs.length(); i++){
			conter = new DockerContainer();
			conter.setId(objs.getJSONObject(i).getString("Id"));
			conter.setIp(objs.getJSONObject(i).getJSONObject("NetworkSettings").getJSONObject("Networks").getJSONObject("bridge").getString("IPAddress"));
//			System.out.println(conter.getId());
//			objs.getJSONObject(i).getJSONObject("NetworkSettings").getJSONObject("Networks").getJSONObject("bridge").getString("IPAddress");
			
//			System.out.println(objs.getJSONObject(i).getJSONObject("NetworkSettings").getJSONObject("Networks").getJSONObject("bridge").getString("IPAddress")); 
		}
		
		return conterList.toArray(new DockerContainer[conterList.size()]);
	}
}