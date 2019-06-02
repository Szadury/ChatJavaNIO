package zad1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class OpClass {
	public static String BBToString(SocketChannel sc) {
		if(!sc.isOpen()) return "";
		
		StringBuffer result = new StringBuffer();
		
		ByteBuffer bb = ByteBuffer.allocate(1024);
		bb.clear();
		try {
			int amount_read = -1;
			amount_read = sc.read(bb);
			if(amount_read==-1) {
				return null;
			}
			if(amount_read>0) {
				bb.flip();
				CharBuffer cb = Charset.forName("UTF-8").decode(bb);
				while(cb.hasRemaining()) {
					char c = cb.get();
					result.append(c);
				}
				
			}
		}catch(IOException e) {try {
			sc.close();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		};
			
		return null;}
		return result.toString();
		
		
	}
}
