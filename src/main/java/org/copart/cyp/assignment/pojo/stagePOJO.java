package org.copart.cyp.assignment.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Jaydev on 4/27/2017.
 */

@XmlRootElement(name = "stage")
@XmlType(propOrder = {"stage", "required","viewable", "editable", "color"})
public class stagePOJO {

    String stage = "";
    boolean viewable = false;
    boolean editable = false;
    String color = "";

    public stagePOJO(){

    }

    public String getColor() {
        return color;
    }

    @XmlElement(name = "color")
    public void setColor(String color) {
        this.color = color;
    }

    public String getStage() {
        return stage;
    }

    @XmlElement(name = "stage-name")
    public void setStage(String stage) {
        this.stage = stage;
    }

    public boolean isViewable() {
        return viewable;
    }

    @XmlElement(name = "viewable")
    public void setViewable(boolean viewable) {
        this.viewable = viewable;
    }

    public boolean isEditable() {
        return editable;
    }

    @XmlElement(name = "editable")
    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
