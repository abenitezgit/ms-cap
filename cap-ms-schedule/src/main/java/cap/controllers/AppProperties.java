package cap.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppProperties {
	private String urlMSdbaccess;
	private String urlMStaskmanager;
	private int txpScheduleTime;
	private String portMSdbaccess;
	private String portMStaskmanager;
	private String hostMSdbaccess;
	private String hostMStaskmanager;
	private int ageGapMinute;
	
	/**
	 * Variables Globales
	 */
	private List<Map<String,Object>> groups;
	
	/**
	 * Varviable Globales de Alertas
	 */
	
	private boolean MS_ALERT_DBACCESS;
	private String MS_ALERT_DBACCESS_MESG;

	public boolean isMS_ALERT_DBACCESS() {
		return MS_ALERT_DBACCESS;
	}
	public void setMS_ALERT_DBACCESS(boolean mS_ALERT_DBACCESS) {
		MS_ALERT_DBACCESS = mS_ALERT_DBACCESS;
	}
	public String getMS_ALERT_DBACCESS_MESG() {
		return MS_ALERT_DBACCESS_MESG;
	}
	public void setMS_ALERT_DBACCESS_MESG(String mS_ALERT_DBACCESS_MESG) {
		MS_ALERT_DBACCESS_MESG = mS_ALERT_DBACCESS_MESG;
	}
	public List<Map<String, Object>> getGroups() {
		return groups;
	}
	public void setGroups(List<Map<String, Object>> groups) {
		this.groups = groups;
	}
	public int getAgeGapMinute() {
		return ageGapMinute;
	}
	public void setAgeGapMinute(int ageGapMinute) {
		this.ageGapMinute = ageGapMinute;
	}
	public String getPortMSdbaccess() {
		return portMSdbaccess;
	}
	public void setPortMSdbaccess(String portMSdbaccess) {
		this.portMSdbaccess = portMSdbaccess;
	}
	public String getPortMStaskmanager() {
		return portMStaskmanager;
	}
	public void setPortMStaskmanager(String portMStaskmanager) {
		this.portMStaskmanager = portMStaskmanager;
	}
	public String getHostMSdbaccess() {
		return hostMSdbaccess;
	}
	public void setHostMSdbaccess(String hostMSdbaccess) {
		this.hostMSdbaccess = hostMSdbaccess;
	}
	public String getHostMStaskmanager() {
		return hostMStaskmanager;
	}
	public void setHostMStaskmanager(String hostMStaskmanager) {
		this.hostMStaskmanager = hostMStaskmanager;
	}
	public String getUrlMSdbaccess() {
		return urlMSdbaccess;
	}
	public void setUrlMSdbaccess(String urlMSdbaccess) {
		this.urlMSdbaccess = urlMSdbaccess;
	}
	public String getUrlMStaskmanager() {
		return urlMStaskmanager;
	}
	public void setUrlMStaskmanager(String urlMStaskmanager) {
		this.urlMStaskmanager = urlMStaskmanager;
	}
	public int getTxpScheduleTime() {
		return txpScheduleTime;
	}
	public void setTxpScheduleTime(int txpScheduleTime) {
		this.txpScheduleTime = txpScheduleTime;
	}
}
