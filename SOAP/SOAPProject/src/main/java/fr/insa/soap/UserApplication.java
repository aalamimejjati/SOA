package fr.insa.soap;

import java.net.MalformedURLException;
import javax.xml.ws.Endpoint;

public class UserApplication {

	public static String host ="localhost";
	public static short port = 8081;
	
	public void demarrerService() {
		String url = "http://"+host+":"+port+"/";
		UserService test = new UserService();
		Endpoint.publish(url, test);
	}
	
	public static void main(String [] args ) throws MalformedURLException {
		new UserApplication().demarrerService() ;
		System.out.print("service démarré");
	}
	
}
