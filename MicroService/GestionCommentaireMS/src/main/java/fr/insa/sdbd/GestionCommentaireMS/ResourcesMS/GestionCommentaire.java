package fr.insa.sdbd.GestionCommentaireMS.ResourcesMS;

import fr.insa.sdbd.GestionCommentaireMS.bd.DatabaseConnection;

import fr.insa.sdbd.GestionCommentaireMS.objects.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class GestionCommentaire {
    @Autowired
    private DatabaseConnection databaseConnection;

    @GetMapping("/getID")
    public Comment getId(){
        Comment c = new Comment();
        
        c.setRequester(11);
        c.setComment("Done correctly");
        c.setMission(11);
        return c;
    }
    
    @PostMapping("/addComment")
    public String addComment(@RequestBody Comment comment) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseConnection.getConnection();

            String query = "INSERT INTO Comment (id_requester, id_mission, comment) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
                
            // Set values for the prepared statement
            preparedStatement.setInt(1, comment.getRequester());
            preparedStatement.setInt(2, comment.getMission());
            preparedStatement.setString(3, comment.getComment());

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return "Comment added successfully";
            } else {
                return "Failed to add comment";
            }
        } finally {
            // Close the connection in a finally block to ensure it's always closed
            if (connection != null) {
                connection.close();
            }
        }
    }

    @DeleteMapping("/deleteComment/{commentId}")
    public String deleteComment(@PathVariable int commentId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseConnection.getConnection();

            String query = "DELETE FROM Comment WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);

            // Set values for the prepared statement
            preparedStatement.setInt(1, commentId);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return "Comment deleted successfully";
            } else {
                return "Failed to delete comment";
            }
        } finally {
            // Close the connection in a finally block to ensure it's always closed
            if (connection != null) {
                connection.close();
            }
        }
    }

    @GetMapping("/getCommentsByMission/{missionId}")
    public List<Comment> getCommentsByMission(@PathVariable int missionId) throws SQLException {
        Connection connection = null;
        List<Comment> comments = new ArrayList<>();

        try {
            connection = databaseConnection.getConnection();

            String query = "SELECT * FROM Comment WHERE id_mission = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, missionId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Comment comment = new Comment();
                        // Set comment attributes from the ResultSet
                        comment.setId(resultSet.getInt("id"));
                        comment.setRequester(resultSet.getInt("id_requester"));
                        comment.setMission(resultSet.getInt("id_mission"));
                        comment.setComment(resultSet.getString("comment"));
                        comments.add(comment);
                    }
                }
            }
        } finally {
            // Close the connection in a finally block to ensure it's always closed
            if (connection != null) {
                connection.close();
            }
        }

        return comments;
    }
    

}
