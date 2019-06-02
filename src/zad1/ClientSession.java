package zad1;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class ClientSession {
	SelectionKey selKey;
	SocketChannel chan;
	ByteBuffer buf;
	boolean isLogged = false;
	
	private String clientName = "";
	
	public ClientSession(SelectionKey selKey, SocketChannel chan) throws Throwable{
		this.selKey = selKey;
		this.chan = (SocketChannel) chan.configureBlocking(false);
		buf = ByteBuffer.allocateDirect(64);
		
	}
	
	void disconnect() {
		try {
			if(selKey == null) selKey.cancel();
			if(chan == null) return;
			System.out.println("bye bye" + (InetSocketAddress)chan.getRemoteAddress());
			chan.close();
		}catch (Throwable t) {//empty
			
		}
	}
}