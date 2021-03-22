package com.robot;

import java.sql.*;

public class Main3 {
    static Connection con;

    public static void main(String args[]) {
        long currTime = System.currentTimeMillis();
        int count = 0;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            con = DriverManager.getConnection("jdbc:oracle:thin:@10.0.11.12:1521:jupiter", "jupiter", "jupiter");
            try {
                Statement stmt = con.createStatement();
                String sql =
                        "select * from KNOWLEDGEPOOL";
                ResultSet result = stmt.executeQuery(sql);
                while (result.next()) {
                    String poolid = result.getString("POOLID");
                    String SQL_INSERT = "INSERT INTO links (itemid,parentid,pos,parenttype,indexclass) VALUES (?,?,?,?,?)";
                    PreparedStatement preparedStatement = con.prepareStatement(SQL_INSERT);
                        preparedStatement.setString(1, poolid);
                        preparedStatement.setString(2, null);
                        preparedStatement.setInt(3, 0);
                        preparedStatement.setInt(4, 0);
                        preparedStatement.setString(5, "0");
                    int row = preparedStatement.executeUpdate();
                        System.out.print(row+"***"); //1

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Count " + count);
        long currentTime = System.currentTimeMillis();
        double elapsedTime = (currentTime - currTime) / 1000.0;
        System.out.println("Time Native ***************** :" + elapsedTime);
    }

}
