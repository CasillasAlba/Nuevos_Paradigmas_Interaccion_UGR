package npi.betweenstudents;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;


public class ActivityMain extends AppCompatActivity implements GestureDetector.OnGestureListener, View.OnTouchListener {

    private RecyclerView rvCampus;
    private LinearLayoutManager rvLinear;

    AdapterCampus adapter;
    ArrayList<Campus> campus;

    // Se recoge la CardView para darle la funcionalidad de ONCLICK
    CardView cv_info;

    Fragment search;
    LinearLayout linearL;

    // Botones
    FloatingActionButton fabQR;
    FloatingActionButton fabMaps;
    ImageView bInfo;

    // Se recogen los gestos en la pantalla
    GestureDetector gestureScanner;

    SensorManager sensorManager;
    Sensor gyroscopeSensor;
    SensorEventListener gyroscopeSensorListener;

    //para sensor interrogacion
    private int corx, cory;
    private Lienzo fondo;

    // Se inicia la Base de Datos que contiene la información de la app
    // Static para poder usarla en cualquier Activity con los datos cargados
    protected static BaseDatos database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestureScanner = new GestureDetector(this);

        // Sensor de Giroscopio
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        findViewById(R.id.principal).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event){
                return gestureScanner.onTouchEvent(event);
            }
        });

        linearL = findViewById(R.id.principal);

        // Fuerza la orientación en vertical
        setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Se inicializa el objeto de la Base de Datos en SQLite que será usada por toda la aplicacion
        database = new BaseDatos(this);

        // Botón para abrir el QR
        fabQR = findViewById(R.id.fb_qr);
        fabQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start the qr scan activity
                Intent qr = new Intent(getApplicationContext(), ActivityQR.class);
                startActivity(qr);
            }
        });

        // Botón para abrir el MAPS
        fabMaps = findViewById(R.id.fb_map);
        fabMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent map = new Intent(getApplicationContext(), ActivityMaps.class);
                map.putExtra("mapa", getString(R.string.map_granada));
                startActivity(map);
            }
        });

        fabMaps.setOnLongClickListener(new View.OnLongClickListener() {
            Intent seleccionado = new Intent(getApplicationContext(), ActivityMaps.class);

            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(ActivityMain.this)
                        .setTitle(getResources().getString(R.string.map_seleccion))
                        .setPositiveButton(getResources().getString(R.string.map_melilla), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                seleccionado.putExtra("mapa", getString(R.string.map_melilla));

                                startActivity(seleccionado);

                            }

                        }).setNegativeButton(getResources().getString(R.string.map_ceuta), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                seleccionado.putExtra("mapa", getString(R.string.map_ceuta));

                                startActivity(seleccionado);

                            }
                        }).show();

                return true;
            }

        });

        bInfo = findViewById(R.id.b_info);
        bInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Lo primero es cambiar el valor en las propiedad de la bandera que indica si fue abierta o no
                SharedPreferences.Editor editor=getSharedPreferences("slide", MODE_PRIVATE).edit();
                editor.putBoolean("slide", false);
                editor.apply();

                // Llamará a la siguiente actividad
                Intent info = new Intent().setClass(getApplicationContext(), ActivitySlide.class);
                startActivity(info);

            /* Se cerrará la actividad haciendo imposible que el usuario
                pueda volver a esta pulsando el botón de volver*/
                finish();
            }
        });


        // Se recoge el RecyclerView del XML
        rvCampus = findViewById(R.id.rview_campus);
        rvLinear = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        InicializarCampus();

        cv_info = findViewById(R.id.cv_informacion);

        cv_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info = new Intent(getApplicationContext(), ActivityInfo.class);
                startActivity(info);
            }
        });

        /*
        * Debemos asegurar de que al registrar el listener, la frecuencia de muestreo sea muy alta.
        * Por lo tanto, el lugar de especificar un intervalo de sondeo en microsegundos,
        * usaremos la constante SENSOR_DELAY_NORMAL
        * */
        gyroscopeSensorListener = new SensorEventListener(){
            @Override
            public void onSensorChanged(SensorEvent sensorEvent){
                /*
                 * Los datos del sensor consisten en tres valores float, que especifican la velocidad angular del dispositivo
                 * sobre los ejes X Y Z. La Unidad de estos tres valores es radianes/segundo.
                 * Rotación en sentido antihorario = Valor Positivo (De 12 hacia atrás, de derecha a izquierda)
                 * Rotación en sentido horario = Valor Negativo
                 * */
                //Como solo estamos interesados en la rotación en el EJE Z, se usa el tercer elemento
                if(sensorEvent.values[2] > 2.5f){
                    onBackPressed();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i){

            }
        };

    }

    // El menú se gestionará en el Fragment que es donde se quiere usar
    @Override
    public void onBackPressed() {
        SalirAplicacion();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroscopeSensorListener, gyroscopeSensor, 210001000);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(gyroscopeSensorListener);

    }

    // Metodos Auxiliares
    private void InicializarCampus(){

        campus = database.ReadCAMPUS();

        adapter = new AdapterCampus(campus);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se recoge el objeto en cuestion en la posicion en la que se clickó
                Campus cm = campus.get(rvCampus.getChildAdapterPosition(v));
                // Crea un intent con el campus seleccionado y la envía a FacultadesActivity para mostrarlas todas
                Intent detallar = new Intent (getApplicationContext(), ActivityFacultades.class);
                //Se añade un contenido extra al Intent, el link de la noticia
                detallar.putExtra("campusSeleccionado", cm.getNombre());
                //Se inicializa
                startActivity(detallar);
            }
        });

        rvCampus.setAdapter(adapter);
        rvCampus.setLayoutManager(rvLinear);
        rvCampus.setPadding(50,0,50,0);
    }

    protected void SalirAplicacion(){
        new AlertDialog.Builder(ActivityMain.this)
                .setIcon(R.drawable.ic_baseline_exit_to_app_24)
                .setTitle(getResources().getString(R.string.app_cerrar))
                .setMessage(getResources().getString(R.string.cerrar_mensaje))
                .setPositiveButton(getResources().getString(R.string.aceptar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent salir = new Intent(getApplicationContext(), ActivityExit.class);
                        salir.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(salir);
                    }

                }).setNegativeButton(getResources().getString(R.string.cancelar), null).show();
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }


    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Intent info = new Intent(getApplicationContext(), FragmentSearchView.class);
        startActivity(info);

        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        corx = (int) event.getX();
        cory = (int) event.getY();
        fondo.invalidate();
        return true;
    }

    class Lienzo extends View {

        public Lienzo(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {
            canvas.drawRGB(255, 255, 0);
            Paint pincel1 = new Paint();
            pincel1.setARGB(255, 255, 0, 0);
            pincel1.setStrokeWidth(4);
            pincel1.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(corx, cory, 20, pincel1);
        }
    }

}