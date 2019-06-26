package cap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cap.model.RequestData;
import cap.model.ResponseQuery;
import cap.model.ResponseUpdate;
import cap.services.ScheduleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class RestControllerAPI {
	private static final Logger logger = LoggerFactory.getLogger(RestControllerAPI.class);
	
	@Autowired
	private AppProperties apProperties;

	
	@RequestMapping(value="/ms-cap/schedule", method=RequestMethod.GET)
    public ResponseEntity<Object> invalidRequest() {

		logger.error("Requerimiento inválido por GET!");	
		return new ResponseEntity<>("Requerimiento inválido!", HttpStatus.BAD_REQUEST);
	}
	
	
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
	@RequestMapping(value="/ms-cap/schedule", method=RequestMethod.POST)
    public ResponseEntity<Object> queryRequest(@RequestBody RequestData requestData) {
		
		ResponseQuery rq = new ResponseQuery();
		ResponseUpdate ru = new ResponseUpdate();
		ScheduleService sds = new ScheduleService(apProperties);
		
		try {
			
			logger.info("Recibiendo TX /ms-cap/schedule");
			logger.info("Parseando datos de entrada...");
			
			if (parseRequestData(requestData,"query")) {
				
				logger.info("Datos recibidos correctamente");
				logger.info("Request: "+requestData.getRequest());
				logger.info("Type: "+requestData.getType());
				logger.info("spName: "+requestData.getSpName());
				logger.info("Query: "+requestData.getQuery());
				logger.info("RX Request: "+serializeObjectToJSon(requestData, false));

				logger.info("Enviando a Ejecutar request a DBService...");
				Object result = true; //dbs.executeQuery(requestData);
				
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
