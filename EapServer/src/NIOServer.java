import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NIOServer nbTest = new NIOServer();
		try {
			nbTest.startServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void startServer() throws Exception{
		
		debug("Server start...");
		int nKeys = 0;
		
		//¨Ï¥ÎSelector
		Selector selector = Selector.open();
				
		
		ServerSocketChannel ssc = ServerSocketChannel.open();
		
		InetSocketAddress address = new InetSocketAddress(InetAddress.getLocalHost(),9000);
		debug("Server bind " + InetAddress.getLocalHost() + ":" + 9000);
		
		ssc.socket().bind(address);
		
		//set non blocking
		ssc.configureBlocking(false);
		
		SelectionKey s = ssc.register(selector, SelectionKey.OP_ACCEPT);
		//printKeyInfo(s);
		while(true){
			nKeys = selector.select();
			
			if(nKeys > 0){
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> i = selectedKeys.iterator();
				while(i.hasNext()){
					 s = i.next();
					 debug("NBTest: Nr Keys in selector: " +selector.selectedKeys().size());
					 
					 
					 debug("remove : " +selector.selectedKeys().size());
					 if(s.isAcceptable()){
						 debug("accept");
						 
						 Socket socket = ((ServerSocketChannel)s.channel()).accept().socket();
						 SocketChannel sc = socket.getChannel();
						 sc.configureBlocking(false);
						 sc.register(selector, SelectionKey.OP_READ |SelectionKey.OP_WRITE);
						 
						 
					 }else{
						 debug("NBTest: Channel not acceptable");
						 
					 }
					 i.remove();
					 
				}
			}
		}
		
	}
	
	private static void debug(String s){
		System.out.println(s);
	}
	
	
}
