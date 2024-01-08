package fr.insa.soa.RestProject;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("activite")
public class ActiviteRessource {

	@GET
	@Produces (MediaType.APPLICATION_JSON)
	public activite getActivite(String id) {
		fr.insa.soa.RestProject.activite activite = new activite();
		activite.setDuree(15);
		activite.setItineraire("droite gauche tout droit");
		return activite;
		
	}
}
