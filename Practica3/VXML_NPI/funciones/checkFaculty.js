function checkFaculty(fac){
    
    var entityFacultades = ['it', 'arts', 'medicine'];
    var respuesta = "";
    // Si el index es -1 quiere decir que no se se corresponde con ningun valor de la Entity
    if (entityFacultades.indexOf(fac) === -1) {
        // La ciudad NO es correcta
        respuesta = 0;
    
    }else{
        // La ciudad ES correcta
        respuesta = 1
    }
    
    return respuesta;
}