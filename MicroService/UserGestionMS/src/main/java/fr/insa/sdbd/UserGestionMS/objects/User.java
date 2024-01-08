package fr.insa.sdbd.UserGestionMS.objects;

public class User {
    private String  name , surname , status , email ,password ;
    private int id ,age ;
    

    public User() {}
    public int getId() {return id;}
	public void setId(int id) {this.id=id;}
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
	/*public User getUserById(String Id) {
		
		return u
	}*/

}
