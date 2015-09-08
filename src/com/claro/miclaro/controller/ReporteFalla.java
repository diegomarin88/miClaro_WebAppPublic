package com.claro.miclaro.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import com.claro.miclaro.auxiliar.Usuario;
import com.claro.miclaro.constantes.ClaroConstants;

/*
* Mi Claro Web App -- Recargar
* @author Unisys
* @version 1.0
* @since   2015-01-01 
*/
@ManagedBean(name="reporteFalla",eager=true)
@SessionScoped
public class ReporteFalla implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
	private static final Logger logger = Logger.getLogger(ReporteFalla.class);
	
	@ManagedProperty(value="#{catReporte}") 
	private String catReporte;
	@ManagedProperty(value="#{comment}") 
	private String comment;
	@ManagedProperty(value="#{email}") 
	private String email;
	@ManagedProperty(value="#{error}") 
	private String error;   
	
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void reportarFalla() {
				
		FacesContext context = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession)context.getExternalContext().getSession(true);
        Usuario usuario = (Usuario)sesion.getAttribute("usuario");
        
		try {
			 
			String urlStr = ClaroConstants.URL_REPORTE_FALLA;
			String userId = "anonimo";
			String userEmail = email;
			
			if(usuario!=null){
				userId = usuario.getTelefono();				
			}
			String soValue = "web_app";
			String versionCode = "1.0";
			String versionName = "1.0";
			
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
	 
			String input = "{\"userMail\":\"" + userEmail + "\",\"info\":{\"userAgent\":\"Mozilla/5.0 (Linux; U; Android 4.1.2; en-us; W851 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko)\",\"versionCode\":\"" + versionCode + "\",\"versionName\":\"" + versionName + "\",\"soVersion\":\"" + soValue + "\",\"userId\":\"" + userId + "\"},\"message\":\"" + catReporte + "*" + comment + "\"}";
			
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
	 
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				logger.error("Reportar Falla Speedy Movil:  HTTP error code : "
					+ conn.getResponseCode());
				throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
			}
	 
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
	 
			String responseObjt = "";
			String output = "";
			while ((output = br.readLine()) != null) {
				responseObjt = responseObjt + output;
			}
			
			conn.disconnect();
			
			try {
				JSONObject responseObject = new JSONObject(responseObjt);
				if(responseObject.getString("response").equals("fail")) {
					error = "001";
				} else {
					error = "000";
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
			
	 
		  } catch (RuntimeException e) { 
			  
			  error = "001 " + e.getMessage();
			  logger.error(e.getMessage());
				
		  } catch (MalformedURLException e) {
	 
			  error = "001 " + e.getMessage();
			  logger.error(e.getMessage());
	 
		  } catch (IOException e) {
	 
			  error = "001 " + e.getMessage();
			  logger.error(e.getMessage());
	 
		 }
		
		
		
	}
	
	public void guardarSugerencia() {
		
		FacesContext context = FacesContext.getCurrentInstance();
        HttpSession sesion = (HttpSession)context.getExternalContext().getSession(true);
        Usuario usuario = (Usuario)sesion.getAttribute("usuario");
        
		try {
			 
			String urlStr = ClaroConstants.URL_REPORTE_SUGERENCIA;
			String userId = "anonimo";
			String userEmail = email;
			
			if(usuario!=null){
				userId = usuario.getCedula();
			}
			String soValue = "web_app";
			String versionCode = "1.0";
			String versionName = "1.0";
			
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
	 
			String input = "{\"userMail\":\"" + userEmail + "\",\"info\":{\"userAgent\":\"Mozilla/5.0 (Linux; U; Android 4.1.2; en-us; W851 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko)\",\"versionCode\":\"" + versionCode + "\",\"versionName\":\"" + versionName + "\",\"soVersion\":\"" + soValue + "\",\"userId\":\"" + userId + "\"},\"message\":\"" + comment + "\"}";
			
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
	 
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				logger.error("Reportar Falla Speedy Movil:  HTTP error code : "
					+ conn.getResponseCode());
				throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
			}
	 
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
	 
			String responseObjt = "";
			String output = "";
			while ((output = br.readLine()) != null) {
				responseObjt = responseObjt + output;
			}
			
			conn.disconnect();
			
			try {
				JSONObject responseObject = new JSONObject(responseObjt);
				if(responseObject.getString("response").equals("fail")) {
					error = "001";
				} else {
					error = "000";
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		  } catch (RuntimeException e) { 
			  
			  error = "001 " + e.getMessage();
			  logger.error(e.getMessage());
				
		  } catch (MalformedURLException e) {
	 
			  error = "001 " + e.getMessage();
			  logger.error(e.getMessage());
	 
		  } catch (IOException e) {
	 
			  error = "001 " + e.getMessage();
			  logger.error(e.getMessage());
	 
		 }
	
	}

	public String getCatReporte() {
		return catReporte;
	}

	public void setCatReporte(String catReporte) {
		this.catReporte = catReporte;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}