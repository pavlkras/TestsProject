package tel_ran.tests.services;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import tel_ran.tests.services.EntityTestDetails;

import javax.persistence.Embedded;

import tel_ran.tests.services.EntityPerson;
import tel_ran.tests.services.common.CommonData;

import javax.persistence.ManyToOne;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
@Entity
@Table(name="TEST_COMMON")
public class EntityTestCommon {
	@Id
	@GeneratedValue
	private int testID;
	private String testCategory = "";
	private String testName = "";
	private Date testDate;
	
	@Embedded
	private EntityTestDetails entityTestDetails;
	@ManyToOne
	@JoinColumn(name="personID")
	private EntityPerson entityPerson;
	
	public EntityTestCommon() {
		
	}
	public int getTestID() {
		return testID;
	}
	public void setTestID(int testID) {
		this.testID = testID;
	}
	public String getTestCategory() {
		return testCategory;
	}
	public void setTestCategory(String testCategory) {
		this.testCategory = testCategory;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public Date getTestDate() {
		return testDate;
	}
	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}
	
	@Override
	public String toString() {
		StringBuffer strbuf = new StringBuffer();
		strbuf.append(testID);
		strbuf.append(CommonData.delimiter);
		strbuf.append(testCategory);
		strbuf.append(CommonData.delimiter);
		strbuf.append(testName);
		strbuf.append(CommonData.delimiter);
		strbuf.append(testDate);
		strbuf.append(CommonData.delimiter);
		strbuf.append(entityPerson.toString());
		strbuf.append(CommonData.delimiter);
		strbuf.append(entityTestDetails);
		return strbuf.toString();
	}
	public JSONObject toJson()
	{
		JSONObject json = new JSONObject();
		json.put("testid",testID);
		json.put("testCategory",testCategory);
		json.put("testName", testName);
		json.put("testDate", testDate);
		json.put("entityPerson", entityPerson);
		json.put("entityTestDetails", entityTestDetails);
		System.out.println(json);
		return json;
	}
	public EntityTestDetails getEntityTestDetails() {
	    return entityTestDetails;
	}
	public void setEntityTestDetails(EntityTestDetails param) {
	    this.entityTestDetails = param;
	}
	public EntityPerson getEntityPerson() {
	    return entityPerson;
	}
	public void setEntityPerson(EntityPerson param) {
	    this.entityPerson = param;
	}

}
