package npi.betweenstudents;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {


    // Establece la duración del Splash, la pantalla de carga o bienvenida
    private static final long SPLASH_SCREEN_DELAY = 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Fuerza la orientación en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Esconde la barra de títutlo
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        TimerTask task = new TimerTask(){
            @Override
            public void run(){
                // Llamará a la siguiente actividad
                Intent empezar = new Intent().setClass(getApplicationContext(), ActivitySlide.class);
                startActivity(empezar);

            /* Se cerrará la actividad haciendo imposible que el usuario
                pueda volver a esta pulsando el botón de volver*/
                finish();
            }
        };

        // Simula una pantalla de carga al arrancar la aplicación
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);

    }
}
