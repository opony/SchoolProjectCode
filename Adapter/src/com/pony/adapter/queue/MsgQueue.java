package com.pony.adapter.queue;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class MsgQueue {
	private static BlockingQueue<String> msgQue = new LinkedBlockingQueue<String>();
	private static boolean isLock = false;
	private static Object lock = new Object();
	public static void putMsg(String msg) throws InterruptedException{
		while(isLock){
			Thread.sleep(100);
		}
		msgQue.put(msg);
	}
	
	public static String takeMsg() throws InterruptedException{
		while(getIsLock()){
			Thread.sleep(100);
		}
		
		return msgQue.take();
	}
	
	public static int msgQueueLength(){
		return msgQue.size();
	}
	
	public static void lock(){
		//lock
		synchronized(lock){
			MsgQueue.isLock = true;
		}
		
	}
	
	public static void unLock(){
		synchronized(lock){
			MsgQueue.isLock = false;
		}
	}
	public static boolean getIsLock(){
		synchronized(lock){
			return isLock;
		}
		
	}
	public static Iterator<String> getIterator(){
		return msgQue.iterator();
	}

	public static void clear() {
		msgQue.clear();
		
	}
}
