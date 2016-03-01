import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.pony.queue.*;
public class Server {
public static final int LISTEN_PORT = 9000;
    
    public void listenRequest(){
        ServerSocket serverSocket = null;
        ExecutorService threadExecutor = Executors.newCachedThreadPool();
        try
        {
            serverSocket = new ServerSocket( LISTEN_PORT );
            System.out.println("Server listening requests...");
            
            System.out.println("accept...");
        	
            while(true){
            	Socket socket = serverSocket.accept();
                threadExecutor.execute( new RequestThread( socket ) );
                System.out.println("request thread end...");
            }
            
            
            //threadExecutor.execute( new UploadMsgThread() );
            
            
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            if ( threadExecutor != null )
                threadExecutor.shutdown();
            if ( serverSocket != null )
                try
                {
                    serverSocket.close();
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
        }
    }
    
    /**
     * @param args
     */
    public static void main( String[] args )
    {
        Server server = new Server();
        server.listenRequest();
    }
    
    /**
     * 處理Client端的Request執行續。
     *
     * @version
     */
    class RequestThread implements Runnable
    {
        private Socket clientSocket;
        
        public RequestThread( Socket clientSocket )
        {
            this.clientSocket = clientSocket;
        }
        
        /* (non-Javadoc)
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run()
        {
            System.out.printf("有%s連線進來!\n", clientSocket.getRemoteSocketAddress() );
            BufferedInputStream input = null;
            BufferedOutputStream output = null;
            
            try
            {
                input = new BufferedInputStream( this.clientSocket.getInputStream() );
                byte[] b = new byte[1024];
                String data = "";
                
                int length;
                
                output = new BufferedOutputStream( this.clientSocket.getOutputStream() );
                while ( true )
                {
                	while ((length = input.read(b)) > 0){
                		data += new String(b, 0, length);
                		if(data.contains("</EOF>")){
                			data = EnServerQueue(data);
                		}
                	}
//                    output.writeUTF( String.format("Hi, %s!\n", clientSocket.getRemoteSocketAddress() ) );
//                    output.flush();
//                    // TODO 處理IO，這邊定義protocol協定！！
//
//                    break;
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
            catch(Exception ex){
            	ex.printStackTrace();
            }finally 
            {
                try
                {
                    if ( input != null )
                        input.close();
                    if ( output != null )
                        output.close();
                    if ( this.clientSocket != null && !this.clientSocket.isClosed() )
                        this.clientSocket.close();
                }
                catch ( IOException e )
                {
                    e.printStackTrace();
                }
            }
        }
        
        public String EnServerQueue(String data) throws InterruptedException{
        	String msg;
        	int eofEndIdx = 0;
        	while(data.contains("</EOF>")){
        		eofEndIdx = data.indexOf("</EOF>") + 6;
        		msg = data.substring(0,eofEndIdx);
        		System.out.println("enqueue msg : " + msg);
        		ServerQueue.putSendToSrvQue(msg);
        		System.out.println("queue size : " + ServerQueue.SendToSrvQueLength());
        		data = data.substring(eofEndIdx, data.length());
        	}
        	
        	System.out.println("return data : " + data);
        	
        	return data;
        }
    }
}
