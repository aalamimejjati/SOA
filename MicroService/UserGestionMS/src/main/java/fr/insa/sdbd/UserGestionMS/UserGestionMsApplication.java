package fr.insa.sdbd.UserGestionMS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.insa.sdbd.UserGestionMS.db.DatabaseConnection;

@SpringBootApplication
public class UserGestionMsApplication {

	
	public static void main(String[] args) {
					
		SpringApplication.run(UserGestionMsApplication.class, args);
	}

}
