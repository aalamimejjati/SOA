package fr.insa.sdbd.GestionSecurityMS.msResources;

//import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import fr.insa.sdbd.GestionSecurityMS.objects.Comment;
import fr.insa.sdbd.GestionSecurityMS.objects.Mission;
import fr.insa.sdbd.GestionSecurityMS.objects.User;

@RestController
public class Orchestrateur {


    @PostMapping("/addMission")
public ResponseEntity<String> addMission(@RequestBody Mission m) {
  
    RestTemplate restTemplate = new RestTemplate();
    User requester = restTemplate.getForObject("http://localhost:8083/user/" + m.getRequester(), User.class);

    if (requester != null && "ROLE_PATIENT".equals(requester.getStatus())) {
    
        ResponseEntity<String> response = restTemplate.postForEntity( "http://localhost:8081/addMission", m, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return new ResponseEntity<>("Mission added successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to add mission", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } else {
       
        return new ResponseEntity<>("Access denied. Requester is not a patient.", HttpStatus.FORBIDDEN);
    }
}


    @GetMapping("/getPendingMissions/{id}")
    public ResponseEntity<List<Mission>> getPendingMissions(@PathVariable int id) {
        RestTemplate restTemplate = new RestTemplate();
        User user = restTemplate.getForObject("http://localhost:8083/user/" + id, User.class);

        if (user != null && "ROLE_DOCTOR".equals(user.getStatus())) {
            List<Mission> missions = restTemplate.getForObject("http://localhost:8081/getMissionsByStatus/Pending", List.class);
            return new ResponseEntity<>(missions, HttpStatus.OK);
        } else {
            // Return 403 Forbidden if the user is not a doctor
            System.out.println("Access denied for user with id: "+ id);
            // Alternatively, you can use System.out.println("Access denied for user with id: " + id);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


    @GetMapping("/getValidatedMissions/{id}")
    public ResponseEntity<List<Mission>> getValidatedMissions(@PathVariable int id) {
        RestTemplate restTemplate = new RestTemplate();
        User user = restTemplate.getForObject("http://localhost:8083/user/" + id, User.class);

        if (user != null && "ROLE_HELPER".equals(user.getStatus())) {
            List<Mission> missions = restTemplate.getForObject("http://localhost:8081/getMissionsByStatus/Accepted", List.class);
            return new ResponseEntity<>(missions, HttpStatus.OK);
        } else {
            // Return 403 Forbidden if the user is not a doctor
            System.out.println("Access denied for user with id: "+ id);
            // Alternatively, you can use System.out.println("Access denied for user with id: " + id);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    


@PutMapping("/addValidator/{id_validator}")
public ResponseEntity<Void> addValidator(@PathVariable int id_validator, @RequestBody Mission m) {
    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<List<Mission>> response = restTemplate.exchange(
        "http://localhost:8081/getMissionsByStatus/Pending",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<Mission>>() {}
    );

    List<Mission> missions = response.getBody();
    boolean missionExists = missions.stream().anyMatch(mission -> mission.getID() == m.getID());
    System.out.println("THE SISE OF THE MISSIONS IS :"+missions.size());
    if (missionExists) {
        User user = restTemplate.getForObject("http://localhost:8083/user/" + id_validator, User.class);

        if (user != null && "ROLE_DOCTOR".equals(user.getStatus())) {
            System.out.println(m.getContent());
            m.setValidator(id_validator);
            
            restTemplate.put("http://localhost:8081/addValidator", m);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            // Return 403 Forbidden if the user is not a doctor
            System.out.println("Access denied for user with id: " + id_validator);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    } else {
        System.out.println("Mission: " + m.getID() + " is not pending");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

@PutMapping("/acceptMission/{id_volunteer}/{id_mission}")
public ResponseEntity<Void> acceptMission(@PathVariable int id_volunteer, @PathVariable int id_mission) {
    RestTemplate restTemplate = new RestTemplate();
    User user = restTemplate.getForObject("http://localhost:8083/user/" + id_volunteer, User.class);

    if (user != null && "ROLE_HELPER".equals(user.getStatus())) {
        ResponseEntity<List<Mission>> response = restTemplate.exchange(
                "http://localhost:8081/getMissionsByStatus/Accepted",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Mission>>() {}
        );

        List<Mission> missions = response.getBody();
        boolean missionExists = missions.stream().anyMatch(mission -> mission.getID() == id_mission);

        if (missionExists) {
            Mission m = new Mission();
            m.setID(id_mission);
            m.setVolunteer(id_volunteer);
            m.setStatus("Taking care of");

            restTemplate.put("http://localhost:8081/addVolunteer", m);
            
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            System.out.println("Mission: " + id_mission + " is not in 'Accepted' status");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } else {
        System.out.println("Access denied for user with id: " + id_volunteer);
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}

@PutMapping("/doneMission/{id_volunteer}/{id_mission}")
public ResponseEntity<Void> doneMission(@PathVariable int id_volunteer, @PathVariable int id_mission) {
    RestTemplate restTemplate = new RestTemplate();
    User user = restTemplate.getForObject("http://localhost:8083/user/" + id_volunteer, User.class);

    if (user != null && "ROLE_HELPER".equals(user.getStatus())) {
        ResponseEntity<List<Mission>> response = restTemplate.exchange(
                "http://localhost:8081/getMissionsByStatus/Taking care of",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Mission>>() {}
        );

        List<Mission> missions = response.getBody();
        boolean missionExists = missions.stream().anyMatch(mission -> mission.getID() == id_mission);
       

        if (missionExists ) {
            Mission m = new Mission();
            m.setID(id_mission);
            m.setStatus("Done");
            m.setVolunteer(id_volunteer);
            System.out.println("the id of m is " +m.getID() +"the requester is "+m.getRequester()+"the validaor is"+m.getValidator()+"the status is "+m.getStatus() );

            restTemplate.put("http://localhost:8081/addVolunteer", m);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            System.out.println("Mission: " + id_mission + " is not in 'Taking care of' status");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } else {
        System.out.println("Access denied for user with id: " + id_volunteer);
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}

@GetMapping("/getCommentsForMission/{missionId}")
public ResponseEntity<List<Comment>> getCommentsForMission(@PathVariable int missionId) {
    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<List<Mission>> missionsResponse = restTemplate.exchange(
        "http://localhost:8081/getMissionsByStatus/Done",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<Mission>>() {}
    );

    List<Mission> doneMissions = missionsResponse.getBody();
    boolean missionExistsAndDone = doneMissions.stream()
        .anyMatch(mission -> mission.getID() == missionId);

    if (missionExistsAndDone) {
        // Call Comment microservice to get comments for the specified mission
        ResponseEntity<List<Comment>> commentsResponse = restTemplate.exchange(
            "http://localhost:8085/getCommentsByMission/" + missionId,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Comment>>() {}
        );

        List<Comment> comments = commentsResponse.getBody();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}



@PostMapping("/addComment/{userId}")
public ResponseEntity<String> addComment(@PathVariable int userId, @RequestBody Comment comment) {
    RestTemplate restTemplate = new RestTemplate();

    User user = restTemplate.getForObject("http://localhost:8083/user/" + userId, User.class);
    System.out.println(user.getId());
        System.out.println(user.getName());

    if (user != null && "ROLE_PATIENT".equals(user.getStatus())) {
        comment.setRequester(userId);

       
        ResponseEntity<List<Mission>> missionsResponse = restTemplate.exchange(
            "http://localhost:8081/getMissionsByStatus/Done",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Mission>>() {}
        );

        List<Mission> doneMissions = missionsResponse.getBody();

       
        boolean missionExistsAndDone = doneMissions.stream()
            .anyMatch(mission -> mission.getID() == comment.getMission());

        if (missionExistsAndDone) {
            ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8085/addComment", comment, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return new ResponseEntity<>("Comment added successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to add comment", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Access denied. Mission not found or status is not 'Done'", HttpStatus.FORBIDDEN);
        }
    } else {
        return new ResponseEntity<>("Access denied. User is not a requester.", HttpStatus.FORBIDDEN);
    }
}


}


