mostrarMensajeDeError = function(message) {
    jQuery('<div />', {'class' : 'rf-p-hdr'})
    .prependTo('#mensajesDeError')
    .text(message);
    //.fadeOut(3000, function() {
    //    $(this).remove();
    //}
	//);
};