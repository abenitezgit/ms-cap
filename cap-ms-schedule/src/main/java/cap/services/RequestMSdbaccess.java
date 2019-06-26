package cap.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cap.controllers.AppProperties;
import cap.model.RequestData;
import cap.model.ResponseQuery;

@Component
public class RequestMSdbaccess {
	private static final Logger logger = LoggerFactory.getLogger(RequestMSdbaccess.class);
	
	@Autowired
	private AppProperties gParams;

	public List<Map<String,Object>> getGroupActiveServer() throws Exception {
		try {
			logger.info("Iniciando acceso a MSdbaccess...");
			List<Map<String,Object>> rows = new ArrayList<>();
			
			String URL_MS_DBACCESS = "http://"+gParams.getHostMSdbaccess()+":"+gParams.getPortMSdbaccess()+gParams.getUrlMSdbaccess();  //"${app.urlMSdbaccess:/ms-cap/dbaccess'}";
			String URL_SUFIJO = "/procedure";
			
			/**
			 * Generando el Request data como Objeto
			 */
			
			//Parametros del sp
			List<Map<String, Object>> params = new ArrayList<>();
			Map<String, Object> param = new HashMap<>();
			param.put("IN", gParams.getAgeGapMinute());
			params.add(param);
			
			RequestData rd = new RequestData();
			rd.setRequest("procedure");
			rd.setType("query");
			rd.setSpName("sp_get_groupActiveServerV2");
			rd.setParams(params);

			logger.info("URL POST Request: "+URL_MS_DBACCESS+URL_SUFIJO);
			logger.info("Enviando POST request: "+serializeObjectToJSon(rd, false));
			/**
			 * Generando el Header del Request
			 */
			
	        // HttpHeaders
	        HttpHeaders headers = new HttpHeaders();
	 
	        //headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
	        // Request to return JSON format
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        //headers.set("my_other_key", "my_other_value");

	        /**
	         * Generando el request data que se enviara por POST
	         */
	        
	        HttpEntity<RequestData> request = new HttpEntity<>(rd, headers);
	 
	        // RestTemplate
	        RestTemplate restTemplate = new RestTemplate();
	 
	        // Send request with POST method, and Headers.
	        logger.info("Esperando respuesta...");
	        try {
	        	ResponseEntity<ResponseQuery> result = restTemplate.postForEntity(URL_MS_DBACCESS+URL_SUFIJO, request, ResponseQuery.class);
	        	
		        logger.info("Request Status code: "+result.getStatusCodeValue());
				if (result.getStatusCodeValue()==200) {
					rows = result.getBody().getData();
					logger.info("Se han recuperado "+rows.size()+ " filas");
					gParams.setMS_ALERT_DBACCESS(true);
				} else {
					gParams.setMS_ALERT_DBACCESS(false);
					logger.error("Error en respuesta de request: "+result.getBody().getErrMesg());
				}
	        } catch (Exception ex) {
	        	gParams.setMS_ALERT_DBACCESS(false);
	        	logger.error("Error Ejecutando request: "+ex.getMessage());
	        }
	 
			
			return rows;
		} catch (Exception e) {
			logger.error("Error Ejecutando request: "+e.getMessage());
			gParams.setMS_ALERT_DBACCESS(false);
			throw new Exception("Error Ejecutando request: "+e.getMessage());
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
