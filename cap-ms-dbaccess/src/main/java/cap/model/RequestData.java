package cap.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestData {
	String request;	// query - procedure
	String type;    // select - update (update-insert-delete)
	String spName;  // procedure or package name
	String query;	// query a ejecutar
	List<Map<String,Object>> params = new ArrayList<>();  //parametros del sp o package
	
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSpName() {
		return spName;
	}
	public void setSpName(String spName) {
		this.spName = spName;
	}
	public List<Map<String, Object>> getParams() {
		return params;
	}
	public void setParams(List<Map<String, Object>> params) {
		this.params = params;
	}
}
