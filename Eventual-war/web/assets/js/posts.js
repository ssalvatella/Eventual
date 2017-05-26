            
$(document).on('click','#boton_publicar',function(){
    $.ajax({
        type: "POST",
        url: "./Social",
        data: {
            'usuario' : $('#ID_USUARIO').text(),
            'contenido' : $('#texto_post').val()
        },
        success: function() {
              window.location.href = "./Social";
        },
        error: function(xhr, ajaxOptions, thrownError) {
             alert(xhr.responseText);
        }
    });
 });