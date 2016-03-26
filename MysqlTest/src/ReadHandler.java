import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;

public class ReadHandler implements CompletionHandler<Integer, Attachment> {

	@Override
	public void completed(Integer result, Attachment attach) {
		if (result == -1) {
			try {
				attach.client.close();
				System.out.format("Stopped   listening to the   client %s%n",
			            attach.clientAddr);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return;
		}
		
		attach.buffer.flip();
		int limits = attach.buffer.limit();
		byte bytes[] = new byte[limits];
		attach.buffer.get(bytes, 0, limits);
		Charset cs = Charset.forName("UTF-8");
		String msg = new String(bytes, cs);
		System.out.format("Client at  %s  says: %s%n", attach.clientAddr,
		          msg);
		attach.buffer.rewind();
		
	}

	@Override
	public void failed(Throwable exc, Attachment attach) {
		exc.printStackTrace();
		
	}

}
