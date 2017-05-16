

var ws = null;

var mensaje_presentacion = '{ mensaje: {tipo: "CONEXION", idUsuario: ' + $('#ID_USUARIO').text() + ', nombre: "' + $('#NOMBRE').text() + '"} }';

function conectar() {
    
    var URL = "ws://localhost:8080/Eventual-war/chat";
    
    if ('WebSocket' in window) {
            ws = new WebSocket(URL);
    } else if ('MozWebSocket' in window) {
            ws = new MozWebSocket(URL);
    } else {
            alert('Tu navegador no soporta WebSockets');
            return;
    }
    
    ws.onopen = function () {
        console.log("Conexión realizada con éxito.");
        enviarMensaje(mensaje_presentacion);
        $('#cargando').hide();
    };
    
    ws.onmessage = function (evento) {
        var mensaje = evento.data;
        console.log("Mensaje recibido: ");
        console.log(mensaje);
        procesarMensaje(JSON.parse(mensaje));
    };
    
    ws.onclose = function () {
        console.log("Desconexión realizada!");
    };
    
    ws.onerror = function (event) {
        console.log("ERROR!");
        console.log(event);
    };

}

function enviarMensaje(mensaje) {
    if (ws !== null) {
        console.log("Enviando mensaje : " + mensaje);
        ws.send(mensaje);
    }
}

function procesarMensaje(recibido) {
    switch(recibido.tipo) {
        case "CONECTADOS":
            console.log(recibido.conectados.length + " amigos conectados.");
            mostrarAmigos(recibido.conectados);
            break;
        case "CONECTADO":
            console.log("Amigo "+ recibido.nombre +" conectado.");
            conectarAmigo(recibido);
            break;
        case "DESCONECTADO":
            console.log("Desconectado amigo " + recibido.id);
            desconectarAmigo(recibido.id);
            break;
    }    
}

function mostrarAmigos(amigos) {
    
    $('#lista_amigos').empty();
    for (var i = 0; i < amigos.length; i++) {
        $('#lista_amigos').append('<li value="' + amigos[i].id +'"><img class="contacts-list-img" src="./assets/plugins/admin-lte/img/avatar5.png" alt="Avatar de contacto"><div class="contacts-list-info"><span class="contacts-list-name">' + amigos[i].nombre + '</span></div></li>');
    }
    
}

function conectarAmigo(amigo) {
    $('#lista_amigos').append('<li value="' + amigo.id +'"><img class="contacts-list-img" src="./assets/plugins/admin-lte/img/avatar5.png" alt="Avatar de contacto"><div class="contacts-list-info"><span class="contacts-list-name">' + amigo.nombre + '</span></div></li>');
}

function desconectarAmigo(id) {
    $("li[value='" + id + "']").remove();
}
    
conectar();
