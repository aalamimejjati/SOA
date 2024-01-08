package clientWS;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import fr.insa.soap.wsdltojava.Add;
import fr.insa.soap.wsdltojava.User;
import fr.insa.soap.wsdltojava.UserService;
//import fr.insa.soap.wsdltojava.UserService_Service;


public class ClientOfSOAPProject {
	public static void main(String [] args) throws MalformedURLException {
		final String adresse="http://localhost:8081/UserService";
		final URL url=URI.create(adresse).toURL();
		final User service = new User();
		final UserService port = service.getPort(UserService.class);
		
		User util = new User();
		util.setId("123");
		util.setName("test");
		
		port.add(util);
		port.userList();
		port.remove(util);
		port.userList();
		

				
	}

}
