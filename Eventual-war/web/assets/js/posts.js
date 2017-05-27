   
/**
 * Realiza la publicación del post en el sistema
 * @type type
 */
$(document).on('click','#boton_publicar',function(){
    $.ajax({
        type: "POST",
        url: "./Social",
        data: {
            'usuario' : $('#ID_USUARIO').text(),
            'contenido' : $('#texto_post').val()
        },
        success: function() {
            $('iframe').contents().find('.wysihtml5-editor').html('');
            new Noty({
                timeout: 2000,
                layout: 'bottomRight',
                theme: 'metroui',
                type: 'success',
                progressBar: true,
                text: "Publicación hecha con éxito!"
            }).show();
        },
        error: function(xhr, ajaxOptions, thrownError) {
             alert(xhr.responseText);
        }
    });
 });