/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionBBDD;

import com.sun.rowset.CachedRowSetImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author gerar
 */
public class ConexionMySQL {
    private static Connection conn;
    private static String ip = "localhost";
    private static String puerto = "3306";
    private static String bbdd = "despacho";
    private static String driver = "com.mysql.jdbc.Driver";
    private static String user = "root";
    private static String pass = "";
    private static String url = "jdbc:mysql://localhost:3306/despacho";

    public ConexionMySQL() {
    }
    
    public ArrayList executeQuery(String sql){
        conn = null;
        ArrayList<HashMap> datos = new ArrayList();
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pass);
            
            if(conn != null){
                //System.out.println("Conexion establecida...");
                Statement s = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                                      ResultSet.CONCUR_READ_ONLY);
 
                ResultSet rs = s.executeQuery(sql);
                int columnas = rs.getMetaData().getColumnCount();
                String texto;               
                HashMap<String,String> fila;
                while(rs.next()){
                    fila = new HashMap();
                    //texto = rs.getMetaData().getColumnName(1)+"-->"+rs.getString(1);
                    //fila.put(rs.getMetaData().getColumnName(1), rs.getString(1));
                    for (int i = 1; i <= columnas; i++) {
                        //texto += ","+rs.getMetaData().getColumnName(i)+"-->"+rs.getString(i);
                        fila.put(rs.getMetaData().getColumnName(i), rs.getString(i));
                    }
                    //System.out.println(texto);
                    datos.add(fila);
                }
                /*for (HashMap<String,String> hashMap : datos) {
                    texto = "";
                    for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        texto += key+"-->"+value+",";
                    }
                    System.out.println(texto);
                }*/
                

                rs.close();
                s.close();
                conn.close();
                
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return datos;
    }
    
    public void execute(String sql){
        conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pass);
            if(conn != null){
                //System.out.println("Conexion establecida...");
                Statement s = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                                      ResultSet.CONCUR_READ_ONLY);
 
                s.execute(sql);
                s.close();
                conn.close();
                
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public Connection getConnection(){
        return conn;
    }
    
    
}
