function comprobarHorario(hor){
    
    var entityHora = ['nueve', 'nueve y media', 'diez' , 'diez y media' , 'once', 'once y media', 'doce', 'doce y media'];
    var respuesta = "";
    // Si el index es -1 quiere decir que no se se corresponde con ningun valor de la Entity
    if (entityHora.indexOf(hor) === -1) {
        // La ciudad NO es correcta
        respuesta = 0;
    
    }else{
        // La ciudad ES correcta
        respuesta = 1
    }
    
    return respuesta;
}