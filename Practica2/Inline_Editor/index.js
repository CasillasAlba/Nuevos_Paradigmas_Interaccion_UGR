'use strict';

const functions = require('firebase-functions');
const admin = require('firebase-admin');
const {WebhookClient} = require('dialogflow-fulfillment');
const {Card, Suggestion, BrowseCarousel, BrowseCarouselItem} = require('dialogflow-fulfillment');
const {dialogflow} = require('actions-on-google');

//Se inicializa la base de datos. Siempre que se quiera llamar a ella habrá que usar la variable admin
admin.initializeApp({
  credential: admin.credential.applicationDefault(),
  databaseURL:'ws://npi-bestbot-rtkv-default-rtdb.firebaseio.com/' 
  // In the databaseURL give your own firebase url
});
process.env.DEBUG = 'dialogflow:debug'; // enables lib debugging statements

exports.dialogflowFirebaseFulfillment = functions.https.onRequest((request, response) => {
  const agent = new WebhookClient({ request, response });
  console.log('Dialogflow Request headers: ' + JSON.stringify(request.headers));
  console.log('Dialogflow Request body: ' + JSON.stringify(request.body));

  function pedirCiudad(agent){
    agent.setContext({'name': 'informacion', 'lifespan': '0'});
    agent.setContext({'name': 'citas', 'lifespan': '0'});
    agent.setContext({'name': 'correo', 'lifespan': '0'});
    agent.setContext({'name': 'cancelacion', 'lifespan': '0'});
    agent.setContext({'name': 'agenda', 'lifespan': '0'});
    const ciudad = agent.parameters.Ciudades;
    var booleana = 0;

    switch (ciudad) {
      case 'Granada':
        booleana = 1;
        break;
      case 'Ceuta':
        booleana = 1;
        break;
      case 'Melilla':
        booleana = 1;
        break;
      default:
        booleana = 2;
    }

    if(booleana === 1){
      var posiblesRespuestas = [
        '¡' + ciudad + ' es muy bonita! Dime en qué Facultad te encuentras',
        '¡Qué suerte vivir en ' + ciudad + '! ¿Y cual es tu facultad?',
        '¡Oh! A mí también me gustaría vivir en ' + ciudad + ' ¿Y dónde estudias?'
      ];

      var pick = Math.floor( Math.random() * posiblesRespuestas.length );

      var respuesta = posiblesRespuestas[pick];
      agent.setContext({'name': 'facultad', 'lifespan': '5'});
      agent.add( respuesta );
    }else{
      agent.add(`¡Jo 😱! Seguro que `+ ciudad + ` es estupenda pero no ofrecemos información de esta ciudad 🥺`);
      agent.setContext({'name': 'facultad', 'lifespan': '0'});
    }   
  }//FIN PEDIR CIUDAD

  function pedirFacultad(agent){
    //Se hace un reset de context por si en otro momento dijese "En Melilla" y luego "donde esta la biblio" y no pueda responder
    agent.setContext({'name': 'citas', 'lifespan': '0'});
    agent.setContext({'name': 'correo', 'lifespan': '0'});
    agent.setContext({'name': 'cancelacion', 'lifespan': '0'});
    agent.setContext({'name': 'agenda', 'lifespan': '0'});
    const ciudad = agent.parameters.Ciudades;
    const facultad = agent.parameters.Facultades;

    return admin.database().ref(ciudad).once("value").then((facultades) => {
      var respuesta = "";
      var booleana = 0;
      facultades.forEach(valor => {
        if(valor.key === facultad){
          booleana = 1;
        }
      });

      if (booleana === 1){
        respuesta = "¡Genial!😊 Puedes gestionar tus citas de la secretaría o preguntarme información sobre la facultad. ¿Qué deseas saber?";
        agent.setContext({'name': 'informacion', 'lifespan': '5'});
      }else{
        respuesta = "Esta facultad no está registrada. ¡Lo siento mucho! 🥶";
        agent.setContext({'name': 'informacion', 'lifespan': '0'});
      }

      agent.add(respuesta);
    });
  }//FIN PEDIR FACULTAD
      
  function informacionFacultad(agent){
    //Aqui la ciudad ya ha sido controlada y solo puede tener un valor correcto
    const ciudad = agent.parameters.Ciudades;
    const facultad = agent.parameters.Facultades;
    const elemento = agent.parameters.ElementosFacultades;
	var respuesta = "";
    
    if (elemento === "Citas"){

      return admin.database().ref(ciudad).child(facultad).child(elemento).orderByValue().equalTo("LIBRE").once("value").then((hijos) => {
        var numHijos = hijos.numChildren();
        if(numHijos === 0) {
          agent.add("No hay citas disponibles");  
        }else{
          respuesta = "HORAS DISPONIBLES";
          hijos.forEach(hora => {
            respuesta = respuesta + "  \n" + hora.key;

          });
          agent.setContext({'name': 'citas', 'lifespan': '5'});
          agent.add(respuesta);
        }
      });

    }if (elemento === "Plantas"){
      return admin.database().ref(ciudad).once("value").then((snapshot) => {
        respuesta = snapshot.child(facultad).child(elemento).val();
        agent.add("Hay " + respuesta + " plantas.");
      });

    }if (elemento === "Horario"){
      return admin.database().ref(ciudad).once("value").then((snapshot) => {
        respuesta = snapshot.child(facultad).child(elemento).val();
        agent.add("Esta facultad esta abierta desde las " + respuesta);
      });
      
    }else{
      return admin.database().ref(ciudad).once("value").then((snapshot) => {
        respuesta = snapshot.child(facultad).child(elemento).val();
        agent.add("Se encuentra en la " + respuesta);

      });
    }
  
  }//FIN INFORMACION FACULTAD

  function pedirCitas(agent){ //No permite avanzar a pedir un correo hasta que la hora introducida es correcta
    const ciudad = agent.parameters.Ciudades;
    const facultad = agent.parameters.Facultades;
    const elemento = agent.parameters.ElementosFacultades;
    const horaElegida = agent.parameters.Citas;

    return admin.database().ref(ciudad).child(facultad).child(elemento).orderByValue().equalTo("LIBRE").once("value").then((hijos) => {
      var booleana = 0;
      hijos.forEach(hora => {
        //Que la hora elegida de verdad esté libre, y no haya puesto otra cosa
        if(horaElegida === hora.key){
          booleana = 1;
        }
      });
      
      if(booleana === 1){
        var posiblesRespuestas = [
          'De acuerdo. Escriba un correo de contacto para proceder al registro',
          'Vale. Dame un e-mail para guardar la cita',
          'Necesito un correo al que hacer la reserva'
        ];

        var pick = Math.floor( Math.random() * posiblesRespuestas.length );

        var respuesta = posiblesRespuestas[pick];
        agent.add(respuesta);

      }else{
        agent.add(`No has elegido una hora que estuviese libre 🧐`);
        agent.setContext({'name': 'correo', 'lifespan': '0'}); //Se elimina el context de correo para que no avance
      }

    });
  }//FIN PEDIR CITAS

  function pedirEmail(agent){
    const ciudad = agent.parameters.Ciudades;
    const facultad = agent.parameters.Facultades;
    const elemento = agent.parameters.ElementosFacultades;
    const hora = agent.parameters.Citas;
    const emailUsuario = agent.parameters.Correo.toLowerCase();
    const cancelaCita = agent.parameters.Cancelacion;
	    
    if (cancelaCita === "cancelacion"){
      return admin.database().ref(ciudad).child(facultad).child(elemento).orderByValue().equalTo(emailUsuario).once("value").then((cancelar) => {
        var h = 0;
        var booleana = 0;
        cancelar.forEach(hora => { //Se busca una hora asociada a ese email
          if(hora.val() === emailUsuario){
            h = hora.key;
            booleana = 1;
          }
        });

        if(booleana === 1){ //Si el correo tiene una cita cogida, entonces la borra
          var cancelar2 = admin.database().ref(ciudad).child(facultad).child(elemento);
          cancelar2.child(h).set("LIBRE");
          agent.add(`Se ha cancelado la cita con e-mail ${emailUsuario}`);
          agent.setContext({'name': 'cancelacion', 'lifespan': '0'});
          agent.setContext({'name': 'citas', 'lifespan': '0'});
          agent.setContext({'name': 'correo', 'lifespan': '0'});
        }else{
          agent.add(`No tienes ninguna cita registrada 😐 `);
          agent.setContext({'name': 'cancelacion', 'lifespan': '0'});
          agent.setContext({'name': 'citas', 'lifespan': '0'});
          agent.setContext({'name': 'correo', 'lifespan': '0'});
        }

      });

    }else{ //Entonces se hace la reserva
      //Se recogen los posibles resultados donde una hora tiene asignada dicho correo
      return admin.database().ref(ciudad).child(facultad).child(elemento).orderByValue().equalTo(emailUsuario).once("value").then((cita) => {
        var citaAsignada = cita.numChildren();
        
        if(citaAsignada === 0){ //Si no tiene hijos, es que no hay ningun correo asociado a una hora
          var reservar = admin.database().ref(ciudad).child(facultad).child(elemento);
          reservar.child(hora).set(emailUsuario);
          agent.add(`Se ha asignado la hora ${hora} al e-mail ${emailUsuario}`); 
          agent.setContext({'name': 'correo', 'lifespan': '0'});
          agent.setContext({'name': 'citas', 'lifespan': '0'});

        }else{

          agent.add(`Ya tienes una cita asignada 😐 `);
          agent.setContext({'name': 'cancelacion', 'lifespan': '0'});
          agent.setContext({'name': 'citas', 'lifespan': '0'});
          agent.setContext({'name': 'correo', 'lifespan': '0'});
        }
      });
    }
  
  }//FIN PEDIR EMAIL

  function mostrarAgenda(agent){
    const ciudad = agent.parameters.Ciudades;
    const facultad = agent.parameters.Facultades;
    const elemento = agent.parameters.ElementosFacultades;
    const emailUsuario = agent.parameters.Correo;
    return admin.database().ref(ciudad).child(facultad).child(elemento).orderByValue().equalTo(emailUsuario).once("value").then((agenda) => {
      var h = 0;
      var booleana = 0;
      agenda.forEach(hora => { //Se busca una hora asociada a ese email
        if(hora.val() === emailUsuario){
          h = hora.key;
          booleana = 1;
        }
      });

      if(booleana === 1){ //Si el correo tiene una cita cogida, entonces la muestra        
        agent.add(`Tiene cita a las ${h}`);
        agent.setContext({'name': 'agenda', 'lifespan': '0'});
      }else{
        agent.add(`No tienes ninguna cita registrada😫. Puedo ayudarle a registrar una cita`);
        agent.setContext({'name': 'agenda', 'lifespan': '0'});

      }

    });
  }//FIN FUNCION MOSTRAR AGENDA

  function despedir(agent){
    var posiblesRespuestas = [
      'Espero que pase un buen día',
      'Espero haberle ayudado',
      'Nos vemos pronto',
      'Hasta luego, cuidate',
      'Espero haber sido de ayuda',
      'Si necesita cualquier otra cosa, aquí estoy'
    ];

    var pick = Math.floor( Math.random() * posiblesRespuestas.length );

    var respuesta = posiblesRespuestas[pick];
    // Se borran todos los context que pudiera haber creados
    agent.setContext({'name': 'ciudad', 'lifespan': '0'});
    agent.setContext({'name': 'facultad', 'lifespan': '0'});
    agent.setContext({'name': 'informacion', 'lifespan': '0'});
    agent.setContext({'name': 'citas', 'lifespan': '0'});
    agent.setContext({'name': 'correo', 'lifespan': '0'});
    agent.setContext({'name': 'cancelacion', 'lifespan': '0'});
    agent.setContext({'name': 'agenda', 'lifespan': '0'});
    agent.add(respuesta);
  
  }//FIN FUNCION DESPEDIR
  
  // Run the proper function handler based on the matched Dialogflow intent name
  let intentMap = new Map();
  intentMap.set('PedirCiudad', pedirCiudad);
  intentMap.set('PedirFacultad', pedirFacultad);
  intentMap.set('InformacionFacultad', informacionFacultad);
  intentMap.set('PedirCitas', pedirCitas);
  intentMap.set('PedirEmail', pedirEmail);
  intentMap.set('MostrarAgenda', mostrarAgenda);
  intentMap.set('Despedida', despedir);
  agent.handleRequest(intentMap);
  
});