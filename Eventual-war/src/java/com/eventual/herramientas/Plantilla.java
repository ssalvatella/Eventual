/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventual.herramientas;

/**
 *
 * @author Samuel
 */
public class Plantilla {
    
    private final static String[] HOJAS_CSS = 
        {"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css", 
        "./assets/plugins/admin-lte/css/AdminLTE.min.css",
        "./assets/plugins/admin-lte/css/skins/_all-skins.min.css",
        "https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css", 
        "https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css",
        "./assets/plugins/iCheck/square/blue.css", 
        "https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css",
        "./assets/plugins/tooltipster-master/dist/css/tooltipster.bundle.min.css",
        "./assets/plugins/tooltipster-master/dist/css/plugins/tooltipster/sideTip/themes/tooltipster-sideTip-shadow.min.css",
        "./assets/plugins/noty/noty.css"};
    
    public static String cargarHojasCSS() {
        String respuesta = "";
        for (String s : HOJAS_CSS) {
            respuesta += "<link href=\"" + s + "\" rel=\"stylesheet\"> \n \t\t";
        }
        return respuesta;
    }
    
    private final static String[] JAVASCRIPTS =
    {"https://code.jquery.com/jquery-3.2.1.min.js", 
    "https://code.jquery.com/ui/1.12.0/jquery-ui.min.js",
    "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js",
    "./assets/plugins/admin-lte/js/app.min.js", 
    "./assets/plugins/iCheck/icheck.min.js", 
    "./assets/plugins/tooltipster-master/dist/js/tooltipster.bundle.min.js", 
    "./assets/plugins/noty/noty.min.js"};
    
    public static String cargarJavaScripts() {
        String respuesta = "";
        for (String js : JAVASCRIPTS) {
            respuesta += "<script src=\"" + js + "\"> </script>\n \t\t";
        }
        return respuesta;
    }
        
}
