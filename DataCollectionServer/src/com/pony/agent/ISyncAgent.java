package com.pony.agent;

import java.io.IOException;

public interface ISyncAgent {
	void sendMsg(String msg) throws IOException;
	void checkConnection();
	void close();
}
