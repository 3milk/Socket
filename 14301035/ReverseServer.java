package socket1;

import socket1.ReverseServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;


public class ReverseServer {
	public ReverseServer() {
		try 
		{
			ServerSocket server = new ServerSocket(3333);
			
			while (true) 
			{
				Socket sc = server.accept();
				Reverse t = new Reverse(sc);
				t.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new ReverseServer();
	}
	
    class Reverse extends Thread{
    	private Socket socket;
    	
    	public Reverse(Socket s){
    		socket=s;
    	}
    	
    	@Override
        public void run() {
    		
    		BufferedReader buf;
            BufferedWriter bufout;
			try {
				buf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				bufout = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				String line = null;
	    		while((line=buf.readLine())!=null) {
	    			StringBuffer re = new StringBuffer(line);
	    			String str = re.reverse().toString();

	    			bufout.write(str);
	    			bufout.newLine();
	    			bufout.flush();
	    		}
	            socket.close();           
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
}
