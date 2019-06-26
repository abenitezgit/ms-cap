package cap.services;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cap.controllers.AppProperties;
import cap.model.RequestData;

public class DBService {

	private static final Logger logger = LoggerFactory.getLogger(DBService.class);
	
	AppProperties gParams = new AppProperties();
	MetaData md;
	
	public DBService(AppProperties gParams) {
		this.gParams = gParams;
		md = new MetaData(gParams);
	}
	
	public Object executeProcedure(RequestData rd) throws Exception {
		try {
			Object result = null;
			
			logger.info("Iniciando solicitud de recuperación de datos");
			
			switch(rd.getType()) {
				case "query":

					logger.info("Solicitando recuperación de datos...");
					@SuppressWarnings("unchecked") List<Map<String, Object>> rows = (List<Map<String, Object>>) md.executeProcedure(rd.getSpName(), rd.getType(), rd.getParams());
					
					result = rows;
					
					break;
				case "update":
					
					logger.info("Actualizando datos...");
					boolean response = (boolean) md.executeProcedure(rd.getSpName(), rd.getType(), rd.getParams());
					
					result = response;
					
					break;
			}
			
			logger.info("Termino de solicitud de recuperación de datos");
			
			return result;
		} catch (Exception e) {
			throw new Exception("executeProcedure(): "+e.getMessage());
		}
	}

	public Object executeQuery(RequestData rd) throws Exception {
		try {
			Object result = null;
			
			logger.info("Iniciando solicitud de recuperación de datos");
			
			switch(rd.getType()) {
				case "query":
					
					logger.info("Solicitando recuperación de datos...");
					@SuppressWarnings("unchecked") List<Map<String, Object>> rows = (List<Map<String, Object>>) md.executeQuery(rd.getQuery(), rd.getType());
					
					result = rows;
					
					break;
				case "update":
					
					logger.info("Actualizando datos...");
					boolean response = (boolean) md.executeQuery(rd.getQuery(), rd.getType());
					
					result = response;
					
					break;
			}
			
			logger.info("Termino de solicitud de recuperación de datos");
			
			return result;
		} catch (Exception e) {
			throw new Exception("executeQuery(): "+e.getMessage());
		}
	}

}
