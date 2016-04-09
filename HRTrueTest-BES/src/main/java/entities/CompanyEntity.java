package main.java.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.GenericGenerator;


@Entity(name="company")
@GenericGenerator(name="credentials-primarykey", strategy="foreign",
parameters={@Parameter(name="property", value="credentials")
})
public class CompanyEntity {
	@Id
	@GeneratedValue(generator = "credentials-primarykey")
	@Column(name="aa_id")
	long id;
	@MapsId
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "aa_id")
	CredentialsEntity credentials;
	@Column(name="name", length=50, nullable=false)
	String name;
	@Column(name="site", length=50)
	String site;
	@Column(name="specialization")
	byte specialization;
	@Column(name="employees_amnt")
	byte employees_amnt;
	@OneToMany(mappedBy="company",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	List<TemplateEntity> templates;
}
