/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package despacho;

import Controlador.Controlador;
import despacho.Abogados.Abogado;
import despacho.Clientes.Cliente;
import conexionBBDD.ConexionMySQL;
import despacho.Clientes.ClienteContrario;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;

/**
 *
 * @author gerar
 */
public class Procedimiento {
    private String nAuto,juzgado,jucio,cuantia,id;
    private ArrayList<Cliente> clientes;
    private ArrayList<ClienteContrario> clientesContrarios;

    public Procedimiento() {
        clientes = new ArrayList();
        clientesContrarios = new ArrayList();
    }

    public String getnAuto() {
        return nAuto;
    }

    public void setnAuto(String nAuto) {
        this.nAuto = nAuto;
    }

    public String getJuzgado() {
        return juzgado;
    }

    public void setJuzgado(String juzagado) {
        this.juzgado = juzagado;
    }

    public String getJucio() {
        return jucio;
    }

    public void setJucio(String jucio) {
        this.jucio = jucio;
    }

    public String getCuantia() {
        return cuantia;
    }

    public void setCuantia(String cuantia) {
        this.cuantia = cuantia;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public ArrayList<ClienteContrario> getClientesContrarios() {
        return clientesContrarios;
    }

    public void setClientesContrarios(ArrayList<ClienteContrario> clientesContrarios) {
        this.clientesContrarios = clientesContrarios;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }  
    
    public void addCliente(Cliente cliente){
        this.clientes.add(cliente);
    }
    
    public void addClienteContrario(ClienteContrario cliente){
        this.clientesContrarios.add(cliente);
    }
    
    
    public void anadirABBDD(){
        ConexionMySQL con = new ConexionMySQL();
        String sql = "SELECT IDProcInterno FROM procedimiento WHERE `Numero Auto` = '"+nAuto+"' AND `Juzgado` = '"+juzgado+"' AND `Juicio` = '"+jucio+"' AND `Cuantia` = '"+cuantia+"'";
        ArrayList datos = con.executeQuery(sql);
        if(datos.isEmpty()){
            sql = "INSERT INTO `procedimiento` (`IDProcInterno`, `Numero Auto`, `Juzgado`, `Juicio`, `Cuantia`) VALUES (NULL, '"+nAuto+"', '"+juzgado+"', '"+jucio+"', '"+cuantia+"');";                  
        }  
        con.execute(sql);
        for (Cliente cliente : clientes) {
            cliente.anadirABBDD();
            Abogado a = cliente.getAbogado();
            sql = "INSERT INTO `procaboprocucliente` (`IDProcedimiento`, `IDProcurador`, `IDAbogado`, `IDCliente`) VALUES "
                    + "('"+getIDProcedimientoBBDD()+"', '"+a.getProcurador().getIDdeBBDD()+"', '"+a.getIDdeBBDD()+"', '"+cliente.getIDdeBBDD()+"');";
            con.execute(sql);
        }
        for (Cliente clienteContrario : this.clientesContrarios) {
            clienteContrario.anadirABBDD();
            Abogado a = clienteContrario.getAbogado();
            sql = "INSERT INTO `procaboprocuclienteContrario` (`IDProcedimiento`, `IDProcurador`, `IDAbogado`, `IDCliente`) VALUES "
                    + "('"+getIDProcedimientoBBDD()+"', '"+a.getProcurador().getIDdeBBDD()+"', '"+a.getIDdeBBDD()+"', '"+clienteContrario.getIDdeBBDD()+"');";
            con.execute(sql);
        }
    }
    public int getIDProcedimientoBBDD(){
        ConexionMySQL con = new ConexionMySQL();
        String sql = "SELECT IDProcInterno FROM Procedimiento WHERE `Numero Auto` = '"+nAuto+"' AND Juzgado = '"+juzgado+"' AND Juicio = '"+jucio+"' AND Cuantia = '"+cuantia+"'";
        HashMap<String,String> datos = (HashMap<String,String>) con.executeQuery(sql).get(0);
        id = datos.get("IDProcInterno");
        return Integer.parseInt(id); 
    }
    
    public void getProcedimientoBBDD(String id){
        ConexionMySQL con = new ConexionMySQL();
        String sql = "SELECT * FROM Procedimiento WHERE `IDProcInterno` = '"+id+"'";
        HashMap<String,String> datos = (HashMap<String,String>) con.executeQuery(sql).get(0);
        this.id = id;
        nAuto = datos.get("Numero Auto");
        jucio = datos.get("Juicio");
        juzgado = datos.get("Juzgado");
        cuantia = datos.get("Cuantia");
        sql = "SELECT PAPC.IDCliente,DNI,C.Nombre,C.Apellidos,PAPC.IDAbogado,A.Nombre 'Nombre Abogado',A.Apellidos 'Apellidos Abogado',P.`Nombre Despacho` FROM cliente C, procaboprocucliente PAPC,abogado A,Procurador P WHERE C.IDCliente = PAPC.IDCliente "+
                "AND PAPC.IDAbogado = A.IDAbogado AND PAPC.IDProcurador = P.IDProcurador AND PAPC.IDProcedimiento = '"+id+"'";
        ArrayList<HashMap<String,String>> datosClientes = con.executeQuery(sql);
        this.clientes = new ArrayList();
        for (HashMap<String, String> datosCliente : datosClientes) {
            Cliente cliente = new Cliente(datosCliente);
            this.clientes.add(cliente);
        }
        
        sql = "SELECT PAPCC.IDCliente,DNI,C.Nombre,C.Apellidos,PAPCC.IDAbogado,A.Nombre 'Nombre Abogado',A.Apellidos 'Apellidos Abogado',P.`Nombre Despacho` FROM cliente C, procaboprocuclientecontrario PAPCC,abogado A,Procurador P WHERE C.IDCliente = PAPCC.IDCliente "+
                "AND PAPCC.IDAbogado = A.IDAbogado AND PAPCC.IDProcurador = P.IDProcurador AND PAPCC.IDProcedimiento = '"+id+"'";
    
        ArrayList<HashMap<String,String>> datosClientesContrarios = con.executeQuery(sql);
        this.clientesContrarios = new ArrayList();
        for (HashMap<String, String> datosCliente : datosClientesContrarios) {
            ClienteContrario cliente = new ClienteContrario(datosCliente);
            this.clientesContrarios.add(cliente);
        }
    }

    public void anadirPadreBBDD(String idPadre) {
        String sql = "INSERT INTO `procedimientopadrehijo` (`IDProcedimientoPadre`, `IDProcedimientoHijo`) VALUES ('"+idPadre+"', '"+getIDProcedimientoBBDD()+"')";
        ConexionMySQL con = new ConexionMySQL();
        con.execute(sql);
    }
    
    public void anadirProcedimientoSwing(Controlador ctrl,JFrame ventana){
        
    }
    
    public void visualizarProcedimientoSwing(Controlador ctrl,JFrame ventana){
        
    }
    
    public void anadirProcedimientoHijoSwing(Controlador ctrl,JFrame ventana){
        
    }
}
