package npi.betweenstudents;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewFacultad extends AppCompatActivity {

    // Obtener acceso a cualquier sensor de hardware
    SensorManager sensorManager;
    Sensor proximitySensor;
    SensorEventListener proximitySensorListener;

    boolean supervisor = true;

    TextView tv_nombre_facultad;
    TextView tv_ubicacion_facultad;
    TextView tv_horario_facultad;
    TextView tv_campus_facultad;
    TextView tv_web_facultad;
    ImageView iv_imagen_facultad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Se recoge el objeto pasado a través del Intent
        // Es necesario parsearlo ya que al hacer GetSerializable no es capaz de conocer el tipo del objeto
        final Facultad facultad = (Facultad) getIntent().getSerializableExtra("objeto_Facultad");

        setContentView(R.layout.layout_facultad_image);
        iv_imagen_facultad = findViewById(R.id.fac_view_imagen);
        iv_imagen_facultad.setImageResource(facultad.getImagen());

        // Creamos un objeto SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Creamos un objeto Sensor para el sensor de proximidad
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        // Listener que permite leer los datos del sensor de proximidad cada dos segundos:
        proximitySensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                /*
                    El objeto sensorEvent posee un array values que contiene todos los datos
                    generados por el sensor. Al ser de proximidad, contiene un único valor que
                    especifica la distancia en cm entre el sensor y un objeto cercano.
                    Si el valor es igual al alcance máximo del sensor, podemos asumir
                    con seguridad que no hay nada cerca.
                 */

                if(event.values[0] < proximitySensor.getMaximumRange()) {
                    // En esta parte del código implementamos el proposito del sensor -> Detecta el sensor
                    CambiarVista(facultad);
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Especificamos el intervalo de tiempo en microsegundos
        sensorManager.registerListener(proximitySensorListener, proximitySensor, 210001000);

    }

    @Override
    protected void onPause() {
        super.onPause();
        //Eliminamos el registro del listener del sensor de proximidad
        sensorManager.unregisterListener(proximitySensorListener);
    }

    // Cambiar la vista mostrada
    private void CambiarVista(Facultad facultad){
        if(supervisor){
            setContentView(R.layout.layout_facultad);

            tv_nombre_facultad = findViewById(R.id.fac_view_nombre);
            tv_ubicacion_facultad = findViewById(R.id.fac_view_ubicacion);
            tv_horario_facultad = findViewById(R.id.fac_view_horario);
            tv_campus_facultad = findViewById(R.id.fac_view_campus);
            tv_web_facultad = findViewById(R.id.fac_view_web);


            tv_nombre_facultad.setText(facultad.getNombre());
            tv_ubicacion_facultad.setText(facultad.getUbicacion());
            tv_horario_facultad.setText(facultad.getHorario());
            tv_campus_facultad.setText(facultad.getCampus());
            tv_web_facultad.setText(facultad.getPagina_web());

            supervisor = false;
        }else{
            setContentView(R.layout.layout_facultad_image);

            iv_imagen_facultad = findViewById(R.id.fac_view_imagen);

            iv_imagen_facultad.setImageResource(facultad.getImagen());

            supervisor = true;
        }
    }
}
