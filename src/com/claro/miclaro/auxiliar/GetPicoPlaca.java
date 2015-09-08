package com.claro.miclaro.auxiliar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.log4j.Logger;
import com.claro.miclaro.constantes.ClaroConstants;

/*
* Mi Claro Web App -- GetPicoPlaca
* @author Unisys
* @version 1.0
* @since   2015-01-01 
*/
public class GetPicoPlaca {
	
	private static final Logger logger = Logger.getLogger(GetPicoPlaca.class);
    
    public static String[] getPicoPlaca() {
        
        String[] salida = new String[2];
        salida[0] = "";
        salida[1] = "";
        
        String url = ClaroConstants.URL_PICO_PLACA;
        
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
            }
            in.close();

            String htmlResponse =response.toString();
            
            if(htmlResponse!=null && !htmlResponse.equals("")) {
                salida[0] = htmlResponse.substring(htmlResponse.indexOf("<span id=\"ContentPlaceHolder1_ctl00_numero1\" class=\"izq_pico0\">")+63).substring(0, 2);
                salida[1] = htmlResponse.substring(htmlResponse.indexOf("<span id=\"ContentPlaceHolder1_ctl00_numero2\" class=\"der_pico0\">")+63).substring(0,2);
            }
            
        } catch (MalformedURLException e1) {
           logger.error(e1.getMessage());
        } catch (IOException e) {
        	logger.error(e.getMessage()); 
        }  
        
        return salida;
                
    }
    
}
