package com.claro.miclaro.controller;


import java.io.Serializable;
import java.sql.SQLException;

import static com.claro.miclaro.auxiliar.GetPicoPlaca.getPicoPlaca;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;


/*
* Mi Claro Web App -- AvisosBean
* @author Unisys
* @version 1.0
* @since   2015-01-01 
*/
@ManagedBean(name="avisos",eager=true)
@SessionScoped
public class AvisosBean implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @ManagedProperty(value="#{picoplaca1}") 
    private String picoplaca1;
    @ManagedProperty(value="#{picoplaca2}") 
    private String picoplaca2;
    
    
    public AvisosBean() {
        
    }
    
    public void generaPicoPlaca() throws SQLException {
        
        this.picoplaca1 = getPicoPlaca()[0];
        this.picoplaca2 = getPicoPlaca()[1];
        
    }

    public String getPicoplaca1() {
        return picoplaca1;
    }

    public void setPicoplaca1(String picoplaca) {
        this.picoplaca1 = picoplaca;
    }
    
    public String getPicoplaca2() {
        return picoplaca2;
    }

    public void setPicoplaca2(String picoplaca) {
        this.picoplaca2 = picoplaca;
    }
    
}

