package fr.insa.sdbd.UserGestionMS.msRessources;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.sdbd.UserGestionMS.db.DatabaseConnection;
import fr.insa.sdbd.UserGestionMS.objects.User;

@RestController
public class GestionUser {

    @Autowired
    private DatabaseConnection databaseConnection;

    @GetMapping("/user/{id}")
    public User getUserByID(@PathVariable int id) throws SQLException {
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM user WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    User u = new User();
                    u.setId(resultSet.getInt("id"));
                    u.setName(resultSet.getString("name"));
                    u.setSurname(resultSet.getString("surname"));
                    u.setStatus(resultSet.getString("status"));
                    u.setEmail(resultSet.getString("email"));
                    u.setPassword(resultSet.getString("password"));
                    u.setAge(resultSet.getInt("age"));

                    return u;
                } else {
                    return null;
                }
            }
        }
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody User u) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        String query = "INSERT INTO user (name, surname, status, email, password, age) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, u.getName());
            preparedStatement.setString(2, u.getSurname());
            preparedStatement.setString(3, u.getStatus());
            preparedStatement.setString(4, u.getEmail());
            preparedStatement.setString(5, u.getPassword());
            preparedStatement.setInt(6, u.getAge());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return "User added successfully";
            } else {
                return "Failed to add user";
            }
        }
    }

    @DeleteMapping("/delUser/{id}")
    public void delUser(@PathVariable int id) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        String query = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User deleted successfully");
            } else {
                System.out.println("User not found or failed to delete");
            }
        }
    }

    @PutMapping("/updateUser")
    public void updateUser(@RequestBody User u) throws SQLException {
        Connection connection = databaseConnection.getConnection();

        String query = "UPDATE user SET name=?, surname=?, status=?, email=?, password=?, age=? WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, u.getName());
            preparedStatement.setString(2, u.getSurname());
            preparedStatement.setString(3, u.getStatus());
            preparedStatement.setString(4, u.getEmail());
            preparedStatement.setString(5, u.getPassword());
            preparedStatement.setInt(6, u.getAge());
            preparedStatement.setInt(7, u.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User updated successfully");
            } else {
                System.out.println("User not found or failed to update");
            }
        }
    }
}
