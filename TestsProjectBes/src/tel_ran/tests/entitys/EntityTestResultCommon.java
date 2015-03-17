package tel_ran.tests.entitys;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.json.JSONException;
import org.json.JSONObject;

@Entity
@Table(name="TEST_RESULTS")
public class EntityTestResultCommon {
	@Id
	@GeneratedValue
	private int testID;
	private String testCategory = "";
	private String testName = "";
	private Date testDate;
	@Embedded
	private EntityTestResultDetails entityTestResultDetails;
	@ManyToOne
	private EntityPerson entityPerson;
	@ManyToOne
	private EntityCompany company;

	public EntityTestResultCommon() {
		
	}
	

	public JSONObject fillJsonObject(JSONObject jsonObj) {
		try {
			jsonObj.put("testid",testID);
			jsonObj.put("testCategory",testCategory);
			jsonObj.put("testName", testName);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			jsonObj.put("testDate", df.format(testDate));
		} catch (JSONException e) {}
		return jsonObj;
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
	public EntityTestResultDetails getEntityTestResultDetails() {
	    return entityTestResultDetails;
	}
	public void setEntityTestResultDetails(EntityTestResultDetails param) {
	    this.entityTestResultDetails = param;
	}
	public EntityPerson getEntityPerson() {
	    return entityPerson;
	}
	public void setEntityPerson(EntityPerson param) {
	    this.entityPerson = param;
	}
	public EntityCompany getCompany() {
	    return company;
	}
	public void setCompany(EntityCompany param) {
	    this.company = param;
	}
}
