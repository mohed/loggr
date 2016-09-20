package com.academicwork;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-09-16.
 */
public class Logger {

    List<String> loggPosts;
    Connection conn;

    public Logger() throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlserver://127.0.0.1:1433;Database=Logger;integratedSecurity=true",
                "Administrator",
                "Moh43hai");
    }

    public void logPost(String username, String input) {

        try {

            String sql = "INSERT INTO dbo.logg(UserId, Body)            " +
                    " VALUES(                                           " +
                    " (select userid from dbo.users                     " +
                    " where username = ?),                              " +
                    " ?);                                               ";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, input);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Fail!");
            System.out.println(e.toString());
        }
    }

    public List<String> getAllFromDB(String username) {

        try {
            List<String> returnList = new ArrayList<>();
            PreparedStatement p = conn.prepareStatement("Select Body    " +
                    " From dbo.logg                                     " +
                    " join dbo.users                                    " +
                    " on dbo.logg.userid = dbo.users.userid             " +
                    " where username = ?;                               ");
            p.setString(1, username);
            ResultSet rs = p.executeQuery();

            while (rs.next()) {
                returnList.add(rs.getString("Body"));
            }
            return returnList;
        } catch (SQLException e) {
            return null;
        }
    }

    public void addUser(String name) {
        try {
            String sql = "INSERT INTO dbo.users" +
                    " (UserName)" +
                    " VALUES( ? )";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Fail!");
        }
    }

    public List<String> getAllUsers() {
        try {
            List<String> listOfUsers = new ArrayList<>();
            String sql = "SELECT UserName" +
                    " FROM dbo.users";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                listOfUsers.add(rs.getString("UserName"));
            }
            return listOfUsers;

        } catch (SQLException e) {
            System.out.println("Fail!");
            return null;
        }

    }
}