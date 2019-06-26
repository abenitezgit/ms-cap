package cap.services;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cap.controllers.AppProperties;
import cap.controllers.Mysql8API;

public class MetaData {
	
	private static final Logger logger = LoggerFactory.getLogger(MetaData.class);
	AppProperties gParams;
	
	public MetaData(AppProperties gParams) {
		this.gParams = gParams;
	}
	
	
	public Object executeQuery(String query, String type) throws Exception {
		try {
			Object result = null;
			
			Mysql8API dbConn = new Mysql8API(	gParams.getDbhost(),
					gParams.getDbname(),
					gParams.getDbport(),
					gParams.getDbuser(),
					gParams.getDbpass(),
					gParams.getDbtimeout());

			logger.info("Iniciando Conexión a MetaData...");
			dbConn.open();
			
			if (dbConn.isConnected()) {
				
				logger.info("Procediendo a ejecutar query en MetaData...");
				switch(type) {
					case "query":
						
						if (dbConn.executeQuery(query)) {
							
							ResultSet rs = dbConn.getQuery();
							ResultSetMetaData rsm =  rs.getMetaData();
							
							if (rs!=null) {
								Map<String, Object> cols = new HashMap<>();
								List<Map<String, Object>> rows = new ArrayList<>();

								while (rs.next()) {
									cols = new HashMap<>();
									cols = parseResult(rs,rsm);
									rows.add(cols);
								}
								
								result = rows;
							} 
						} else {
							result = false;
						}
						
						break;
					case "update":
						
						if (dbConn.executeUpdate(query)>=0) {
							result = true;
						} else {
							result = false;
						}
						
						break;
				}
				
				logger.info("Término de ejecución de query en MetaData");
				
				dbConn.close();
			} else {
				result = false;
			}
			
			logger.info("Retornando respuesta a servicio...");
			return result;
		} catch (Exception e) {
			logger.error("Error de ejecución: "+e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	public Object executeProcedure(String spName, String type, List<Map<String, Object>> params) throws Exception {
		
		try {
			
			Object result = null;
			
			Mysql8API dbConn = new Mysql8API(	gParams.getDbhost(),
					gParams.getDbname(),
					gParams.getDbport(),
					gParams.getDbuser(),
					gParams.getDbpass(),
					gParams.getDbtimeout());

			logger.info("Iniciando Conexión a MetaData...");
			dbConn.open();
			
			if (dbConn.isConnected()) {
				
				logger.info("Procediendo a ejecutar procedure en MetaData...");
				switch(type) {
					case "query":
						if (dbConn.executeProcedure(spName, type, params)) {
							
							ResultSet rs = dbConn.getSpResult();
							ResultSetMetaData rsm =  rs.getMetaData();
							
							if (rs!=null) {
								Map<String, Object> cols = new HashMap<>();
								List<Map<String, Object>> rows = new ArrayList<>();

								while (rs.next()) {
									cols = new HashMap<>();
									cols = parseResult(rs,rsm);
									rows.add(cols);
								}
								
								result = rows;
							} 
							
						} else {
							result = false;
						}
						break;
				
					case "update":
						
						result = dbConn.executeProcedure(spName, type, params);
						
						break;
				}
				
				logger.info("Término de ejecución de procedure en MetaData");
				
				dbConn.close();
			} else {
				result = false;
			}
			
			logger.info("Retornando respuesta a servicio...");
			
			return result;
			
		} catch (Exception e) {
			logger.debug("Error de ejecución: "+e.getMessage());
			throw new Exception(e.getMessage());
		}
		
		
		
	}
	
	private Map<String, Object> parseResult(ResultSet rs, ResultSetMetaData rsm) throws Exception {
		
		Map<String, Object> cols = new HashMap<>();
		
		for (int i=1; i<=rsm.getColumnCount(); i++) {
			switch(rsm.getColumnType(i)) {
				case java.sql.Types.VARCHAR:
					cols.put(rsm.getColumnLabel(i), rs.getString(rsm.getColumnLabel(i)));
					break;
				case java.sql.Types.DATE:
					cols.put(rsm.getColumnLabel(i), rs.getDate(rsm.getColumnLabel(i)));
					break;
				case java.sql.Types.INTEGER:
					cols.put(rsm.getColumnLabel(i), rs.getInt(rsm.getColumnLabel(i)));
					break;
				default:
					cols.put(rsm.getColumnLabel(i), rs.getString(rsm.getColumnLabel(i)));
					break;
			}
		}
		return cols;
	}

}
