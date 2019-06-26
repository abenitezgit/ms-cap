package cap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import cap.model.RequestData;
import cap.model.ResponseQuery;
import cap.model.ResponseUpdate;
import cap.services.DBService;

@RestController
public class DBaccessAPI {
	private static final Logger logger = LoggerFactory.getLogger(DBaccessAPI.class);
	
	@Autowired
	private AppProperties apProperties;
	
	/**
	 * Request Mapping para ejecutar una query directa a base de datos (CRUD)
	 *
	 * Ejemplo:
	 * {
		 "request":"query",
		 "query":"select * from tb_groupControl order by numSecExec desc limit 10",
		 "type":"query"
		}
	 * 
	 * @param requestData
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/ms-cap/dbaccess/query", method=RequestMethod.POST)
    public ResponseEntity<Object> queryRequest(@RequestBody RequestData requestData) {
		
		ResponseQuery rq = new ResponseQuery();
		ResponseUpdate ru = new ResponseUpdate();
		DBService dbs = new DBService(apProperties);
		
		try {
			
			logger.info("Recibiendo TX /ms-cap/dbaccess/query");
			logger.info("Parseando datos de entrada...");
			
			if (parseRequestData(requestData,"query")) {
				
				logger.info("Datos recibidos correctamente");
				logger.info("Request: "+requestData.getRequest());
				logger.info("Type: "+requestData.getType());
				logger.info("spName: "+requestData.getSpName());
				logger.info("Query: "+requestData.getQuery());
				logger.info("RX Request: "+serializeObjectToJSon(requestData, false));

				logger.info("Enviando a Ejecutar request a DBService...");
				Object result = dbs.executeQuery(requestData);
				
				String classType = result.getClass().getSimpleName();
				logger.info("Se ha retorna una respuesta del tipo: "+classType);
				
				if (classType.equals("Boolean")) {
					
					//Respuesta Booleana
					Boolean respuesta = (Boolean) result;
					if (respuesta) {
						ru.setErrCode(0);
						ru.setStatus("OK");
					} else {
						ru.setErrCode(99);
						ru.setErrMesg("Actualización fallida");
						ru.setStatus("ERROR");
					}

					logger.info("Retornando respuesta: "+serializeObjectToJSon(ru, false));
					return new ResponseEntity<>(ru, HttpStatus.OK);
					
				} else {
					
					//Respuesta de Lista
					
					List<Map<String,Object>> lstRows = new ArrayList<>();
					lstRows = (List<Map<String, Object>>) result;
					
					rq.setData(lstRows);
					rq.setRows(lstRows.size());
					rq.setStatus("OK");
					
					logger.info("Retornando respuesta: "+serializeObjectToJSon(rq, false));
					return new ResponseEntity<>(rq, HttpStatus.OK);
				}
			} else {
				logger.error("Error en conformación del request");
				return new ResponseEntity<>("Error en conformación del request", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Error dbRequest: "+e.getMessage());
			return new ResponseEntity<>("Error dbRequest: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	/**
	 * Request Mapping para ejecutar un procedure o package en base de datos
	 *
	 * Ejemplo:
	 * {
		 "request":"procedure",
		 "type":"query",
		 "spName":"sp_get_category",
		 "params":[{"INO":"*"}]
		}
	 * @param requestData
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/ms-cap/dbaccess/procedure", method=RequestMethod.POST)
    public ResponseEntity<Object> procedureRequest(@RequestBody RequestData requestData) {

		ResponseQuery rq = new ResponseQuery();
		ResponseUpdate ru = new ResponseUpdate();
		DBService dbs = new DBService(apProperties);
		
		try {
			
			logger.info("Recibiendo TX /ms-cap/dbaccess/procedure");
			logger.info("Parseando datos de entrada...");
			
			if (parseRequestData(requestData,"procedure")) {
				
				logger.info("Datos recibidos correctamente");
				logger.info("Request: "+requestData.getRequest());
				logger.info("Type: "+requestData.getType());
				logger.info("spName: "+requestData.getSpName());
				logger.info("Query: "+requestData.getQuery());
				logger.info("RX Request: "+serializeObjectToJSon(requestData, false));

				logger.info("Enviando a Ejecutar request a DBService...");
				Object result = dbs.executeProcedure(requestData);
				
				String classType = result.getClass().getSimpleName();
				
				if (classType.equals("Boolean")) {
					
					//Respuesta Booleana
					Boolean respuesta = (Boolean) result;
					if (respuesta) {
						ru.setErrCode(0);
						ru.setStatus("OK");
					} else {
						ru.setErrCode(99);
						ru.setErrMesg("Actualización fallida");
						ru.setStatus("ERROR");
					}
					
					logger.info("Retornando respuesta: "+serializeObjectToJSon(ru, false));
					return new ResponseEntity<>(ru, HttpStatus.OK);
					
				} else {
					
					//Respuesta de Lista
					
					List<Map<String,Object>> lstRows = new ArrayList<>();
					lstRows = (List<Map<String, Object>>) result;
					
					rq.setData(lstRows);
					rq.setRows(lstRows.size());
					rq.setStatus("OK");
					
					logger.info("Retornando respuesta: "+serializeObjectToJSon(rq, false));
					return new ResponseEntity<>(rq, HttpStatus.OK);
				}

				
			} else {
				logger.error("Error en conformación del request");
				return new ResponseEntity<>("Error en conformación del request", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Error dbRequest: "+e.getMessage());
			return new ResponseEntity<>("Error dbRequest: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	private boolean parseRequestData(RequestData requestData, String request) {
		boolean valid = false;
		try {
			if (!Objects.isNull(requestData.getRequest()) && 
				!Objects.isNull(requestData.getType())) {
			
				if (requestData.getRequest().equals("query") || requestData.getRequest().equals("procedure")) {
					
					switch(requestData.getRequest()) {
					
						case "query":
							
							if (!Objects.isNull(requestData.getQuery()) && !requestData.getQuery().isEmpty() && request.contentEquals("query")) {
								valid = true;
							}
							
							break;
						case "procedure":
							
							if (!Objects.isNull(requestData.getSpName()) && !requestData.getSpName().isEmpty() && request.contentEquals("procedure")) {
								valid = true;
							}
							
							break;
						default:
							valid = false;
							break;
					}
				}
			}
			
			return valid;
		} catch (Exception e) {
			return valid;
		}
	}
	
    public String serializeObjectToJSon (Object object, boolean formated) throws Exception {
        try {
            ObjectMapper mapper = new ObjectMapper();

            mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, formated);

            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
