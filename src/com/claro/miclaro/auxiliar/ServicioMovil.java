package com.claro.miclaro.auxiliar;

import java.rmi.RemoteException;
import java.util.concurrent.TimeoutException;
import com.claro.miclaro.constantes.ClaroConstants;
import com.unisys.claro.cliente.ConsumosCliente;
import com.unisys.claro.model.ResultadoConsumo;

/*
* Mi Claro Web App -- ServicioMovil
* @author Unisys
* @version 1.0
* @since   2015-01-01 
*/
public class ServicioMovil {
    
    String ID;
    String tipo; //Prepago/Postpago

    public ServicioMovil() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ServicioMovil(String ID, String tipo) {
        this.ID = ID;
        this.tipo = tipo;
    }
    
    public static String cabeceraReportConsumo(String periodo, String fCorte) {               
        return "<hr/>\n";
    }
    
    public static String filaProgreso(String leyenda, int max, int consumo, String subleyenda) {
        
        
        String salida ="<div id=\"consumoContent\" class=\"thumbnail\">\n" +
                	"<table><tr>\n" +
                			"<td class=\"leftT\">" + leyenda + "\n"; 
        
        if(!subleyenda.equals("Error en la consulta")) {
        	salida = salida + "<td class=\"rightVBlack\">" + consumo + " " + subleyenda + "</td>\n";
        } else {
        	salida = salida + "<td class=\"leftV\">" + subleyenda + "</td>\n";
        }
        salida = salida + "</tr>\n</table></div>";
        return salida;
    }
    
    public static String filaError(String leyenda) {
        
        
        String salida ="<div id=\"consumoContent\" class=\"thumbnail\">\n" +
                	"<table><tr>\n" +
                			"<td class=\"leftT\">" + leyenda + "</div>\n" +
                		"</tr>\n" +
                		"<tr class=\"conSeparador\">\n";
        
        	salida = salida + "<td class=\"leftV\">NO DISPONIBLE</td>\n";
        
        salida = salida + "</tr>\n</table></div>";
        return salida;
    }
    
    public static String imprimeReporteConsumo(String idServicio, String tipo) throws RemoteException{
    	
        String salida = "";
        
        try {
        ConsumosCliente conCliente = new ConsumosCliente(ClaroConstants.ENV_NAME.equals("PROD"));
        ResultadoConsumo consumos = conCliente.ConsultaTiempoAlAire(idServicio); 
        
        //Superado nÃºmero mÃ¡ximo de consultas
        if("002".equals(consumos.getDatos().getAnswerCode())) {
        	salida="<h1>Se ha superado el número máximo de consultas diarias.</h1>\n";
        }
        
        //Consumos de Voz
        if("000".equals(consumos.getDatos().getAnswerCode()) || "997".equals(consumos.getDatos().getAnswerCode()) || "998".equals(consumos.getDatos().getAnswerCode())) {
        	
        	String unidades = consumos.getDatos().getUnidad().equals("M")?"Min":"Seg";
        	
        	salida = "<h1 class=\"cabeceraRed\"> Consumos </h1>\n" +
        	"<h4 align=\"justify\"> Consulte los consumos de su línea Claro, Esta información corresponde a consumos generados 12 horas antes de dicha consulta. </h4>\n"+ //Obtener el tipo de servicio!!!

		    filaProgreso("Celulares Claro",400,Integer.parseInt(consumos.getDatos().getSaldoComcel()),unidades) + //<SaldoComcel>                    
		    filaProgreso("Otros Operadores",40,Integer.parseInt(consumos.getDatos().getSaldoOtros()),unidades) + //<SaldoOtros>
		    filaProgreso("Números Fijos",400,Integer.parseInt(consumos.getDatos().getSaldoFijos()),unidades) + //<SaldoFijos>
		    filaProgreso("Números Elegidos",400,Integer.parseInt(consumos.getDatos().getSaldoElegidos()),unidades) +   //<SaldoElegidos>             
		    filaProgreso("Números Elegidos Fijos",400,Integer.parseInt(consumos.getDatos().getSaldoElegFijos()),unidades) + //<SaldoElegFijos>               
		    filaProgreso("Larga Dist. Internacional",40,Integer.parseInt(consumos.getDatos().getSaldoInternal()),unidades);   //<SaldoInternal>
		    if (consumos.getDatos().getSaldoComRoll()==null && "".equals(consumos.getDatos().getSaldoComRoll())) {
	                salida += filaProgreso("Pasamin. Disp. Claro",400,Integer.parseInt(consumos.getDatos().getSaldoRollO()),unidades);//<SaldoRollO> ÃƒÂ³ <SaldoComRoll>	               
		    } else {
	                salida += filaProgreso("Pasamin. Disp. Claro",400,Integer.parseInt(consumos.getDatos().getSaldoComRoll()),unidades); //<SaldoRollO> ÃƒÂ³ <SaldoComRoll>
		    }
		    salida += filaProgreso("Pasamin. Disp. Todo Destino",400,Integer.parseInt(consumos.getDatos().getSaldoMovRoll()),unidades)+ //<SaldoMovRoll>
		    filaProgreso("Pasamin. Disp. Fijos",400,Integer.parseInt(consumos.getDatos().getSaldoFijRoll()),unidades) + //<SaldoFijRoll>
		    filaProgreso("Celulares mismo NIT",400,Integer.parseInt(consumos.getDatos().getSaldoNIT()),unidades) + //<SaldoNIT>
		    filaProgreso("Consumo Total",4000,Integer.parseInt(consumos.getDatos().getSaldoTotal()),unidades); //<SaldoTotal>
        }
        
        //Datos OK (Consumos Internet)
        if("000".equals(consumos.getDatos().getAnswerCode())) {
		    
		    if(consumos.getDatos().getObjConsumosInternet().getStrTipoConsumo().equals("10")) {//KB		    			    	
		    	if(!"N/A".equals(consumos.getDatos().getObjConsumosInternet().getStrConsumoKb())) {
		    		salida += filaProgreso("Consumo GPRS en Kilobytes",400,Integer.parseInt(consumos.getDatos().getObjConsumosInternet().getStrConsumoKb())," Kb");
		    	}
		    } else { //MB
		    	if(!"N/A".equals(consumos.getDatos().getObjConsumosInternet().getStrConsumoMb())) {
		    		salida += filaProgreso("Consumo GPRS en Megabytes",400,Integer.parseInt(consumos.getDatos().getObjConsumosInternet().getStrConsumoMb())," Mb"); //<SaldoFijRoll>
		    	}
		    	if(!"N/A".equals(consumos.getDatos().getObjConsumosInternet().getStrConsumoMbBolsa())) {
		    		salida += filaProgreso("Consumo de Bolsa de Gb en Megabytes",400,Integer.parseInt(consumos.getDatos().getObjConsumosInternet().getStrConsumoMbBolsa())," Mb"); //<SaldoFijRoll>
		    	}
		    	
		    }
        }
        
        //Error en datos de consumos internet
        if("998".equals(consumos.getDatos().getAnswerCode())) {
        	salida += filaError("Consumo GPRS");
        }
        
	    //Error en la consulta
	    if(!"000".equals(consumos.getDatos().getAnswerCode()) && !"997".equals(consumos.getDatos().getAnswerCode()) && !"998".equals(consumos.getDatos().getAnswerCode())) {
            salida="<h1>Se ha producido un error al realizar tu consulta inténtalo de nuevo.</h1>\n";			
        }
        
        } catch (TimeoutException ex) {
            salida="<h1>Se ha producido un error al realizar tu consulta inténtalo de nuevo.</h1>\n";			
        } catch (Exception ex) {
            salida="<h1>Se ha producido un error al realizar tu consulta inténtalo de nuevo.</h1>\n";			
        }
        
       return salida;
    }

    
}
