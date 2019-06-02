/**
 *
 *  @author Szadurski Wojciech S17445
 *
 */

package zad1;

import java.net.InetSocketAddress;

public class Main {

	public static void main(String[] args) {
		//starting server
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					new Server(new InetSocketAddress("localhost", 9999));
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					//Client w ktorym trzeba wpisac swoj nick
					new Client();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}).start();;
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {

					//Client ktory ma nick Wojtek
					new Client("Wojtek");
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
