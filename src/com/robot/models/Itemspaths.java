package com.robot.models;

import java.io.Serializable;
import java.math.BigInteger;


public class Itemspaths implements Serializable {

    private static final long serialVersionUID = 1L;
    private String itemid;
    private String directparentid;
    private int directparenttype;
    private String itemfullpath;
    private String parentlabel;
    private String itemfullpathids;
    private String rootparent;
    private String fullparenttype;

    public Itemspaths() {
    }

    public Itemspaths(String itemid) {
        this.itemid = itemid;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getDirectparentid() {
        return directparentid;
    }

    public void setDirectparentid(String directparentid) {
        this.directparentid = directparentid;
    }

    public int getDirectparenttype() {
        return directparenttype;
    }

    public void setDirectparenttype(int directparenttype) {
        this.directparenttype = directparenttype;
    }

    public String getItemfullpath() {
        return itemfullpath;
    }

    public void setItemfullpath(String itemfullpath) {
        this.itemfullpath = itemfullpath;
    }

    public String getParentlabel() {
        return parentlabel;
    }

    public void setParentlabel(String parentlabel) {
        this.parentlabel = parentlabel;
    }

    public String getItemfullpathids() {
        return itemfullpathids;
    }

    public void setItemfullpathids(String itemfullpathids) {
        this.itemfullpathids = itemfullpathids;
    }

    public String getRootparent() {
        return rootparent;
    }

    public void setRootparent(String rootparent) {
        this.rootparent = rootparent;
    }

    public String getFullparenttype() {
        return fullparenttype;
    }

    public void setFullparenttype(String fullparenttype) {
        this.fullparenttype = fullparenttype;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemid != null ? itemid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Itemspaths)) {
            return false;
        }
        Itemspaths other = (Itemspaths) object;
        if ((this.itemid == null && other.itemid != null) || (this.itemid != null && !this.itemid.equals(other.itemid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.en.Itemspath[ itemid=" + itemid + " ]";
    }
}
