/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.stateless.modelo;

import com.eventual.singleton.AdministracionLocal;
import com.eventual.singleton.BaseDatosLocal;
import com.eventual.singleton.ChatLocal;
import com.ocpsoft.pretty.time.PrettyTime;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Samuel
 */
@Stateless
public class Post implements PostRemote {
    
    @EJB
    private UsuarioRemote usuarios;
    
    @EJB
    private AdministracionLocal admin;
    
    @EJB
    private ChatLocal chat;
    
    private int idPost;
    private int idUsuario;
    private String nombreUsuario;
    private String fecha;
    private String contenido;
    
    private boolean meGustaPulsado;
    
    // ESTADÍSTICAS
    
    private int numero_comentarios;
    private int numero_me_gustas;
    
    private List<MeGusta> meGustas;
    private List<Comentario> comentarios;
    private List<Compartido> compartido;
    
    private final PrettyTime pt = new PrettyTime(new Locale("es"));
    
    public Post() {
        
    }
    
    public Post(int idPost, int idUsuario, String nombreUsuario, String fecha, String contenido) {
        this.nombreUsuario = nombreUsuario;
        this.idPost = idPost;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.contenido = contenido;
    }

    public Post(int idPost, int idUsuario, String nombreUsuario, String fecha, String contenido, int numero_comentarios, int numero_me_gustas, boolean meGustaPulsado) {
        this.nombreUsuario = nombreUsuario;
        this.idPost = idPost;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.contenido = contenido;
        this.numero_comentarios = numero_comentarios;
        this.numero_me_gustas = numero_me_gustas;
        this.meGustaPulsado = meGustaPulsado;
    }
    
    @EJB
    private BaseDatosLocal bd;

    @Override
    public void registrarPost(int idUsuario, String contenido) {
        try { 
            String consulta = "INSERT INTO post (usuario_post, contenido_post) VALUES (" +
                    idUsuario + ", '" + contenido + "');";
            Statement stm = bd.getStatement();
            stm.execute(consulta);
            // Notificar a admins...
            this.admin.notificarNumeroPosts();
            // Notificamos a los amigos
            this.chat.notificarPost(devuelveUltimoPost(idUsuario));
        } catch (SQLException ex) {
            Logger.getLogger(Mensaje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Post devuelveUltimoPost(int id) {
        
        try {
            // Construimos la consulta que obtendrá todo
            String consulta = "SELECT  id_post, usuario_post, nombre_organizacion , nombre_perfil, contenido_post, fecha_post, "
                    + "COUNT(id_comentario) as numero_comentarios, "
                    + "COUNT(id_me_gusta) as numero_me_gustas, "
                    + "COUNT(CASE WHEN me_gusta.usuario_me_gusta = "+ id +" THEN 1 END) as meGustaPulsado FROM post ";
            
            // Joins para obtener datos de los emisores
            consulta += "LEFT JOIN perfil_social ON (post.usuario_post = perfil_social.usuario_perfil) ";
            consulta += "LEFT JOIN perfil_organizacion ON (post.usuario_post = perfil_organizacion.id_organizacion) ";
            consulta += "LEFT JOIN me_gusta ON (post.id_post = me_gusta.post_me_gusta) ";
            consulta += "LEFT JOIN comentario ON (post.id_post = comentario.post_comentario) ";
            
            consulta += "WHERE usuario_post=" + id;
            consulta += " GROUP BY id_post ASC ";
            consulta += " ORDER BY fecha_post DESC ";
            consulta += " LIMIT 1; ";
            
            Statement stm = bd.getStatement();
            ResultSet rs = stm.executeQuery(consulta);
            
            while (rs.next()) {
                int idPost = rs.getInt("id_post");
                int idUsuario = rs.getInt("usuario_post");
                String nombreOrg = rs.getString("nombre_organizacion");
                String nombreUsuario = rs.getString("nombre_perfil");
                String contenido = rs.getString("contenido_post");
                
                String fechaPublicacion = rs.getString("fecha_post");
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                Date fecha = df.parse(fechaPublicacion);
                fechaPublicacion = pt.format(fecha);
                
                int numeroComentarios = rs.getInt("numero_comentarios");
                int numeroMeGustas = rs.getInt("numero_me_gustas");
                int mgp = rs.getInt("meGustaPulsado");
                boolean meGustaPulsado = (mgp > 0);
                String nombre = (nombreOrg == null) ? nombreUsuario : nombreOrg;
                return new Post(idPost, idUsuario, nombre, 
                        fechaPublicacion, contenido, numeroComentarios, numeroMeGustas, meGustaPulsado);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Post.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Post.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Post> devuelve(int id, int cantidad, int salto) {
        
        int limiteFinal = salto + cantidad;
        
        List<Post> resultados = new ArrayList<>();
        
        // Obtenemos los amigos del usuario
        List<Integer> amigos = this.usuarios.devuelveIdsAmigos(id);
        if (amigos.isEmpty()) return resultados;
        
        // Construimos la consulta que obtendrá todo
        String consulta = "SELECT  id_post, usuario_post, nombre_organizacion , nombre_perfil, contenido_post, fecha_post, "
                + "COUNT(id_comentario) as numero_comentarios, "
                + "COUNT(id_me_gusta) as numero_me_gustas, "
                + "COUNT(CASE WHEN me_gusta.usuario_me_gusta = "+ id +" THEN 1 END) as meGustaPulsado FROM post ";
        
        // Joins para obtener datos de los emisores
        consulta += "LEFT JOIN perfil_social ON (post.usuario_post = perfil_social.usuario_perfil) ";
        consulta += "LEFT JOIN perfil_organizacion ON (post.usuario_post = perfil_organizacion.id_organizacion) ";
        consulta += "LEFT JOIN me_gusta ON (post.id_post = me_gusta.post_me_gusta) ";
        consulta += "LEFT JOIN comentario ON (post.id_post = comentario.post_comentario) ";
        
        consulta += "WHERE ";
        
        // Solo los posts de sus amigos
        if (amigos.size() > 1) {
            consulta += "( ";
            consulta += "usuario_post=" + amigos.get(0) + " ";
            for (int i = 1; i < amigos.size(); i++) {
                consulta += "OR usuario_post=" + amigos.get(i) + " ";
            }
            consulta += ") ";
        } else {
            consulta += "usuario_post=" + amigos.get(0) + " ";
        }
        
        // Group by y Order By
        consulta += "GROUP BY id_post ASC ";
        consulta += "ORDER BY fecha_post DESC ";
        
        // Salto para obtener un rango de posts concretos
        consulta += "LIMIT "+ salto + ", " + limiteFinal;
 
        consulta += "; ";
        // Ejecutamos la consulta
        try {
            Statement stm = bd.getStatement();
            ResultSet rs = stm.executeQuery(consulta);
            while (rs.next()) {
                int idPost = rs.getInt("id_post");
                int idUsuario = rs.getInt("usuario_post");
                String nombreOrg = rs.getString("nombre_organizacion");
                String nombreUsuario = rs.getString("nombre_perfil");
                String contenido = rs.getString("contenido_post");
                
                String fechaPublicacion = rs.getString("fecha_post");
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                Date fecha = df.parse(fechaPublicacion);
                fechaPublicacion = pt.format(fecha);
                
                int numeroComentarios = rs.getInt("numero_comentarios");
                int numeroMeGustas = rs.getInt("numero_me_gustas");
                int mgp = rs.getInt("meGustaPulsado");
                boolean meGustaPulsado = (mgp > 0);
                String nombre = (nombreOrg == null) ? nombreUsuario : nombreOrg;
                resultados.add(new Post(idPost, idUsuario, nombre, 
                        fechaPublicacion, contenido, numeroComentarios, numeroMeGustas, meGustaPulsado));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Post.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Post.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return resultados;
    }
    
    @Override
    public int cuentaUltimosPosts() {
        try {
            String consulta = "SELECT COUNT(id_post) as numero_posts FROM post "
                    + "WHERE (TIMESTAMPDIFF(DAY, fecha_post,  NOW())) <= 1;";
            Statement stm = bd.getStatement();
            ResultSet rs = stm.executeQuery(consulta);
            if (rs.next()) {
                return rs.getInt("numero_posts");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        } 
        return -1;
    }    

    @Override
    public void añadirMeGusta(int usuario, int post) {
        try { 
            String consulta = "INSERT INTO me_gusta (post_me_gusta, usuario_me_gusta) VALUES (" +
                    post + ", " + usuario + ");";
            Statement stm = bd.getStatement();
            stm.execute(consulta);
            // Notificar
            this.chat.notificarMeGusta(post);
        } catch (SQLException ex) {
            Logger.getLogger(Mensaje.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    @Override
    public void quitarMeGusta(int usuario, int post) {
        try { 
            String consulta = "DELETE FROM me_gusta WHERE (post_me_gusta=" + post + " AND usuario_me_gusta=" + usuario +") ;";
            Statement stm = bd.getStatement();
            stm.execute(consulta);
            // Notificar
            this.chat.notificarEliminacionMeGusta(post);
        } catch (SQLException ex) {
            Logger.getLogger(Mensaje.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public int getIdPost() {
        return idPost;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getFecha() {
        return fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public int getNumero_comentarios() {
        return numero_comentarios;
    }

    public int getNumero_me_gustas() {
        return numero_me_gustas;
    }

    public List<MeGusta> getMeGustas() {
        return meGustas;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public List<Compartido> getCompartido() {
        return compartido;
    }

    public boolean isMeGustaPulsado() {
        return meGustaPulsado;
    }
    
    private class MeGusta {
        
        private final int idPost;
        private final int idUsuario;
        private final String nombreUsuario;
        
        public MeGusta(int idPost, int id, String nombre) {
            this.idPost = idPost;
            this.idUsuario = id;
            this.nombreUsuario = nombre;
        }
        
    }
    
    private class Compartido {
        
        private final int idPost;
        private final int idUsuario;
        private final String nombreUsuario;
        
        public Compartido(int idPost, int id, String nombre) {
            this.idPost = idPost;
            this.idUsuario = id;
            this.nombreUsuario = nombre;
        }
        
    }
    
    private class Comentario {
        
        private final int idPost;
        private final int idUsuario;
        private final String nombreUsuario;
        private final String contenido;
        
        public Comentario(int idPost, int id, String nombre, String contenido) {
            this.idPost = idPost;
            this.idUsuario = id;
            this.nombreUsuario = nombre;
            this.contenido = contenido;
        }       
    }
    
}
