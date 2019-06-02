/**
 *
 *  @author Szadurski Wojciech S17445
 *
 */

package zad1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import javax.swing.JFrame;

public class Client {
	JFrame frame;
	ChatPanel chatPan;
	String nick;
	Client c;
	SocketChannel s;

	public Client() {
		new LoginPanel(this);
	}

	public Client(String name) {
		c = this;
		nick = name;
		try {
			s = SocketChannel.open();
			s.connect(new InetSocketAddress("localhost", 9999));
			s.configureBlocking(false);
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}

		chatPan = new ChatPanel(c);
		new MessageReader(this, s).start();
		c.chatPan.appendToArea("Witaj na wspólnym czacie " + nick + "\n");
	}

	public void sendMessage(String text) {
		text += "\n";
		try {
			ByteBuffer buf = ByteBuffer.wrap(text.getBytes());
			while (buf.hasRemaining()) {
				c.s.write(buf);
			}
			buf.clear();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void main(String[] args) throws Throwable {
		new Client();
	}
}

class MessageReader extends Thread {
	Client c;
	SocketChannel s;

	MessageReader(Client c, SocketChannel s) {
		this.c = c;
		this.s = s;

	}

	public void run() {
		boolean runThread = true;
		while (runThread) {
			try {
				StringBuffer resultString = new StringBuffer();
				if (s.isOpen()) {
					
					resultString.setLength(0);
					ByteBuffer bb = ByteBuffer.allocate(1024);
					bb.clear();

					int loopCounter = 0;
					while (loopCounter < 500) {
						int n = c.s.read(bb);
						if (n > 0) {
							System.out.println("is read socket");
							bb.flip();
							CharBuffer cb = Charset.forName("UTF-8").decode(bb);
							while (cb.hasRemaining()) {
								char c = cb.get();
								resultString.append(c);
							}

						} else
							loopCounter++;
					}
				}
				if (resultString.length() > 0) {
					System.out.println(resultString.toString());
					c.chatPan.appendToArea(resultString.toString());
				}
			} catch (ClosedChannelException cce) {
				System.out.println("Channel closed"); runThread = false;
			} catch (IOException e) {
				System.out.println("IOException. " + e.getMessage()); c.chatPan.logoutButt.doClick(); runThread = false;
				return;
				
			}

		}
	}
}
