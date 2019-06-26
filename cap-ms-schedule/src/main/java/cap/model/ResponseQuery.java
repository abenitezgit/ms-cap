package cap.model;

import java.util.List;
import java.util.Map;

public class ResponseQuery {
	
	String status;
	int errCode;
	String errMesg;
	int rows;
	List<Map<String, Object>> data;
	
	
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public List<Map<String, Object>> getData() {
		return data;
	}
	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getErrCode() {
		return errCode;
	}
	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}
	public String getErrMesg() {
		return errMesg;
	}
	public void setErrMesg(String errMesg) {
		this.errMesg = errMesg;
	}
}
