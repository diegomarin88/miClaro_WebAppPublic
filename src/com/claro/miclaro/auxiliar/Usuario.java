package com.claro.miclaro.auxiliar;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import static com.claro.miclaro.auxiliar.ServicioMovil.imprimeReporteConsumo;
import com.claro.miclaro.constantes.ClaroConstants;
import com.claro.miclaro.utils.ClaroUtils;

/*
* Mi Claro Web App -- Usuario
* @author Unisys
* @version 1.0
* @since   2015-01-01 
*/
public class Usuario implements Serializable{
    
    private static final long serialVersionUID = 1L;
	
    
    String ID;
    String nombre;
    String pass;
    String email;
    String telefono;
    String cedula;
    String reporteConsumoUnicoServicio;
    int tipo; //0 prepago, 1 postpago
    List<ServicioMovil> servicios;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cd) {
        this.cedula = cd;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public List<ServicioMovil> getServicios() {
        return servicios;
    }

    public void setServicios(List<ServicioMovil> servicios) {
        this.servicios = servicios;
    }

    public String getTelefono() {
        return telefono;
    }
    
    public Usuario(String nombre, 
                   String telefono,
                   String cedula,
                   String email,
                   int tipo) {
    
        
        this.nombre = nombre;
        this.telefono = telefono;
        this.cedula = cedula;
        this.email = email;
        this.tipo = tipo;
    }
    
    public List<ServicioMovil> getServiciosClaro() {
        
        return this.servicios;
    }
    
    public int getNumServicios() {
        return this.servicios.size();
    }
    
    public String getReporteConsumoUnicoServicio() throws RemoteException {
        ServicioMovil s = this.servicios.get(0);
        
        try{
	    	//Controlar 3 veces el acceso diario al consumo
	    	//if (ClaroUtils.accesoConsultas(telefono))    	
	    	return imprimeReporteConsumo(s.ID,s.tipo);
	    		
        }catch(Exception e){
        	e.printStackTrace();
        }
        
        return ClaroUtils.getPropertyValue(ClaroConstants.TIEMPO_CONSULTAS);
    }
    
      
}
