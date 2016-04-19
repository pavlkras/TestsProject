package main.java.model.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.stereotype.Component;

import main.java.model.dao.ActivityTypeData;

@Component
public class ActivityTypeMap extends LinkedHashMap<Integer, String> {
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	public ActivityTypeMap() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ActivityTypeMap(Map<? extends Integer, ? extends String> m) {
		super(m);
		// TODO Auto-generated constructor stub
	}
	
	public static Iterable<ActivityTypeData> convertToActivityTypeDataList(ActivityTypeMap activityTypes){
		List<ActivityTypeData> ret = new ArrayList<ActivityTypeData>();
		for (Integer id : activityTypes.keySet()){
			ret.add(new ActivityTypeData(id, activityTypes.get(id)));
		}
		return ret;
	}
}
