package org.copart.cyp.assignment.pojo;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jaydev on 4/27/2017.
 */

@XmlRootElement(name = "page")
@XmlType(propOrder = {"name", "list"})
public class rootPOJO {

    String name = "";
    List<fieldsPOJO> list = null;

    public rootPOJO() {

    }

    public rootPOJO(String name) {
        this.name = name;
        list = new LinkedList<>();
    }

    public rootPOJO(String name, List<fieldsPOJO> list) {
        this.name = name;
        this.list = list;
    }

    public String getName() {
        return name;
    }

    @XmlElement(name = "page-name")
    public void setName(String name) {
        this.name = name;
    }

    public List<fieldsPOJO> getList() {
        return list;
    }

    @XmlElement(name = "field")
    @XmlElementWrapper(name = "fields")
    public void setList(List<fieldsPOJO> list) {
        this.list = list;
    }
}
