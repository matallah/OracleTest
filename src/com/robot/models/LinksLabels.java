package com.robot.models;

public class LinksLabels {

    private static final long serialVersionUID = 1L;
    private String itemid;
    private String parentid;
    private int pos;
    private int parenttype;
    private String indexclass;
    private String label;
    private int type;

    public LinksLabels() {
    }

    public LinksLabels(String itemid, String parentid, int pos, int parenttype, String indexclass, String label, int type) {
        this.itemid = itemid;
        this.parentid = parentid;
        this.pos = pos;
        this.parenttype = parenttype;
        this.indexclass = indexclass;
        this.label = label;
        this.type = type;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getParenttype() {
        return parenttype;
    }

    public void setParenttype(int parenttype) {
        this.parenttype = parenttype;
    }

    public String getIndexclass() {
        return indexclass;
    }

    public void setIndexclass(String indexclass) {
        this.indexclass = indexclass;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
        if (!(object instanceof LinksLabels)) {
            return false;
        }
        LinksLabels other = (LinksLabels) object;
        if ((this.itemid == null && other.itemid != null) || (this.itemid != null && !this.itemid.equals(other.itemid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.en.LinksLabels[ itemid=" + itemid + " ]";
    }
}
