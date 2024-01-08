package fr.insa.soap;

import javax.jws.WebService;
import javax.jws.WebParam;
import javax.jws.WebMethod;
import java.util.ArrayList;

@WebService(serviceName="UserService")
public class UserService {
	
	ArrayList<User> UserList = new ArrayList<User>();
	
	public UserService() {
		System.out.println(UserList);
		}
	
	@WebMethod(operationName="Add")
	public void addUser(@WebParam(name="user")User user) {
			UserList.add(user);
	}
	
	@WebMethod(operationName="Remove")
	public void removeUser(@WebParam(name="user")User user) {
			UserList.remove(user);
	}
	public void userList () {
		System.out.println(UserList);
	}

}
