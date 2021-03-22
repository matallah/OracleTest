package com.robot;

import java.sql.*;

public class RunnableDemo implements Runnable {
    
    private Thread t;
    private String kpId;
    static Connection con;

    RunnableDemo(String kpId) {
        this.kpId = kpId;
        System.out.println("Creating KP thread"+ kpId);
    }

    public void run() {
        try {
            System.out.println("Running " + kpId);
            startFillData(kpId);
                // Let the thread sleep for a while.
                Thread.sleep(50);

        } catch (InterruptedException e) {
            System.out.println("Thread " + kpId + " interrupted.");
        }
        System.out.println("Thread " + kpId + " exiting.");
    }

    public void start() {
        System.out.println("Starting " + kpId);
        if (t == null) {
            t = new Thread(this, kpId);
            t.start();
        }
    }

   static void startFillData(String kpId){
        long currTime = System.currentTimeMillis();
        int count = 0;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@10.0.11.12:1521:jupiter", "jupiter", "jupiter");
            try {
                Statement stmt = con.createStatement();
                String sql =
                        "WITH T1(ITEMID, PARENTID, LVL, ROOT_ID, PATH, LABEL, FULL_PATH, PARENT_TYPE) AS (\n" +
                                "    -- ANCHOR MEMBER.\n" +
                                "    SELECT ITEMID,\n" +
                                "           PARENTID,\n" +
                                "           1                  AS LVL,\n" +
                                "           ITEMID             AS ROOT_ID,\n" +
                                "           TO_CHAR(ITEMID)    AS PATH,\n" +
                                "           KNOWLEDGEPOOL.NAME AS LABEL,\n" +
                                "           KNOWLEDGEPOOL.NAME AS FULL_PATH,\n" +
                                "           '1'                AS PARENT_TYPE\n" +
                                "    FROM LINKS\n" +
                                "             LEFT JOIN\n" +
                                "         KNOWLEDGEPOOL\n" +
                                "         ON\n" +
                                "             KNOWLEDGEPOOL.POOLID = LINKS.ITEMID\n" +
                                "    WHERE PARENTID IS NULL\n" +
                                "      AND ITEMID = '"+kpId+"'\n" +
                                "    UNION ALL\n" +
                                "    -- RECURSIVE MEMBER.\n" +
                                "    SELECT T2.ITEMID,\n" +
                                "           T2.PARENTID,\n" +
                                "           LVL + 1,\n" +
                                "           T1.ROOT_ID,\n" +
                                "           T1.PATH || '~' || T2.ITEMID                                                            AS PATH,\n" +
                                "           (SELECT LABEL FROM ITEMS WHERE ITEMS.ITEMID = T2.ITEMID)                               AS LABEL,\n" +
                                "           T1.FULL_PATH || '~' || (SELECT LABEL FROM ITEMS WHERE ITEMS.ITEMID = T2.ITEMID)        AS FULL_PATH,\n" +
                                "           T1.PARENT_TYPE || '~' || (SELECT ITEMS.TYPE FROM ITEMS WHERE ITEMS.ITEMID = T2.ITEMID) AS PARENT_TYPE\n" +
                                "    FROM LINKS T2,\n" +
                                "         T1\n" +
                                "    WHERE T2.PARENTID = T1.ITEMID\n" +
                                ")\n" +
                                "         SEARCH DEPTH FIRST BY PARENTID SET ORDER1\n" +
                                "SELECT ITEMID,\n" +
                                "       PARENTID,\n" +
                                "       RPAD('.', (LVL - 1) * 2, '.') || ITEMID AS TREE,\n" +
                                "       LVL,\n" +
                                "       ROOT_ID,\n" +
                                "       PATH,\n" +
                                "       LABEL,\n" +
                                "       FULL_PATH,\n" +
                                "       PARENT_TYPE\n" +
                                "FROM T1\n" +
                                "ORDER BY ORDER1";
                ResultSet result = stmt.executeQuery(sql);
                String SQL_INSERT = "INSERT INTO itemspaths (itemid,directparentid,directparenttype,itemfullpath,parentlabel,ITEMFULLPATHIDS,ROOTPARENT,fullParentType) VALUES (?,?,?,?,?,?,?,?)";
                PreparedStatement preparedStatement = con.prepareStatement(SQL_INSERT);
                while (result.next()) {
                    if (result.getObject("PARENTID") == null) continue;
                    String itemid = result.getString("ITEMID");
                    String parentid = result.getString("PARENTID");
                    String fullPath = result.getString("FULL_PATH");
                    String fullParentType = result.getString("PARENT_TYPE");
                    String fullPathIds = result.getString("PATH");
                    String rootParent = result.getString("ROOT_ID");
                    String[] fullPaths = fullPath.split("~");
                    String[] parentTypes = fullParentType.split("~");
                    String parentLabel = fullPaths.length > 1 ? fullPaths[fullPaths.length - 2] : fullPaths[fullPaths.length - 1];
                    String parentType = parentTypes.length > 1 ? parentTypes[parentTypes.length - 2] : parentTypes[parentTypes.length - 1];
                    preparedStatement.setString(1, itemid);
                    preparedStatement.setString(2, parentid);
                    preparedStatement.setInt(3, Integer.parseInt(parentType));
                    preparedStatement.setString(4, fullPath);
                    preparedStatement.setString(5, parentLabel);
                    preparedStatement.setString(6, fullPathIds);
                    preparedStatement.setString(7, rootParent);
                    preparedStatement.setString(8, fullParentType);
                    int row = preparedStatement.executeUpdate();
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
