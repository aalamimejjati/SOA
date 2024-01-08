package fr.insa.soa.RestProject;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import java.util.ArrayList;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

@Path("comparator")
public class Comparator {
	public ArrayList<User> UserList = new ArrayList<User>();
	
	@GET
	@Path("/st")
	@Produces(MediaType.APPLICATION_JSON)
	public User get(@Context UriInfo uriInfo) {
		// UserList.add(user);
		User u=new User();
		u.setId("ab41");
		u.setAge(20);
		u.setEmail("ab@gmail.com");
		u.setName("Ab");
		u.setPassword("pws");
		u.setStatus("helper");
		u.setSurname("NG");
		u.addLink(getUriforSelf(uriInfo,u), "self", "GET");
		u.addLink(getUriforActivite(uriInfo), "Activite", "GET");
		 System.out.println("je suis la");
		 return u;
	}
	
	private String getUriforActivite(UriInfo uriInfo) {
		String url=uriInfo.getBaseUriBuilder()
		.path(ActiviteRessource.class)
		.build()
		.toString();
		return url;
	}
	
	private String getUriforSelf(UriInfo uriInfo, User user) {
		String url=uriInfo.getBaseUriBuilder()
		.path(Comparator.class)
		.path(user.getId())
		.build()
		.toString();
		return url;
	}
		

	@PUT
	@Path("/ajouter")
	@Consumes(MediaType.APPLICATION_JSON)
	public void add(User user) {
		UserList.add(user);
		System.out.println("l'utilisateur "+user.getName()+ "");
	}
	
	
	@PUT 
	@Path("/retirer")
	@Consumes(MediaType.APPLICATION_JSON)
	public void remove( User user) {
		UserList.remove(user);

	}
	
}
