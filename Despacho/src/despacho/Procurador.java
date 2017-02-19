/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package despacho;

import conexionBBDD.ConexionMySQL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author gerar
 */
public class Procurador extends Persona{
    private String nombreDespacho;
    
    public Procurador(String nombreDespacho){
        super();
        this.nombreDespacho = nombreDespacho;
    }

    public String getNombreDespacho() {
        return nombreDespacho;
    }

    public void setNombreDespacho(String nombreDespacho) {
        this.nombreDespacho = nombreDespacho;
    }
    
    
    
    @Override
    public int getIDdeBBDD(){
        ConexionMySQL con = new ConexionMySQL();
        String sql = "SELECT IDProcurador FROM Procurador WHERE `Nombre Despacho` = '"+nombreDespacho+"'";
        HashMap<String,String> datos = (HashMap<String,String>) con.executeQuery(sql).get(0);
        return Integer.parseInt(datos.get("IDProcurador"));
    }

    @Override
    public void anadirABBDD() {
        ConexionMySQL con = new ConexionMySQL();
        String sql = "SELECT IDProcurador FROM procurador WHERE `Nombre Despacho` = '"+nombreDespacho+"'";
        ArrayList datos = con.executeQuery(sql);
        if(datos.isEmpty()){
            sql = "INSERT INTO `procurador` (`IDProcurador`, `Nombre Despacho`) VALUES (NULL, '"+nombreDespacho+"');";
            con.execute(sql);
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
