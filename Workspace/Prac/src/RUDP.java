import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import net.rudp.ReliableSocket;
import java.io.FileInputStream; 
import java.io.OutputStream;
import java.io.RandomAccessFile;
import net.rudp.ReliableServerSocket;

public class RUDP {
	public static void main(String[] args) throws IOException {
		new UDPtests();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new UDPtestc();
	}
}

class UDPtests implements Runnable {
	ReliableServerSocket rss;
	ReliableSocket rs;
	public UDPtests() throws IOException {
		rss = new ReliableServerSocket(9131);
	    Thread serverthread = new Thread(this);
	    serverthread.start();
	}
	
	public void run() {
		while (true) {
			try {
				rs =  (ReliableSocket)rss.accept();
	            System.out.println("Connection Accepted");
	            System.out.println("" + rs.getRemoteSocketAddress());
	            
	            byte[] sendData = "vj rox".getBytes();
		        OutputStream os = rs.getOutputStream();
		        int bytesReceived = 0;
		        while (true) {
		        	System.out.println("Server Sending");
		        	os.write(sendData, 0, bytesReceived);
		        	Thread.sleep(1000);
		        }
	        } 
			catch (IOException ex) {
	            ex.printStackTrace();
	        } 
			catch (InterruptedException e) {
				e.printStackTrace();
			}	
	    }
	}
}

class UDPtestc {
	ReliableSocket server;
	public UDPtestc() throws IOException {
		server = new ReliableSocket();
	    server.connect(new InetSocketAddress("127.0.0.1", 9131));
	    
	    byte[] buffer = new byte[1024];
	    InputStream in = server.getInputStream();
	    int ctr = 0;
	    
	    while(ctr < 100) {
	    	ctr++;
	    	in.read(buffer);
	    	System.out.println("Client Receiving");
	    	System.out.println(buffer);
	    }
	    server.close();            
	}
}