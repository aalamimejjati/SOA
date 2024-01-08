package fr.insa.soa.RestProject;

import java.util.ArrayList;

public class User {
    private String id , name , surname , status , email ,password ;
    private int age ;
    private activite activite;
    private ArrayList<Link> links=new ArrayList<Link>();

    public User() {}
    public String getId() {return id;}

    public String getName() {return name;}

    public int getAge() {return age;}

    public String getEmail() {return email;}

    public String getStatus() {return status;}

    public String getSurname() {return surname;}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public void addLink(String uri, String rel, String methode) {
		Link newLink=new Link();
		newLink.setUri(uri);
		newLink.setRel(rel);
		newLink.setMethode(methode);
		links.add(newLink);
	}
	public ArrayList<Link>getLinks(){
		return links;
		
	}

}
