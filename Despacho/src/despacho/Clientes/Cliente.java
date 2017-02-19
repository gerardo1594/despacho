/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package despacho.Clientes;

import conexionBBDD.ConexionMySQL;
import despacho.Abogados.Abogado;
import despacho.Persona;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author gerar
 */
public class Cliente extends Persona{
    private Abogado abogado;

    public Cliente(String dni,String nombre,String apellidos){
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public Cliente(HashMap<String,String> datos){
        this.id = datos.get("IDCliente");
        this.dni = datos.get("DNI");
        this.nombre = datos.get("Nombre");
        this.apellidos = datos.get("Apellidos");
        abogado = new Abogado(datos);
    }
    
    public Cliente() {
        super();
    }

    public Abogado getAbogado() {
        return abogado;
    }

    public void setAbogado(Abogado abogado) {
        this.abogado = abogado;
    }
    
    @Override
    public void anadirABBDD(){
        ConexionMySQL con = new ConexionMySQL();
        String sql = "SELECT IDCliente FROM Cliente WHERE DNI = '"+dni+"' AND Nombre = '"+nombre+"' AND Apellidos = '"+apellidos+"'";
        ArrayList datos = con.executeQuery(sql);
        if(datos.isEmpty()){
            sql = "INSERT INTO `cliente` (`IDCliente`, `DNI`, `Nombre`, `Apellidos`) VALUES (NULL, '"+dni+"', '"+nombre+"', '"+apellidos+"');";       
            con.execute(sql);
        }        
        abogado.anadirABBDD();
    }
    
    public int getIDdeBBDD(){
        ConexionMySQL con = new ConexionMySQL();
        String sql = "SELECT IDCliente FROM Cliente WHERE DNI = '"+dni+"' AND Nombre = '"+nombre+"' AND Apellidos = '"+apellidos+"'";
        HashMap<String,String> datos = (HashMap<String,String>) con.executeQuery(sql).get(0);
        this.id = datos.get("IDCliente");
        return Integer.parseInt(id);
    }
    
    
    
    
    
    
}
