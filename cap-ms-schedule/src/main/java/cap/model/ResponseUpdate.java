package cap.model;

public class ResponseUpdate {
	
	String status;
	int errCode;
	String errMesg;
	
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
