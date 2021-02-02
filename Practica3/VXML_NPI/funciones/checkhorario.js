function checkHorario(hor){
    
    var entityHora = ['nine', 'half past nine', 'nine o clock', 'ten', 'ten past nine', 'ten o clock', 'eleven', 'half past eleven', 'eleven o clock', 'twelve', 'half past twelve', 'nine o twelve'];
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