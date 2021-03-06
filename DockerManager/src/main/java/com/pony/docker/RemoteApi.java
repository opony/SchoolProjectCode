package com.pony.docker;


import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class RemoteApi {
	private static final String URL = "http://schoolproject.cloudapp.net:2375";
	
	/*
	 * Start a container
	 * Example request:
	 * POST /containers/(id)/start HTTP/1.1
	 * 
	 * Example response:
	 * HTTP/1.1 204 No Content
	 * 
	 * Status Codes:

	    204 �V no error
	    304 �V container already started
	    404 �V no such container
	    500 �V server error
	*/	
	public static int startContainer(String conterID) throws Exception{
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(URL + "/containers/" + conterID + "/start");
		int statusCode = client.executeMethod(post);
		post.releaseConnection();
		return statusCode;
	}
	
	/*
	 * Stop a container
	 * POST /containers/(id)/stop
	 * Example request:

		POST /containers/e90e34656806/stop?t=5 HTTP/1.1
		
	 * Example response:

		HTTP/1.1 204 No Content
	 * Query Parameters:

    	t �V number of seconds to wait before killing the container

	Status Codes:
	
	    204 �V no error
	    304 �V container already stopped
	    404 �V no such container
	    500 �V server error
*/
	public static int stopContainer(String conterID) throws Exception{
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(URL + "/containers/" + conterID + "/stop?t=5");
		int statusCode = client.executeMethod(post);
		post.releaseConnection();
		return statusCode;
	}
	
	
	/*Remove a container
	 * DELETE /containers/(id)
	 * Example request:

		DELETE /containers/16253994b7c4?v=1 HTTP/1.1
	 *Example response:

		HTTP/1.1 204 No Content
	 *
	 *Query Parameters:

	    v �V 1/True/true or 0/False/false, Remove the volumes associated to the container. Default false.
	    force - 1/True/true or 0/False/false, Kill then remove the container. Default false.
	
	  Status Codes:
	    204 �V no error
	    400 �V bad parameter
	    404 �V no such container
	    500 �V server error
	 *
	 *
*/
	public static int removeContainer(String conterID)throws Exception{
		HttpClient client = new HttpClient();
		DeleteMethod delete = new DeleteMethod(URL + "/containers/" + conterID + "?v=1");
		int statusCode = client.executeMethod(delete);
		delete.releaseConnection();
		return statusCode;
	}
	
	/*Create a container
	 * POST /containers/create
	 * Example request:
	 * POST /containers/create HTTP/1.1
		Content-Type: application/json
		
		{
		       "Hostname": "",
		       "Domainname": "",
		       "User": "",
		       "AttachStdin": false,
		       "AttachStdout": true,
		       "AttachStderr": true,
		       "Tty": false,
		       "OpenStdin": false,
		       "StdinOnce": false,
		       "Env": [
		               "FOO=bar",
		               "BAZ=quux"
		       ],
		       "Cmd": [
		               "date"
		       ],
		       "Entrypoint": "",
		       "Image": "ubuntu",
		       "Labels": {
		               "com.example.vendor": "Acme",
		               "com.example.license": "GPL",
		               "com.example.version": "1.0"
		       },
		       "Mounts": [
		         {
		           "Name": "fac362...80535",
		           "Source": "/data",
		           "Destination": "/data",
		           "Driver": "local",
		           "Mode": "ro,Z",
		           "RW": false,
		           "Propagation": ""
		         }
		       ],
		       "WorkingDir": "",
		       "NetworkDisabled": false,
		       "MacAddress": "12:34:56:78:9a:bc",
		       "ExposedPorts": {
		               "22/tcp": {}
		       },
		       "StopSignal": "SIGTERM",
		       "HostConfig": {
		         "Binds": ["/tmp:/tmp"],
		         "Links": ["redis3:redis"],
		         "Memory": 0,
		         "MemorySwap": 0,
		         "MemoryReservation": 0,
		         "KernelMemory": 0,
		         "CpuShares": 512,
		         "CpuPeriod": 100000,
		         "CpuQuota": 50000,
		         "CpusetCpus": "0,1",
		         "CpusetMems": "0,1",
		         "BlkioWeight": 300,
		         "BlkioWeightDevice": [{}],
		         "BlkioDeviceReadBps": [{}],
		         "BlkioDeviceReadIOps": [{}],
		         "BlkioDeviceWriteBps": [{}],
		         "BlkioDeviceWriteIOps": [{}],
		         "MemorySwappiness": 60,
		         "OomKillDisable": false,
		         "OomScoreAdj": 500,
		         "PortBindings": { "22/tcp": [{ "HostPort": "11022" }] },
		         "PublishAllPorts": false,
		         "Privileged": false,
		         "ReadonlyRootfs": false,
		         "Dns": ["8.8.8.8"],
		         "DnsOptions": [""],
		         "DnsSearch": [""],
		         "ExtraHosts": null,
		         "VolumesFrom": ["parent", "other:ro"],
		         "CapAdd": ["NET_ADMIN"],
		         "CapDrop": ["MKNOD"],
		         "GroupAdd": ["newgroup"],
		         "RestartPolicy": { "Name": "", "MaximumRetryCount": 0 },
		         "NetworkMode": "bridge",
		         "Devices": [],
		         "Ulimits": [{}],
		         "LogConfig": { "Type": "json-file", "Config": {} },
		         "SecurityOpt": [""],
		         "CgroupParent": "",
		         "VolumeDriver": "",
		         "ShmSize": 67108864
		      }
		  }
	 * Example response:
	
	  HTTP/1.1 201 Created
	  Content-Type: application/json
	  {
	       "Id":"e90e34656806",
	       "Warnings":[]
	  }

	 * */
	public static String createContainer(String image) throws Exception{
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(URL + "/containers/create");
		StringRequestEntity requestEntity = new StringRequestEntity(
			    "{\"Image\": \"" + image + "\"}",
			    "application/json",
			    "UTF-8");
		post.setRequestEntity(requestEntity);
		int statusCode = client.executeMethod(post);
		
		InputStream in = post.getResponseBodyAsStream();
		
		String res = IOUtils.toString(in, "UTF-8");
		post.releaseConnection();
		
		JSONObject obj = new JSONObject(res);
		String conterID = obj.getString("Id");
		
		return conterID;
		
	}
	
	/*List containers
	 * GET /containers/json
	 * Example request:
		GET /containers/json?all=1&before=8dfafdbc3a40&size=1 HTTP/1.1
	 *
	 *Status Codes:	
	    200 �V no error
	    400 �V bad parameter
	    500 �V server error

	 * */
	public static String listContainer() throws Exception{
		HttpClient client = new HttpClient();
		GetMethod get = new GetMethod(URL + "/containers/json?all=1");
		client.executeMethod(get);
		InputStream in = get.getResponseBodyAsStream();
		String res = IOUtils.toString(in, "UTF-8");
		get.releaseConnection();
		return res;
	}
}
