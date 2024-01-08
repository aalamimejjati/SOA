package fr.insa.sdbd.MissionGestionMS.ResourcesMS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.insa.sdbd.MissionGestionMS.db.DatabaseConnection;

import fr.insa.sdbd.MissionGestionMS.objects.Mission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GestionMission {

	    @Autowired
    private DatabaseConnection databaseConnection;
	
	@GetMapping("/missionId/{id}")
	public Mission getMission(@PathVariable int id) {
		Mission m = new Mission();
		m.setContent("pizza fruit de mer ");
		m.setRequester(id);
		return m;
		
	}
	
	@PostMapping("/addMission")
	public String addMission(@RequestBody Mission m) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        
		connection = databaseConnection.getConnection();

		String query = "INSERT INTO mission (requester_id, content , status) VALUES (?, ?, ?)";
		preparedStatement = connection.prepareStatement(query);

		// Set values for the prepared statement
		preparedStatement.setInt(1, m.getRequester());
		preparedStatement.setString(2, m.getContent());
		preparedStatement.setString(3, "Pending");
		

		// Execute the update
		int rowsAffected = preparedStatement.executeUpdate();

		if (rowsAffected > 0) {
			return "Mission added successfully";
		} else {
			return "Failed to add mission";
		}    
    }

	@PutMapping("/addValidator")
	public String updateValidator(@RequestBody Mission m) throws SQLException {
		Connection connection = null;
        PreparedStatement preparedStatement = null;

        
		connection = databaseConnection.getConnection();

		String query = "update mission set validator_id = ? , status = ?  where id = ? ";
		System.out.println(query);
		preparedStatement = connection.prepareStatement(query);

		// Set values for the prepared statement
		preparedStatement.setInt(1, m.getValidator());
		preparedStatement.setString(2, m.getStatus());
		preparedStatement.setInt(3, m.getID());
	
		// Execute the update
		int rowsAffected = preparedStatement.executeUpdate();

		if (rowsAffected > 0) {
			return "validator added successfully";
		} else {
			return "Failed to add validator";
		}  
	}

	@GetMapping("/getMissions/{id}")
	public List<Mission> getMissionsByRequester(@PathVariable int id) throws SQLException {
		Connection connection = null;
		List<Mission> missions = new ArrayList<>();

		try {
			connection = databaseConnection.getConnection();

			String query = "select * from mission where requester_id = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				preparedStatement.setInt(1, id);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						Mission m = new Mission();
						// Set mission attributes from the ResultSet
						m.setID(resultSet.getInt("id"));
						m.setRequester(resultSet.getInt("requester_id"));
						m.setVolunteer(resultSet.getInt("volunteer_id"));
						m.setContent(resultSet.getString("content"));
						m.setStatus(resultSet.getString("status"));
						missions.add(m);
					}
				}
			}
		} finally {
			// Close the connection in a finally block to ensure it's always closed
			if (connection != null) {
				connection.close();
			}
		}

		return missions;
	}
	
	@GetMapping("/getMissionsByStatus/{status}")
	public List<Mission> getPendingMissions(@PathVariable String status) throws SQLException {
		Connection connection = null;
		List<Mission> pendingMissions = new ArrayList<>();

		try {
			connection = databaseConnection.getConnection();
			
			String query = "select * from mission where status = ? " ;

			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				preparedStatement.setString(1, status);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next()) {
						Mission m = new Mission();
						// Set mission attributes from the ResultSet
						m.setID(resultSet.getInt("id"));
						m.setRequester(resultSet.getInt("requester_id"));
						m.setVolunteer(resultSet.getInt("volunteer_id"));
						m.setContent(resultSet.getString("content"));
						m.setStatus(resultSet.getString("status"));
						pendingMissions.add(m);
					}
				}
			}
		} finally {
			// Close the connection in a finally block to ensure it's always closed
			if (connection != null) {
				connection.close();
			}
    }

    return pendingMissions;
	}

	@PutMapping("/addVolunteer")
	public String addVolunteer(@RequestBody Mission m) throws SQLException {
		Connection connection = null;
        PreparedStatement preparedStatement = null;

        
		connection = databaseConnection.getConnection();

		String query = "update mission set volunteer_id = ? , status = ?  where id = ? ";
		System.out.println(query);
		preparedStatement = connection.prepareStatement(query);

		// Set values for the prepared statement
		preparedStatement.setInt(1, m.getVolunteer());
		preparedStatement.setString(2, m.getStatus());
		preparedStatement.setInt(3, m.getID());
		System.out.println(preparedStatement);
	
		// Execute the update
		int rowsAffected = preparedStatement.executeUpdate();

		if (rowsAffected > 0) {
			return "volunteer added successfully";
		} else {
			return "Failed to add volunteer";
		}  
	}

	@DeleteMapping("/deleteMission")
	public String deleteMission(@RequestBody String id) throws SQLException {
		Connection connection = null;
        PreparedStatement preparedStatement = null;

        
		connection = databaseConnection.getConnection();

		String query = "delete from mission where id = ? ";
		
		preparedStatement = connection.prepareStatement(query);

		// Set values for the prepared statement
	
		preparedStatement.setString(1, id);
		
	
		System.out.println(query);
	
		// Execute the update
		int rowsAffected = preparedStatement.executeUpdate();

		if (rowsAffected > 0) {
			return "mission deleted successfully";
		} else {
			return "Failed to delete mission";
		}  
	}


}