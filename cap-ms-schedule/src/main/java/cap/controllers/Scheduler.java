package cap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cap.services.RequestMSdbaccess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class Scheduler {
	private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);
	
	@Autowired
	RequestMSdbaccess rMSdbaccess;
	
	@Autowired
	private AppProperties gParams;
	
	@Scheduled(fixedRateString = "${app.txpScheduleTime:10000}")
	public void cronJobFindGroupActive() {
		try {
			
			logger.info("Iniciando Ciclo Scheduler Service...");
			logger.info("MS_ALERT_DBACCESS: "+gParams.isMS_ALERT_DBACCESS());
			logger.info("MS_ALERT_DBACCESS_MESG: "+gParams.getMS_ALERT_DBACCESS_MESG());
			
			gParams.setGroups(rMSdbaccess.getGroupActiveServer());

			logger.info("# Grupos para activar: "+gParams.getGroups().size());
			
			logger.info("Finalizando Ciclo Scheduler Service...");
		} catch (Exception e) {
			logger.error("Error ejecutado Ciclo Sheduler Service: "+e.getMessage());
		}
   }
}