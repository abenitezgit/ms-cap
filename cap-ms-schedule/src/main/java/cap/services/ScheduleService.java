package cap.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cap.controllers.AppProperties;

public class ScheduleService {
	
	AppProperties gParams = new AppProperties();
	
	public ScheduleService(AppProperties m) {
		gParams = m;
	}
	
	public List<Map<String, Object>> getGroupActiveServer() throws Exception {
		try {
			List<Map<String, Object>> rows = new ArrayList<>();

			
			
			return rows;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
