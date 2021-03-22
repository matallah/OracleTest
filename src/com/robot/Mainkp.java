package com.robot;

import java.sql.*;
import java.util.*;

public class Mainkp {
    static Connection con;

    public static List<String> findAllKps() {
        long currTime = System.currentTimeMillis();
        int count = 0;
        ArrayList<String> kps = new ArrayList<>();
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            con = DriverManager.getConnection("jdbc:oracle:thin:@10.0.11.12:1521:jupiter", "jupiter", "jupiter");
            try {
                Statement stmt = con.createStatement();
                String sql =
                        "SELECT ITEMID FROM LINKS WHERE PARENTID IS NULL";
                ResultSet result = stmt.executeQuery(sql);
                while (result.next()) {
                    String itemid = result.getString("ITEMID");
                    kps.add(itemid);
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
        return kps;
    }

    public static void main(String[] args) {
        List<String> allKps = findAllKps();
        List<Integer> allKpsNew = new ArrayList<>();
        Iterator<String> iterator = allKps.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            String ug = next.replace("UG", "");
            allKpsNew.add(Integer.parseInt(ug));
        }
        Collections.sort(allKpsNew);
        for (Integer allKp : allKpsNew) {
            System.out.println("UG"+allKp);
        }
    }
}
