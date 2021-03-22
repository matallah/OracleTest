package com.robot;

import java.sql.*;

public class Main2 {
    static Connection con;

    public static void main(String args[]) {
        long currTime = System.currentTimeMillis();
        int count = 0;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

//            con = DriverManager.getConnection("jdbc:oracle:thin:@10.0.10.96:1521:jupiter", "jdcci", "jdcci");
            con = DriverManager.getConnection("jdbc:oracle:thin:@10.0.11.12:1521:jupiter", "jupiter", "jupiter");

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
            try {
                Statement stmt = con.createStatement();
                String sql =
                        "WITH t1(ITEMID, PARENTID, lvl, root_id, path,label,full_path,parent_type) AS (\n" +
                                "  -- Anchor member.\n" +
                                "  SELECT ITEMID,\n" +
                                "         PARENTID,\n" +
                                "         1 AS lvl,\n" +
                                "         ITEMID AS root_id,\n" +
                                "         TO_CHAR(ITEMID) AS path,\n" +
                                "         knowledgepool.name as label,\n" +
                                "         knowledgepool.name as full_path,\n" +
                                "         '1' as parent_type\n" +
                                "  FROM   LINKS\n" +
                                "  LEFT JOIN\n" +
                                "    knowledgepool\n" +
                                "ON\n" +
                                "    knowledgepool.poolid = links.itemid\n" +
                                "  WHERE  PARENTID IS NULL\n" +
                                "  UNION ALL\n" +
                                "  -- Recursive member.\n" +
                                "  SELECT t2.ITEMID,\n" +
                                "         t2.PARENTID,\n" +
                                "         lvl+1,\n" +
                                "         t1.root_id,\n" +
                                "         t1.path || '~' || t2.ITEMID AS path,\n" +
                                "         (SELECT label FROM items WHERE items.ITEMID = t2.ITEMID) AS label,\n" +
                                "         t1.full_path || '~' || (SELECT label FROM items WHERE items.ITEMID = t2.ITEMID) AS full_path,\n" +
                                "         t1.parent_type || '~' || (SELECT items.TYPE FROM items WHERE items.ITEMID = t2.ITEMID) AS parent_type\n" +
                                "         FROM   LINKS t2, t1\n" +
                                "  WHERE  t2.PARENTID = t1.ITEMID\n" +
                                ")\n" +
                                "SEARCH DEPTH FIRST BY ITEMID SET order1\n" +
                                "SELECT ITEMID,\n" +
                                "       PARENTID,\n" +
                                "       RPAD('.', (lvl-1)*2, '.') || ITEMID AS tree,\n" +
                                "       lvl,\n" +
                                "       root_id,\n" +
                                "       path,\n" +
                                "       label,\n" +
                                "       full_path,\n" +
                                "       parent_type\n" +
                                "FROM t1 \n" +
                                "ORDER BY order1";
                ResultSet result = stmt.executeQuery(sql);
                String SQL_INSERT = "INSERT INTO itemspath (itemid,directparentid,directparenttype,itemfullpath,parentlabel) VALUES (?,?,?,?,?)";
                PreparedStatement preparedStatement = con.prepareStatement(SQL_INSERT);
                while (result.next()) {
                    if (result.getObject("PARENTID") == null) continue;
                    String itemid = result.getString("ITEMID");
                    String parentid = result.getString("PARENTID");
                    String fullPath = result.getString("FULL_PATH");
                    String fullParentType = result.getString("PARENT_TYPE");
                    String[] fullPaths = fullPath.split("~");
                    String[] parentTypes = fullParentType.split("~");
                    String parentLabel = fullPaths.length > 1 ? fullPaths[fullPaths.length - 2] : fullPaths[fullPaths.length - 1];
                    String parentType = parentTypes.length > 1 ? parentTypes[parentTypes.length - 2] : parentTypes[parentTypes.length - 1];
                    preparedStatement.setString(1, itemid);
                    preparedStatement.setString(2, parentid);
                    preparedStatement.setInt(3, Integer.parseInt(parentType));
                    preparedStatement.setString(4, fullPath);
                    preparedStatement.setString(5, parentLabel);
                    int row = preparedStatement.executeUpdate();
                    System.out.print(row); //1

                    System.out.print("Done"); //1
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
