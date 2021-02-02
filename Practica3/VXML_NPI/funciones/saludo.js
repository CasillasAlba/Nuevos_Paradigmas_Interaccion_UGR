function saludo(){
    var date = new Date();
    var time = date.getHours();

    if (time < 12) {
        return "Buenos dias ";
    }
    if (time < 21) {
        return "Buenas tardes ";
    }
    if (time >= 21) {
        return "Buenas noches ";
    }
}