package tel_ran.tests.services;
import javax.persistence.*;

import java.util.List;

public class EntityPerson {
    
    private int person_id;
    private String f_name;
    private String l_name;

    @ManyToOne(targetEntity=EntityCompanyTwo.class)
    Company company;
    @OneToMany (mappedBy = "person_data")
    List<EntityTestCommon> test_details;

    public EntityPerson() {
    }

    public EntityPerson(List<EntityTestCommon> test_details, int person_id, String f_name, String l_name, Company company) {
        this.test_details = test_details;
        this.person_id = person_id;
        this.f_name = f_name;
        this.l_name = l_name;
        this.company = company;
    }

    @Override
    public String toString() {
    	StringBuffer strbuf = new StringBuffer ();
    	strbuf.append(f_name);
    	strbuf.append(CommonData.delimiter);
    	strbuf.append(l_name);
        return strbuf.toString();
    }
    
    @Id
    @GeneratedValue
	public int getPerson_id() {
		return person_id;
	}

	public void setPerson_id(int person_id) {
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<EntityTestCommon> getTest_details() {
		return test_details;
	}

	public void setTest_details(List<EntityTestCommon> test_details) {
		this.test_details = test_details;
	}



}
