package tel_ran.tests.services;
import javax.persistence.*;
import java.util.List;

public class Personal_Details {
    @Id
    int  personal_id;
    String f_name;
    String l_name;

    @ManyToOne
    Company id;
    @ManyToMany (mappedBy = "personal_datas")
    List<Test_Details>test_detailses;

    public Personal_Details() {
    }

    public Personal_Details(List<Test_Details> test_detailses, int personal_id, String f_name, String l_name, Company company_id) {
        this.test_detailses = test_detailses;
        this.personal_id = personal_id;
        this.f_name = f_name;
        this.l_name = l_name;
        id = company_id;
    }

    @Override
    public String toString() {
        return "Personal_Data{" +
                "personal_id=" + personal_id +
                ", f_name='" + f_name + '\'' +
                ", l_name='" + l_name + '\'' +
                ", test_detailses=" + test_detailses +
                '}';
    }

    public int getPersonal_id() {

        return personal_id;
    }

    public void setPersonal_id(int personal_id) {
        this.personal_id = personal_id;
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

    public Company getCompany_id() {
        return id;
    }

    public void setCompany_id(Company company_id) {
        this.id = company_id;
    }

    public List<Test_Details> getTest_detailses() {
        return test_detailses;
    }

    public void setTest_detailses(List<Test_Details> test_detailses) {
        this.test_detailses = test_detailses;
    }
}
