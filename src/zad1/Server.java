/**
 *
 *  @author Szadurski Wojciech S17445
 *
 */

package zad1;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server {

	static HashMap<SelectionKey, ClientSession> clientMap = new HashMap<SelectionKey, ClientSession>();
	ServerSocketChannel serverChannel;
	Selector selector;

	Server(InetSocketAddress listenAddress) throws Throwable {
		serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);
		serverChannel.register(selector = Selector.open(), SelectionKey.OP_ACCEPT);
		serverChannel.bind(listenAddress);

		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
			try {
				loop();
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}, 0, 500, TimeUnit.MILLISECONDS);
		System.out.println("Server started.");

	}

	void loop() throws Throwable {
		selector.selectNow();

		for (SelectionKey key : selector.selectedKeys()) {
			if (!key.isValid())
				continue;
			if (key.isAcceptable()) {
				// Akceptowanie klucza i nowego klienta
				SocketChannel acceptedChannel = serverChannel.accept();
				if (acceptedChannel == null)
					continue;
				acceptedChannel.configureBlocking(false);
				SelectionKey readKey = acceptedChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

				clientMap.put(readKey, new ClientSession(readKey, acceptedChannel));
				System.out.println("New client connected");
			}
			if (key.isReadable()) {
				SocketChannel sc = (SocketChannel) key.channel();
				String response = "";
				if(sc.isOpen())
				response = OpClass.BBToString(sc);

				
				if (response != null && response.length() > 0) {
					System.out.println(response);

					for (Map.Entry<SelectionKey, ClientSession> map : clientMap.entrySet()) {
						if (map.getValue().selKey.isValid() && map.getValue().selKey.isWritable()) {
							SocketChannel sc2 = (SocketChannel) map.getKey().channel();

							ByteBuffer bb = Charset.forName("UTF-8").encode(response);
							sc2.write(bb);
							bb.clear();
							System.out.println(response + " : Sent to : " + sc2.getRemoteAddress());

						}

					}
				}
			}

		}
		selector.selectedKeys().clear();

	}

	public static void main(String[] args) throws Throwable {
		new Server(new InetSocketAddress("localhost", 9999));
	}

}
