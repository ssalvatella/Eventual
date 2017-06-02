/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.stateless.modelo;

import java.util.List;
import javax.ejb.Remote;


/**
 *
 * @author Samuel
 */
@Remote
public interface UsuarioRemote {
    

    /**
     * valido()
     * 
     * Comprueba si el email y contraseña pasados como parámetros son 
     * válidos de un usuario del sistema.
     * @param email
     * @param contraseña
     * @return 'true' si datos correctos, 'false' en caso contrario
     */
   public boolean valido(String email, String contraseña);
   
   /**
    * existeEmail()
    * 
    * Devuelve 'true' si existe el email pasado por parámetro y 'false' en 
    * caso contrario.
    * @param email
    * @return 
    */
   public boolean existeEmail(final String email);    

   /**
    * registrar()
    * 
    * Registra un usuario como perfil social en la base de datos.
    * Devuelve 'false' en caso de que no se realice la insercción.
    * 
    * @param email
    * @param contraseña
    * @param nombre
    * @return 
    */
   public boolean registrar(final String email, final String contraseña, final String nombre);
   
   public boolean registrarOrganizacion(final String email, final String contraseña, final String nombre, final String dirrecion, final String ciudad);
   
    /**
     * devuelveUsuario()
     * 
     * Devuelve el usuario con el email pasado por parámetro.
     * @param email
     * @return 
     */
   public Usuario devuelveUsuario(String email);
   
   /**
    * devuelveUsuario()
    * 
    * Devuelve el usuario con el id pasado por parámetro.
    * @param id
    * @return 
    */
   public Usuario devuelveUsuario(int id);
   
   public List<Integer> devuelveIdsAmigos(int idUsuario) ;
   
   public void registrarAmistad(int id, int usuario);
   
   public boolean sonAmigos(int id, int usuario);

    public int getId();
    
    public String getEmail();

    public Usuario.TIPO getTipo();

    public String getFechaRegistro();

    public String getUltimaModificacion();
}
