var tabla= $('#tabla').DataTable({
      "paging": false,
      "lengthChange": false,
      "searching": false,
      "ordering": true,
      "info": false,
      "autoWidth": false,
    "columnDefs": [ {
                "targets": -1,
                "data": null,
                "defaultContent": '<button class="btn btn-danger">Expulsar</button>'
            } ]
    });

$('#tabla tbody').on( 'click', 'button', function () {
    var data = tabla.row( $(this).parents('tr') ).data();
        var mensaje = '{ mensaje: {tipo: "EXPULSION", id: '+ data[0] + '} }';
        enviar(mensaje);
    } );

var ws = null;

var grafico, data =[];

var ultimo_dato_usuarios;

var mensaje_presentacion = '{ mensaje: {tipo: "CONEXION", idUsuario: ' + $('#ID_USUARIO').text() + ', nombre: "' + $('#NOMBRE').text() + '"} }';

var opciones = {
      grid: {
        borderColor: "#f3f3f3",
        borderWidth: 1,
        tickColor: "#f3f3f3"
      },
      series: {
        shadowSize: 0, // Drawing is faster without shadows
        color: "#3c8dbc"
      },
      lines: {
        fill: true, //Converts the line chart to area chart
        color: "#3c8dbc"
      },
      xaxis: {
        show: true,
        tickDecimals:0
      },
      yaxis: {
          tickDecimals:0
      }
    };
    
grafico = $.plot("#interactive", [data], opciones);
conectar();
function conectar() {
    
    var URL = "ws://localhost:8080/Eventual-war/administradorWS";
    
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
        enviar(mensaje_presentacion);
        actualizarDatos();
        setInterval(actualizarGrafico, 2000);
    };
    
    ws.onmessage = function (evento) {
        var mensaje = evento.data;
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

function enviar(mensaje) {
    if (ws !== null) {
        ws.send(mensaje);
    }
}

function actualizarGrafico() {
    data.push([data.length + 2, ultimo_dato_usuarios]);
    var datos = [{label: " Conectados", data: data}];
    grafico = $.plot("#interactive", datos, opciones);
}

function procesarMensaje(recibido) {
    console.log(recibido.tipo);
    switch (recibido.tipo) {
        case "CONECTADOS":
            ultimo_dato_usuarios = recibido.conectados;
            break;
        case "NUMERO_MENSAJES":
            $('#numero_mensajes').text(parseInt($('#numero_mensajes').text()) + 1);
        break;
        case "NUMERO_POSTS":
            $('#numero_posts').text(parseInt($('#numero_posts').text()) + 1);
        break;
        case "NUEVO_REGISTRO":
            $('#numero_registros').text(parseInt($('#numero_registros').text()) + 1);
        break;
        case "CONECTADO":
            //$('#tabla tr:last').after('<tr value="'+ recibido.id + '"><td>'+ recibido.id + '</td><td>'+ recibido.nombre +'</td><td><button class="btn btn-danger">Expulsar</button></td></tr>');
            var rowNode = tabla
                .row.add( [ recibido.id , recibido.nombre] )
                .draw()
                .node();
            $( rowNode )
                .css( 'color', 'red' )
                .animate( { color: 'black' } );
        break;
        case "DESCONECTADO":
            var indexes = tabla.rows().eq( 0 ).filter( function (rowIdx) {
                return tabla.cell( rowIdx, 0 ).data() == recibido.id;
            } );
            tabla.row(indexes["0"]).remove().draw();;
        break;
        case "LISTA_CONECTADOS":
            escribirTabla(recibido.lista);
        break;
    }
}

function escribirTabla(lista) {
    for (var i = 0; i < lista.length; i++) {
        var rowNode = tabla
           .row.add( [ lista[i].id , lista[i].nombre] )
           .draw()
           .node();
       $( rowNode )
           .css( 'color', 'red' )
           .animate( { color: 'black' } );       
    }
}

function actualizarDatos() {
    var mensaje = '{ mensaje: {tipo: "ADMINISTRADOR"} }';
    enviar(mensaje);
    return 0;
}