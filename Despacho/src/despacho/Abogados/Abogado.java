/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package despacho.Abogados;

import conexionBBDD.ConexionMySQL;
import despacho.Persona;
import despacho.Procurador;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JDialog;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author gerar
 */
public class Abogado extends Persona{
    private Procurador procurador;
    
    public Abogado(){
        super();
    }
    
    public Abogado(String nombre,String apellidos,String nombreDespacho){
        super();
        super.nombre = nombre;
        super.apellidos = apellidos;
        procurador = new Procurador(nombreDespacho);
    }
    
    public Abogado(HashMap<String,String> datos){
        super();
        procurador = new Procurador(datos.get("Nombre Despacho"));
    }

    public Procurador getProcurador() {
        return procurador;
    }

    public void setProcurador(Procurador procurador) {
        this.procurador = procurador;
    } 

    @Override
    public void anadirABBDD() {
        ConexionMySQL con = new ConexionMySQL();
        String sql = "SELECT IDAbogado FROM Abogado WHERE Nombre = '"+super.nombre+"' AND Apellidos = '"+apellidos+"'";
        ArrayList datos = con.executeQuery(sql);
        if(datos.isEmpty()){
            sql = "INSERT INTO `abogado` (`IDAbogado`, `Nombre`, `Apellidos`) VALUES (NULL, '"+nombre+"', '"+apellidos+"');";
            con.execute(sql);
        }
        procurador.anadirABBDD();       
    }
    
    @Override
    public int getIDdeBBDD(){
        ConexionMySQL con = new ConexionMySQL();
        String sql = "SELECT IDAbogado FROM Abogado WHERE Nombre = '"+nombre+"' AND Apellidos = '"+apellidos+"'";
        HashMap<String,String> datos = (HashMap<String,String>) con.executeQuery(sql).get(0);
        id = datos.get("IDAbogado");
        return Integer.parseInt(id);
    }
    
    
    
}
