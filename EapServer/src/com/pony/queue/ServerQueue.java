package com.pony.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerQueue {
	private static BlockingQueue<String> sendToSrvQue = new LinkedBlockingQueue<String>();
	
	public static void putSendToSrvQue(String msg) throws InterruptedException{
		sendToSrvQue.put(msg);
	}
	
	public static String pollSendToSrvQue() throws InterruptedException{
		return sendToSrvQue.take();
	}
	
	public static int SendToSrvQueLength(){
		return sendToSrvQue.size();
	}
}
