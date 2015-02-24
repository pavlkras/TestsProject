package tel_ran.tests.services;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONException;
import org.json.JSONObject;

import tel_ran.tests.services.common.CommonData;
@Entity
@Table(name="PERSON")
public class EntityPerson {
    @Id
    private String person_id;
    @Column(name="firstName")
    private String f_name = "";
    @Column(name="lastName")
    private String l_name = "";
	
	public EntityPerson() {
    }


	public void fillJsonObject(JSONObject jsonObj) {
		try {
			jsonObj.put("firstName",f_name);
			jsonObj.put("lastName",l_name);
		} catch (JSONException e) {}		
	}
	
    @Override
    public String toString() {
    	StringBuffer strbuf = new StringBuffer ();
    	strbuf.append(f_name);
    	strbuf.append(CommonData.delimiter);
    	strbuf.append(l_name);
        return strbuf.toString();
    }

	public String getPerson_id() {
		return person_id;
	}

	public void setPerson_id(String person_id) {
		this.person_id = person_id;
	}

	public String getF_name() {
		return f_name;
	}

	public void setF_name(String f_name) {
		this.f_name = f_name;
	}

	public String getL_name() {
		return l_name;
	}

	public void setL_name(String l_name) {
		this.l_name = l_name;
	}
}