package com.robot;

import com.robot.models.Itemspaths;
import com.robot.models.LinksLabels;

import java.sql.*;
import java.util.Collection;
import java.util.HashMap;

public class LinksLabel {
    static Connection con;

    private static void openConnection() {
        try {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //            con = DriverManager.getConnection("jdbc:oracle:thin:@10.0.10.96:1521:jupiter", "jdcci", "jdcci");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:assetiso", "JDCCI", "JDCCI");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void main(String args[]) {
        long currTime = System.currentTimeMillis();
        int count = 0;
        HashMap<String, LinksLabels> linksLabelsHashMap = new HashMap<>();
        try {
            openConnection();
            try {
                Statement stmt = con.createStatement();
                String sql =
                        "SELECT * FROM LINKSLABEL";
                ResultSet result = stmt.executeQuery(sql);
                while (result.next()) {
                    String itemid = result.getString("ITEMID");
                    linksLabelsHashMap.put(itemid, (new LinksLabels(itemid,
                            result.getString("PARENTID"),
                            result.getLong("POS"),
                            result.getShort("PARENTTYPE"),
                            result.getString("INDEXCLASS"),
                            result.getString("LABEL"))
                    ));
                }
                con.close();
                long currentTimeNow = System.currentTimeMillis();
                double elapsedTimeNow = (currentTimeNow - currTime) / 1000.0;
                System.out.println("Retrieved Time ***************** :" + elapsedTimeNow);
                openConnection();
                Collection<LinksLabels> values = linksLabelsHashMap.values();
                for (LinksLabels value : values) {
                    Itemspaths itemsPathsPojo = new Itemspaths();
                    itemsPathsPojo.setItemid(value.getItemid());
                    itemsPathsPojo.setDirectparentid(value.getParentid());
                    itemsPathsPojo.setDirectparenttype(value.getParenttype());
                    if (linksLabelsHashMap.containsKey(value.getParentid())) {
                        LinksLabels parent = linksLabelsHashMap.get(value.getParentid());
                        itemsPathsPojo.setParentlabel(parent.getLabel());//Parent label
                    }
                    StringBuilder itemFullPathBuilder = new StringBuilder(value.getLabel());
                    StringBuilder itemfullpathidsBuilder = new StringBuilder(value.getItemid());
                    StringBuilder iullparenttypeBuilder = new StringBuilder(value.getParenttype());
                    String rootParent = "";
                    recursionFullParent(itemFullPathBuilder, itemfullpathidsBuilder, iullparenttypeBuilder, rootParent, value.getParentid(), linksLabelsHashMap);
                    itemsPathsPojo.setItemfullpath(itemFullPathBuilder.toString());//full path labels
                    itemsPathsPojo.setItemfullpathids(itemfullpathidsBuilder.toString());//full path ids
                    itemsPathsPojo.setFullparenttype(iullparenttypeBuilder.toString());//full path ids
                    itemsPathsPojo.setRootparent(rootParent);//kp id
                    String SQL_INSERT = "INSERT INTO itemspaths (itemid,directparentid,directparenttype,itemfullpath,parentlabel,itemfullpathids,rootparent,fullparenttype) VALUES (?,?,?,?,?,?,?,?)";
                    PreparedStatement preparedStatement = con.prepareStatement(SQL_INSERT);
                    preparedStatement.setString(1, itemsPathsPojo.getItemid());
                    preparedStatement.setString(2, itemsPathsPojo.getDirectparentid());
                    preparedStatement.setInt(3, itemsPathsPojo.getDirectparenttype());
                    preparedStatement.setString(4, itemsPathsPojo.getItemfullpath());
                    preparedStatement.setString(5, itemsPathsPojo.getParentlabel());
                    preparedStatement.setString(6, itemsPathsPojo.getItemfullpathids());
                    preparedStatement.setString(7, itemsPathsPojo.getRootparent());
                    preparedStatement.setString(8, itemsPathsPojo.getFullparenttype());
                    preparedStatement.executeUpdate();
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
        System.out.println("All Time ***************** :" + elapsedTime);
    }

    private static void recursionFullParent(StringBuilder itemFullPathBuilder, StringBuilder itemfullpathidsBuilder, StringBuilder iullparenttypeBuilder, String rootParent, String parentid, HashMap<String, LinksLabels> linksLabelsHashMap) {
        if (linksLabelsHashMap.containsKey(parentid)) {
            LinksLabels parent = linksLabelsHashMap.get(parentid);
            itemFullPathBuilder.append(parent.getLabel()).append("~");
            itemfullpathidsBuilder.append(parent.getParentid()).append("~");
            iullparenttypeBuilder.append(parent.getItemid()).append("~");
            rootParent = parent.getParentid();
            parentid = parent.getParentid();
            recursionFullParent(itemFullPathBuilder, itemfullpathidsBuilder, iullparenttypeBuilder, rootParent, parentid, linksLabelsHashMap);
        }
    }
}
