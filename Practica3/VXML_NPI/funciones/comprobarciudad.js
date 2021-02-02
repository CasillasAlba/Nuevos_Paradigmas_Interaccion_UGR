function comprobarCiudad(ciu){
    
    var entityCiudades = ['granada', 'ceuta', 'melilla'];
    var respuesta = "";
    // Si el index es -1 quiere decir que no se se corresponde con ningun valor de la Entity
    if (entityCiudades.indexOf(ciu) === -1) {
        // La ciudad NO es correcta
        respuesta = 0;
    
    }else{
        // La ciudad ES correcta
        respuesta = 1
    }
    
    return respuesta;
}