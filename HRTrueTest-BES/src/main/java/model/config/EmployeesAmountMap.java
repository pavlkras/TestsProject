package main.java.model.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.stereotype.Component;

import main.java.model.dao.EmployeesAmountData;

@Component
public class EmployeesAmountMap extends LinkedHashMap<Integer, String> {
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	public EmployeesAmountMap() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmployeesAmountMap(Map<? extends Integer, ? extends String> m) {
		super(m);
		// TODO Auto-generated constructor stub
	}
	
	public static Iterable<EmployeesAmountData> convertToEmployeesAmountDataList(EmployeesAmountMap map){
		List<EmployeesAmountData> ret = new ArrayList<EmployeesAmountData>();
		for (Integer id : map.keySet()){
			ret.add(new EmployeesAmountData(id, map.get(id)));
		}
		return ret;
	}
}
