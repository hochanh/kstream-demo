package service;

import lombok.extern.java.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Log
public class UserDBService {


    private static Connection getJDBCConnection() {
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/events", "root", "root");
            if (conn == null) {
                log.info("Failed to make connection!");
            }
        } catch (SQLException e) {
            log.info("MySQL Connection Failed!");
            e.printStackTrace();
            return null;
        }

        return conn;
    }

    public static void addDataToDB(String userId, long count) {
        PreparedStatement prepareStat;
        Connection conn = getJDBCConnection();

        try {
            String insertQueryStatement = "INSERT  INTO  users  VALUES  (?,?)";

            prepareStat = conn.prepareStatement(insertQueryStatement);
            prepareStat.setString(1, userId);
            prepareStat.setLong(2, count);
            prepareStat.executeUpdate();

            log.info("Insert successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
