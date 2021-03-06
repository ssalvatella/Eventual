
function salir() {
    
    window.location.replace('./');
    
}

$(document).ready(function() {
    
    var autocompletado = new Awesomplete(document.getElementById("buscador"));
    
    $('#buscador').on('awesomplete-selectcomplete', function () {
        $('#ir_perfil').submit();
    });
    
    function buscar() {

        var busqueda = $('#buscador').val();
        if (busqueda.length >= 2) {
            $.ajax({
               type: "POST",
               url: "./Buscador",
               data: {
                   'peticion' : 'buscar',
                   'campo' : busqueda
               },
               success: function(respuesta) {
                   console.log(respuesta);
                   var list = respuesta.map(function(i) {
                       if($('#ID_USUARIO').text() != i.idUsuario) {
                           return { label: i.nombre, value: i.idUsuario };
                       } else {
                           return {};
                       }
                   });
                   autocompletado.list = list;
                   autocompletado.evaluate();
                   $('#buscador').focus();
               }
           });       
        }
    };

    $('#buscador').bind('input', buscar);

});


