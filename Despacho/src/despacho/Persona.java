/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package despacho;

/**
 *
 * @author gerar
 */
public abstract class Persona {
    protected String id,dni,nombre,apellidos;
    
    public Persona(String id,String dni,String nombre,String apellidos){
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }
    public Persona(String dni,String nombre,String apellidos){
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }
    public Persona(){
        this.id = "";
        this.dni = "";
        this.nombre = "";
        this.apellidos = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public abstract int getIDdeBBDD();
    public abstract void anadirABBDD();
    
}
