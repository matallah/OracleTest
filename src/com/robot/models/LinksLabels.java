package com.robot.models;

public class LinksLabels {

    private static final long serialVersionUID = 1L;
    private String itemid;
    private String parentid;
    private Long pos;
    private Short parenttype;
    private String indexclass;
    private String label;

    public LinksLabels() {
    }

    public LinksLabels(String itemid, String parentid, Long pos, Short parenttype, String indexclass, String label) {
        this.itemid = itemid;
        this.parentid = parentid;
        this.pos = pos;
        this.parenttype = parenttype;
        this.indexclass = indexclass;
        this.label = label;
    }

    public LinksLabels(String itemid) {
        this.itemid = itemid;
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

    public Long getPos() {
        return pos;
    }

    public void setPos(Long pos) {
        this.pos = pos;
    }

    public Short getParenttype() {
        return parenttype;
    }

    public void setParenttype(Short parenttype) {
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
