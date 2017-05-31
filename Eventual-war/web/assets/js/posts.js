   
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
 
 /**
  * Función para los "me gusta"
  */
 function redifinirEscuchaMeGustas() {
  $('.meGusta').on('click', function() {
     
     var idUsuario = $('#ID_USUARIO').text();
     var idPost = $(this).closest('.post').attr('id');
     
     // Si ya esta pulsado es que se quiere quitar
     if ($(this).hasClass('text-green')) {
         
        var data = {
          'tipo': 'quitar_megusta',
          'usuario': idUsuario,
          'post': idPost
        };

        $(this).removeClass('text-green');
     } else { // Si no, publicar me gusta
         
        var data = {
           'tipo': 'poner_megusta',
           'usuario': idUsuario,
           'post': idPost
         };

        $(this).addClass('text-green');
     }
     
    $.ajax({
        type: "POST",
        url: "./Social",
        data: data,
        success: function() {
            
        },
        error: function(xhr, ajaxOptions, thrownError) {
             alert(xhr.responseText);
        }
    });
     
 });    
 }

redifinirEscuchaMeGustas();
 
function quitarMeGusta(idPost) {
     
     var post = $('#' + idPost);
     var megustas = parseInt(post.find('.meGusta').attr('value'));
     megustas = megustas - 1;
     post.find('.meGusta').attr('value', megustas);
     if (megustas !== 0) {
        post.find('.meGusta').html(' <i class="fa fa-thumbs-o-up margin-r-5"></i> Me gusta ('+ megustas +')');         
     } else {
        post.find('.meGusta').html(' <i class="fa fa-thumbs-o-up margin-r-5"></i> Me gusta');         
     }
    post.find('.meGusta').addClass('animated rubberBand');
}

function añadirMeGusta(idPost) {
     var post = $('#' + idPost);
     var megustas = parseInt(post.find('.meGusta').attr('value'));
     megustas = megustas + 1;
     post.find('.meGusta').attr("value", megustas);
     if (megustas !== 0) {
        post.find('.meGusta').html(' <i class="fa fa-thumbs-o-up margin-r-5"></i> Me gusta ('+ megustas +')');         
     } else {
        post.find('.meGusta').html(' <i class="fa fa-thumbs-o-up margin-r-5"></i> Me gusta');         
     }
    post.find('.meGusta').addClass('animated rubberBand');
}