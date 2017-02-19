/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package despacho.Clientes;

import java.util.HashMap;

/**
 *
 * @author gerar
 */
public class ClienteContrario extends Cliente{
    
    public ClienteContrario(){
        super();
    }
    
    public ClienteContrario(HashMap<String,String> datos){
        super(datos);
    }
    
    public ClienteContrario(String dni,String nombre,String apellidos){
        super(dni,nombre,apellidos);
    }
}
