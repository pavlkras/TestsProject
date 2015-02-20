package tel_ran.tests.services;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;


import tel_ran.tests.services.common.CommonData;
import tel_ran.tests.services.Company;

import javax.persistence.ManyToOne;

import tel_ran.tests.services.EntityTestResultCommon;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OneToMany;
@Entity
@Table(name="PERSON")
public class EntityPerson {
    @Id
    @GeneratedValue
    private int person_id;
    @Column(name="firstName")
    private String f_name = "";
    @Column(name="lastName")
    private String l_name = "";
	@ManyToOne
	@JoinColumn(name="CompanyName")
	private Company company;
	@OneToMany(targetEntity = EntityTestResultCommon.class, mappedBy = "entityPerson", cascade = CascadeType.ALL)
	private List<EntityTestResultCommon> entityTestResultCommon = new ArrayList<EntityTestResultCommon>();
	public EntityPerson() {
    }

    @Override
    public String toString() {
    	StringBuffer strbuf = new StringBuffer ();
    	strbuf.append(f_name);
    	strbuf.append(CommonData.delimiter);
    	strbuf.append(l_name);
        return strbuf.toString();
    }

	public Company getCompany() {
	    return company;
	}

	public void setCompany(Company param) {
	    this.company = param;
	}

	public List<EntityTestResultCommon> getEntityTestResultCommon() {
	    return entityTestResultCommon;
	}

	public void setEntityTestResultCommon(List<EntityTestResultCommon> param) {
	    this.entityTestResultCommon = param;
	}

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
    


}
