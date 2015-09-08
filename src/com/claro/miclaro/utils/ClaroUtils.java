package com.claro.miclaro.utils;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.claro.miclaro.constantes.ClaroConstants;
import com.unisys.claro.model.PlanTarifario;

public class ClaroUtils {
	
	//Fichero de propiedades comunes de la aplicación
	private static Properties props = new Properties();
	//Flag que indica si el fichero de propiedades se ha cargado
	private static boolean isPropsLoaded = false;
	private static final String file = "claro.properties";
	private static Logger logger = Logger.getLogger(ClaroUtils.class);

	
	/**
	 * Método que obtiene un valor de una propiedad del fichero de properties
	 * @param id El id de la propiedad que queremos obtener
	 * @return String. El valor de la propiedad
	 */
	public static String getPropertyValue(String id){		
		//Log
		logger.debug("Inicio del método getPropertyValue(String id)");
		logger.debug("id ["+id+"]");
		//Log
		//Si no estaba cargado el fichero de propiedades lo cargamos
		if(!isPropsLoaded)
			loadProperties();
		
		String value = null;
		if(props!=null && id!=null && props.getProperty(id)!=null){
			value = props.getProperty(id);
		}
		//Log
		logger.debug("Fin del método getPropertyValue(String id)");
		logger.debug("return ["+value+"]");
		//Log
		return value;
	}

	/**
	 * Método que carga el fichero de propiedades de la aplicación
	 * @param file El nombre del fichero de propiedades
	 */
	private static void loadProperties(){
		//Log
		logger.debug("Inicio del método loadProperties()");
		//Log
		//Cargamos el fichero de propiedades
		try{
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(file));
			isPropsLoaded = true;
		}catch(IOException e){
			e.printStackTrace();
		}
		//Log
		logger.debug("Fin del método loadProperties()");
		//Log
	}
	
	public static String numElegidos(List<String> elegidos, boolean cambio, String numNew, String numOld){
		
		//Formato a devolver --> ELEGIDO1;ELEGIDO2;ELEGIDO3;...;ELEGIDO9;
		String elementos="";
		String separador=";";
					
		for (int i = 0; i < elegidos.size(); i++) {
			if ((cambio) && (elegidos.get(i).equals(numOld)))
				elementos+=numNew+separador;
			else elementos+=elegidos.get(i)+separador;	
		}
		
		//Eliminamos el ultimo ;
		return elementos.substring(0, elementos.length()-1);
	}
	
	public static boolean validaNum(String telefono){
		
		if ((telefono.length() == 10) && (isNumeric(telefono)))		
			return true;
		return false;
	}
	
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	
	public static String procesaError(String error){
	
		String mensaje="";
		switch (error) {
		case "900":
			mensaje=ClaroUtils.getPropertyValue(ClaroConstants.ERROR_900);
			break;
		case "902":
			mensaje=ClaroUtils.getPropertyValue(ClaroConstants.ERROR_902);
			break;
		case "903":
			mensaje=ClaroUtils.getPropertyValue(ClaroConstants.ERROR_903);
			break;
		case "917":
			mensaje=ClaroUtils.getPropertyValue(ClaroConstants.ERROR_917);
			break;
		case "950":
			mensaje=ClaroUtils.getPropertyValue(ClaroConstants.ERROR_950);
			break;
		case "951":
			mensaje=ClaroUtils.getPropertyValue(ClaroConstants.ERROR_951);
			break;
		case "952":
			mensaje=ClaroUtils.getPropertyValue(ClaroConstants.ERROR_952);
			break;
		case "953":
			mensaje=ClaroUtils.getPropertyValue(ClaroConstants.ERROR_953);
			break;
		case "954":
			mensaje=ClaroUtils.getPropertyValue(ClaroConstants.ERROR_954);
			break;
		case "955":
			mensaje=ClaroUtils.getPropertyValue(ClaroConstants.ERROR_955);
			break;
		case "956":
			mensaje=ClaroUtils.getPropertyValue(ClaroConstants.ERROR_956);
			break;
		case "957":
			mensaje=ClaroUtils.getPropertyValue(ClaroConstants.ERROR_957);
			break;
		case "958":
			mensaje=ClaroUtils.getPropertyValue(ClaroConstants.ERROR_958);
			break;
		case "959":
			mensaje=ClaroUtils.getPropertyValue(ClaroConstants.ERROR_959);
			break;
		case "960":
			mensaje=ClaroUtils.getPropertyValue(ClaroConstants.ERROR_960);
			break;
		case "999":
			mensaje=ClaroUtils.getPropertyValue(ClaroConstants.ERROR_999);
			break;
		default:
			mensaje=ClaroUtils.getPropertyValue(ClaroConstants.ERROR_999);
			break;
		}
		
		return mensaje;
	}

	public static PlanTarifario getPlan(List<PlanTarifario> planes, String indice){
	
		PlanTarifario plan = new PlanTarifario();
		
		for (int i = 0; i < planes.size(); i++) {
			plan= planes.get(i);
			if (plan.getStrIndice().equals(indice)){
				break;
			}			
		}
		
		return plan;
		
	}
	

	public static boolean compareToday(Date fecha){		
		
		if (fecha == null)
			return false;
		
		java.util.Calendar cal = java.util.Calendar.getInstance();
		
		String mes = "";
		if ((cal.get(Calendar.MONTH)+1)<10)
			mes += "0";
		mes+=cal.get(Calendar.MONTH)+1;
		
		String day = "";
		if ((cal.get(Calendar.DAY_OF_MONTH)+1)<10)
			day+="0";
		day+=cal.get(Calendar.DAY_OF_MONTH);
		
		String hoy = cal.get(Calendar.YEAR)+"-"+mes+"-"+day;
				
		if (fecha.toString().equals(hoy))
			return true;
		
    	return false;		
    	
	}

}
