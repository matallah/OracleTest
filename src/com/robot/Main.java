package com.robot;

import java.sql.*;

public class Main {


    public static boolean checkAccessRights(int accessRights, int access) {
        if ((accessRights & (1 << Defines.ITM_ACS_OWNER)) != 0)
            return true;
        if ((accessRights & (1 << Defines.ITM_ACS_CHANGE_ACCESS)) != 0)
            return true;
        if ((accessRights & (1 << Defines.ITM_ACS_NO_ACCESS)) != 0)
            return false;
        if ((accessRights & (1 << access)) != 0)
            return true;
        return false;
    }

    static Connection con;

    public static void main(String args[]) {
        long currTime = System.currentTimeMillis();
        int count = 0;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            con = DriverManager.getConnection("jdbc:oracle:thin:@10.0.10.96:1521:jupiter", "jdcci", "jdcci");
//            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:asset", "jupiter", "jupiter");
            /*
            String SQL_INSERT = "Insert into ACCESSRULES (ITEMID,UGID,ITEMTYPE,UGTYPE,ACCESSRIGHT) values ('UG2416',?,3,2,32)";
            PreparedStatement preparedStatement = con.prepareStatement(SQL_INSERT);
            for (int i = 0; i < 1000000; i++) {
                preparedStatement.setInt(1, i);
                int row = preparedStatement.executeUpdate();
                System.out.print(i+"***"); //1
            }
            System.out.print("Done"); //1
            */
            getParent("UG68840");

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Count " + count);
        long currentTime = System.currentTimeMillis();
        double elapsedTime = (currentTime - currTime) / 1000.0;
        System.out.println("Time Native ***************** :" + elapsedTime);
    }

    private static void getParent(String parentId) {
        String val = null;
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM links WHERE ITEMID ='" + parentId + "'");
            while (result.next()) {
                val = result.getString("PARENTID");
                if (val == null) break;
                System.out.println(val);
                getParent(val);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
