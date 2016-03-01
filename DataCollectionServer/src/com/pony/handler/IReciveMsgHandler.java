package com.pony.handler;

public interface IReciveMsgHandler {
	String reciveMsg(String msg) throws Exception;
	
	void init();
}
