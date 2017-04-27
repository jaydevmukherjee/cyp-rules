package org.copart.cyp.assignment.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaydev on 4/27/2017.
 */

@XmlRootElement(name = "field-info")
@XmlType(propOrder = {"fieldName", "condition", "requiredCondition", "readableCondition"})
public class fieldsPOJO {

    String fieldName = "";
    String condition = "";
    String requiredCondition = "";
    String readableCondition = "";

    public fieldsPOJO() {

    }

    /**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	@XmlElement(name = "condition")
	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * @return the requiredCondition
	 */
	public String getRequiredCondition() {
		return requiredCondition;
	}

	/**
	 * @param requiredCondition the requiredCondition to set
	 */
	@XmlElement(name = "requiredCondition")
	public void setRequiredCondition(String requiredCondition) {
		this.requiredCondition = requiredCondition;
	}

	/**
	 * @return the readableCondition
	 */
	public String getReadableCondition() {
		return readableCondition;
	}

	/**
	 * @param readableCondition the readableCondition to set
	 */
	@XmlElement(name = "readableCondition")
	public void setReadableCondition(String readableCondition) {
		this.readableCondition = readableCondition;
	}

	public String getFieldName() {
        return fieldName;
    }

    @XmlElement(name = "field-name")
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
