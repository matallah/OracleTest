package com.robot;

import com.robot.models.Itemspaths;
import com.robot.models.LinksLabels;

import java.sql.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
//            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:assetiso", "JDCCI", "JDCCI");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "jupiter", "jupiter");
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
                con.prepareStatement("DELETE FROM ITEMSPATHS").executeUpdate();
                ResultSet resultSet = con.prepareStatement("SELECT KNOWLEDGEPOOL.POOLID,KNOWLEDGEPOOL.NAME FROM KNOWLEDGEPOOL").executeQuery();
                while (resultSet.next()) {
                    String itemid = resultSet.getString("POOLID");
                    linksLabelsHashMap.put(itemid, (new LinksLabels(itemid,
                            "",
                            0,
                            0,
                            "",
                            resultSet.getString("NAME"),
                            1)
                    ));
                }
                Statement stmt = con.createStatement();
                String sql = "SELECT * FROM LINKSLABELVIEW";
                ResultSet result = stmt.executeQuery(sql);
                while (result.next()) {
                    String itemid = result.getString("ITEMID");
                    linksLabelsHashMap.put(itemid, (new LinksLabels(itemid,
                            result.getString("PARENTID"),
                            result.getInt("POS"),
                            result.getShort("PARENTTYPE"),
                            result.getString("INDEXCLASS"),
                            result.getString("LABEL"),
                            result.getInt("TYPE"))
                    ));
                }
                con.close();
                long currentTimeNow = System.currentTimeMillis();
                double elapsedTimeNow = (currentTimeNow - currTime) / 1000.0;
                System.out.println("Retrieved Time ***************** :" + elapsedTimeNow);
                openConnection();
                Collection<LinksLabels> values = linksLabelsHashMap.values();
                for (LinksLabels value : values) {
                    if (value.getParenttype() == 0) continue;
                    Itemspaths itemsPathsPojo = new Itemspaths();
                    itemsPathsPojo.setItemid(value.getItemid());
                    itemsPathsPojo.setDirectparentid(value.getParentid());
                    itemsPathsPojo.setDirectparenttype(value.getParenttype());
                    if (linksLabelsHashMap.containsKey(value.getParentid())) {
                        LinksLabels parent = linksLabelsHashMap.get(value.getParentid());
                        itemsPathsPojo.setParentlabel(parent.getLabel());//Parent label
                    }
                    StringBuilder itemFullPathBuilder = new StringBuilder(value.getLabel() + "~");
                    StringBuilder itemfullpathidsBuilder = new StringBuilder(value.getItemid() + "~");
                    StringBuilder iullparenttypeBuilder = new StringBuilder(value.getType() + "~");
                    List<String> rootParent = new LinkedList<>();
                    recursionFullParent(itemFullPathBuilder, itemfullpathidsBuilder, iullparenttypeBuilder, rootParent, value.getParentid(), linksLabelsHashMap);
                    itemsPathsPojo.setItemfullpath(itemFullPathBuilder.toString());//full path labels
                    itemsPathsPojo.setItemfullpathids(itemfullpathidsBuilder.toString());//full path ids
                    itemsPathsPojo.setFullparenttype(iullparenttypeBuilder.toString());//full path ids
                    try {
                        if (rootParent.size() - 1 >= rootParent.size())
                            itemsPathsPojo.setRootparent(rootParent.get(rootParent.size() - 1));//kp id
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println(value.getItemid());
                        throw e;
                    }
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

    private static void recursionFullParent(StringBuilder itemFullPathBuilder, StringBuilder itemfullpathidsBuilder, StringBuilder iullparenttypeBuilder, List<String> rootParent, String parentid, HashMap<String, LinksLabels> linksLabelsHashMap) {
        if (linksLabelsHashMap.containsKey(parentid)) {
            LinksLabels parent = linksLabelsHashMap.get(parentid);
            itemFullPathBuilder.append(parent.getLabel()).append("~");
            itemfullpathidsBuilder.append(parent.getItemid()).append("~");
            iullparenttypeBuilder.append(parent.getType() != 0 ? parent.getType() : 1).append("~");
            if (parent.getParentid() != null)
                rootParent.add(parent.getParentid());
            else
                rootParent.add(parent.getItemid());
            parentid = parent.getParentid();
            recursionFullParent(itemFullPathBuilder, itemfullpathidsBuilder, iullparenttypeBuilder, rootParent, parentid, linksLabelsHashMap);
        }
    }

    void createView(){
        String SQL = "CREATE VIEW LINKSLABELVIEW\n" +
                "AS SELECT\n" +
                "    links.itemid,\n" +
                "    links.parentid,\n" +
                "    links.pos,\n" +
                "    links.parenttype,\n" +
                "    links.indexclass,\n" +
                "    items.label\n" +
                "     ||  knowledgepool.name label,\n" +
                "    items.type,\n" +
                "    ROWNUM id\n" +
                "FROM\n" +
                "    links\n" +
                "LEFT JOIN\n" +
                "    items\n" +
                "ON\n" +
                "    links.itemid = items.itemid\n" +
                "LEFT JOIN\n" +
                "    knowledgepool\n" +
                "ON\n" +
                "    links.itemid = knowledgepool.poolid\n" +
                "ORDER BY\n" +
                "    items.type,\n" +
                "    links.itemid;";
    }
}
