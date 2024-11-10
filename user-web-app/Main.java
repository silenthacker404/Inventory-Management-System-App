mvn archetype:generate -DgroupId=com.example.inventory -DartifactId=InventoryManagementSystem -DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false
<dependencies>
    <!-- MySQL Connector -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.32</version>
    </dependency>

    <!-- Servlet API -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>4.0.1</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
package com.example.inventory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtils {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "yourpassword";

    public static Connection getConnection(String dbName) throws SQLException {
        return DriverManager.getConnection(URL + dbName, USER, PASSWORD);
    }

    public static void createDatabase(String dbName) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
        }
    }

    public static void initializeUserTables(String dbName) throws SQLException {
        try (Connection conn = getConnection(dbName); 
             Statement stmt = conn.createStatement()) {
            String createProductTable = "CREATE TABLE IF NOT EXISTS Products (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(255), " +
                    "quantity INT, " +
                    "price DECIMAL(10, 2))";
            stmt.executeUpdate(createProductTable);
        }
    }
}
package com.example.inventory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/registerUser")
public class RegisterUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");

        String dbName = "user_" + username;
        
        try {
            DatabaseUtils.createDatabase(dbName);
            DatabaseUtils.initializeUserTables(dbName);
            response.getWriter().println("Database and tables created for user: " + username);
        } catch (SQLException e) {
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}