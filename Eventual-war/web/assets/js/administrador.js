var ws = null;

var grafico, data =[0], totalPoints, realtime, updateInterval;

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
        setInterval(actualizarDatos, 2000);
    };
    
    ws.onmessage = function (evento) {
        var mensaje = evento.data;
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

function procesarMensaje(recibido) {
    data.push([data.length + 2, recibido.conectados]);
    var datos = [{label: " Conectados", data: data}];
    grafico = $.plot("#interactive", datos, opciones);
}

function actualizarDatos() {
    var mensaje = '{ mensaje: {tipo: "ADMINISTRADOR"} }';
    enviar(mensaje);
    return 0;
}