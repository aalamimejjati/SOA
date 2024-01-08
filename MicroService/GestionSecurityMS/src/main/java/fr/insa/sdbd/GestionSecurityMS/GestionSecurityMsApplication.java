package fr.insa.sdbd.GestionSecurityMS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.insa.sdbd.GestionSecurityMS.db.db;

@SpringBootApplication

public class GestionSecurityMsApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(GestionSecurityMsApplication.class, args);
	}

}
