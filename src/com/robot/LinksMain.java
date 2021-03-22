package com.robot;

import java.sql.*;

public class LinksMain {
    static Connection con;

    static void startFillData(String kpId) {
        long currTime = System.currentTimeMillis();
        int count = 0;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@10.0.11.12:1521:jupiter", "jupiter", "jupiter");
            try {
                Statement stmt = con.createStatement();
                String sql =
                        "SELECT * FROM LINKSBK";
                ResultSet result = stmt.executeQuery(sql);
                while (result.next()) {
                    if (result.getObject("PARENTID") == null) continue;
                    String itemid = result.getString("ITEMID");
                    String parentid = result.getString("PARENTID");
                    String fullPath = result.getString("FULL_PATH");
                    String fullParentType = result.getString("PARENT_TYPE");
                    String fullPathIds = result.getString("PATH");
                    String rootParent = result.getString("ROOT_ID");
                    System.out.println(kpId); //1
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Count " + count);
        long currentTime = System.currentTimeMillis();
        double elapsedTime = (currentTime - currTime) / 1000.0;
        System.out.println("Time Native ***************** :" + elapsedTime);
    }
}
