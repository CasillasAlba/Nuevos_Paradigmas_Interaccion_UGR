package npi.betweenstudents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import java.sql.SQLOutput;

public class ActivityMaps extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    //Hace que el mapa aparezca centrado en el mismo punto en el que se establecio el Marker
    private CameraUpdate camara;

    SupportMapFragment mapFragment;
    FusedLocationProviderClient client;

    double latitudeInicial = 37.1813504;
    double longitudeInicial = -3.5966409;
    int profundidad = 12;

    private final int PERMISSIONS_REQUEST_MAPS = 0;
    private final int LOCATION_REQUEST = 500;
    View layout;

    boolean locationPermission = false;


    private Marker mCampusFuentenueva;
    private Marker mCampusCartuja;
    private Marker mCampusCentro;
    private Marker mCampusAynadamar;
    private Marker mCampusPTS;
    private Marker mCampusMelilla;
    private Marker mCampusCeuta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        layout = findViewById(R.id.google_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);

        String mapaSelect = getIntent().getStringExtra("mapa");

        switch (mapaSelect) {
            case "Ceuta":
                latitudeInicial = 35.890377866285235;
                longitudeInicial = -5.296596134510538;
                profundidad = 15;
                break;
            case "Melilla":
                latitudeInicial = 35.289622085649135;
                longitudeInicial = -2.951885501240261;
                profundidad = 15;
                break;
            default:
                latitudeInicial = 37.1813504;
                longitudeInicial = -3.5966409;
                break;
        }


        CheckPermissionsMaps();

        client = LocationServices.getFusedLocationProviderClient(this);

        mapFragment.getMapAsync(this);

    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Intent reinicio = getIntent();
        finish();
        startActivity(reinicio);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMarkerClickListener(this);

        StarMap();

    }

    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

    @Override
    public void onBackPressed(){

        if(back_pressed + TIME_DELAY > System.currentTimeMillis()){
            super.onBackPressed();

        }else{
            mMap.clear();
            IniciarCampus();

        }

        back_pressed = System.currentTimeMillis();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSIONS_REQUEST_MAPS) {
            // Se pregunta por los permisos
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Los permisos fueron concedidos, se inicia la actividad
                Snackbar.make(layout, R.string.location_permission_granted, Snackbar.LENGTH_SHORT).show();
                getCurrentLocation();
            } else {
                // Los permisos fueron denegados
                Snackbar.make(layout, R.string.location_permission_denied, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void CheckPermissionsMaps(){

        // Comprueba si los permisos fueron garantizados para la App para usar Maps
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Los permisos ya fueron concedidos, inicia la Actividad
            Snackbar.make(layout, R.string.location_permission_available, Snackbar.LENGTH_SHORT).show();
            mapFragment.getMapAsync(this); // Esto llama al onMapReady

        } else {
            // No hay permisos por lo que deben ser pedidos
            requestMapsPermission();
        }
    }

    // Se piden los permisos necesarios para usar la Camara
    private void requestMapsPermission() {
        // Los permisos no fueron garantizados y es obligatorio pedirlos
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {

            // Muestra en la misma pantalla un pequeño Dialog para pedir los permisos
            Snackbar.make(layout, R.string.location_access_required,
                    Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Solicita el aceptar los permisos
                    ActivityCompat.requestPermissions(ActivityMaps.this,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            PERMISSIONS_REQUEST_MAPS);
                }
            }).show();

        } else {
            Snackbar.make(layout, R.string.location_unavailable, Snackbar.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_REQUEST_MAPS);
        }
    }

    private void StarMap() {
        // Tipo de Mapa
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);

        // Control de los gestos
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.isBuildingsEnabled();

        //Es necesario comprobar en primer lugar si los permisos de localizacion estan concedidos
        if (ActivityCompat.checkSelfPermission
                (this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Aparece la brujula cuando giramos el mapa
        mMap.getUiSettings().setCompassEnabled(true);

        // Establece un punto inicial
        LatLng lugar = new LatLng(latitudeInicial, longitudeInicial);
        camara = CameraUpdateFactory.newLatLngZoom(lugar, profundidad);
        mMap.moveCamera(camara);

        // Coloca en el mapa unos atajos a las posiciones de la Universidades
        //IniciarMarcas();

        //Coloca area de campus
        IniciarCampus();

    }

    private void IniciarCampus () {

        final LatLng campus_centro = new LatLng(37.179537, -3.603082);
        mMap.addCircle(
                new CircleOptions().center(campus_centro).radius(300.0)
                        .strokeWidth(3f).strokeColor(Color.GREEN)
                        .fillColor(Color.argb(70, 45, 250, 200))
        );
        mCampusCentro = mMap.addMarker(new MarkerOptions().position(campus_centro).title("Campus Centro").icon(BitmapDescriptorFactory.fromResource(R.drawable.campus)));
        mCampusCentro.setTag(0);

        final LatLng campus_cartuja = new LatLng(37.193501, -3.596922);
        mMap.addCircle(
                new CircleOptions().center(campus_cartuja).radius(500.0)
                        .strokeWidth(3f).strokeColor(Color.GREEN)
                        .fillColor(Color.argb(70, 45, 250, 200))
        );
        mCampusCartuja = mMap.addMarker(new MarkerOptions().position(campus_cartuja).title("Campus Cartuja").icon(BitmapDescriptorFactory.fromResource(R.drawable.campus)));
        mCampusCartuja.setTag(0);

        final LatLng campus_fuentenueva = new LatLng(37.180758, -3.608952);
        mMap.addCircle(
                new CircleOptions().center(campus_fuentenueva).radius(200.0)
                        .strokeWidth(3f).strokeColor(Color.GREEN)
                        .fillColor(Color.argb(70, 45, 250, 200))
        );
        mCampusFuentenueva = mMap.addMarker(new MarkerOptions().position(campus_fuentenueva).title("Campus Fuentenueva").icon(BitmapDescriptorFactory.fromResource(R.drawable.campus)));
        mCampusFuentenueva.setTag(0);

        final LatLng campus_aynadamar = new LatLng(37.196439, -3.625597);
        mMap.addCircle(
                new CircleOptions().center(campus_aynadamar).radius(200.0)
                        .strokeWidth(3f).strokeColor(Color.GREEN)
                        .fillColor(Color.argb(70, 45, 250, 200))
        );
        mCampusAynadamar = mMap.addMarker(new MarkerOptions().position(campus_aynadamar).title("Campus Aynadamar").icon(BitmapDescriptorFactory.fromResource(R.drawable.campus)));
        mCampusAynadamar.setTag(0);

        final LatLng campus_pts = new LatLng(37.148302, -3.604420);
        mMap.addCircle(
                new CircleOptions().center(campus_pts).radius(200.0)
                        .strokeWidth(3f).strokeColor(Color.GREEN)
                        .fillColor(Color.argb(70, 45, 250, 200))
        );
        mCampusPTS = mMap.addMarker(new MarkerOptions().position(campus_pts).title("Campus PTS").icon(BitmapDescriptorFactory.fromResource(R.drawable.campus)));
        mCampusPTS.setTag(0);

        final LatLng campus_ceuta = new LatLng(35.890722, -5.298554);
        mMap.addCircle(
                new CircleOptions().center(campus_ceuta).radius(200.0)
                        .strokeWidth(3f).strokeColor(Color.GREEN)
                        .fillColor(Color.argb(70, 45, 250, 200))
        );
        mCampusCeuta = mMap.addMarker(new MarkerOptions().position(campus_ceuta).title("Campus Ceuta").icon(BitmapDescriptorFactory.fromResource(R.drawable.campus)));
        mCampusCeuta.setTag(0);

        final LatLng campus_melilla = new LatLng(35.289627, -2.952693);
        mMap.addCircle(
                new CircleOptions().center(campus_melilla).radius(200.0)
                        .strokeWidth(3f).strokeColor(Color.GREEN)
                        .fillColor(Color.argb(70, 45, 250, 200))
        );
        mCampusMelilla = mMap.addMarker(new MarkerOptions().position(campus_melilla).title("Campus Melilla").icon(BitmapDescriptorFactory.fromResource(R.drawable.campus)));
        mCampusMelilla.setTag(0);

    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                if (location != null) {
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            MarkerOptions options = new MarkerOptions().position(latLng).title("Estoy aqui");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                            googleMap.addMarker(options);
                        }
                    });
                }
            }
        });

        //get destination location when user click on map

    }


    private void IniciarCampusCentro(){
        LatLng derecho = new LatLng(37.178139, -3.602);
        mMap.addMarker(new MarkerOptions().position(derecho).title(getString(R.string.f_derecho)).icon(BitmapDescriptorFactory.fromResource(R.drawable.derecho_icon)));

        LatLng trabajo = new LatLng(37.180334, -3.604475);
        mMap.addMarker(new MarkerOptions().position(trabajo).title(getString(R.string.f_trabajo)).icon(BitmapDescriptorFactory.fromResource(R.drawable.trabajo)));

        LatLng cs_politicas = new LatLng(37.180595, -3.604367);
        mMap.addMarker(new MarkerOptions().position(cs_politicas).title(getString(R.string.f_politicas)).icon(BitmapDescriptorFactory.fromResource(R.drawable.politicas)));

        LatLng arquitectura = new LatLng(37.172863, -3.591304);
        mMap.addMarker(new MarkerOptions().position(arquitectura).title(getString(R.string.f_arquitectura)).icon(BitmapDescriptorFactory.fromResource(R.drawable.arquitectura)));

        LatLng trabajo_social = new LatLng(37.180101, -3.604329);
        mMap.addMarker(new MarkerOptions().position(trabajo_social).title(getString(R.string.f_trabajosocial)).icon(BitmapDescriptorFactory.fromResource(R.drawable.trabajo)));

        LatLng traduccion = new LatLng(37.175441, -3.603583);
        mMap.addMarker(new MarkerOptions().position(traduccion).title(getString(R.string.f_traduccion)).icon(BitmapDescriptorFactory.fromResource(R.drawable.traduccion)));


    }

    private void IniciarCampusCartuja(){
        LatLng educacion = new LatLng(37.193141, -3.599666);
        mMap.addMarker(new MarkerOptions().position(educacion).title(getString(R.string.f_educacion)).icon(BitmapDescriptorFactory.fromResource(R.drawable.educacion_icon)));

        LatLng farmacia = new LatLng(37.194926, -3.596407);
        mMap.addMarker(new MarkerOptions().position(farmacia).title(getString(R.string.f_farmacia)).icon(BitmapDescriptorFactory.fromResource(R.drawable.farmacia_icon)));

        LatLng comunicacion = new LatLng(37.193182, -3.596504);
        mMap.addMarker(new MarkerOptions().position(comunicacion).title(getString(R.string.f_comunicacion)).icon(BitmapDescriptorFactory.fromResource(R.drawable.documentacion_icon)));

        LatLng filo_letras = new LatLng(37.191003, -3.595581);
        mMap.addMarker(new MarkerOptions().position(filo_letras).title(getString(R.string.f_filosofia)).icon(BitmapDescriptorFactory.fromResource(R.drawable.filosofia_icon)));

        LatLng psico = new LatLng(37.194328, -3.594304);
        mMap.addMarker(new MarkerOptions().position(psico).title(getString(R.string.f_psicologia)).icon(BitmapDescriptorFactory.fromResource(R.drawable.psico_icon)));

        LatLng economicas = new LatLng(37.192217, -3.594540);
        mMap.addMarker(new MarkerOptions().position(economicas).title(getString(R.string.f_economia)).icon(BitmapDescriptorFactory.fromResource(R.drawable.economia_icon)));

        LatLng cs_deporte = new LatLng(37.205061, -3.597239);
        mMap.addMarker(new MarkerOptions().position(cs_deporte).title(getString(R.string.f_deporte)).icon(BitmapDescriptorFactory.fromResource(R.drawable.deporte)));

        LatLng odontologia = new LatLng(37.205061, -3.597239);
        mMap.addMarker(new MarkerOptions().position(odontologia).title(getString(R.string.f_odontologia)).icon(BitmapDescriptorFactory.fromResource(R.drawable.odon)));

    }

    private void IniciarCampusFuentenueva(){
        LatLng ciencias = new LatLng(37.179694, -3.609667);
        mMap.addMarker(new MarkerOptions().position(ciencias).title(getString(R.string.f_ciencias)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ciencias_icon)));

        LatLng caminos = new LatLng(37.181333, -3.608028);
        mMap.addMarker(new MarkerOptions().position(caminos).title(getString(R.string.f_caminos)).icon(BitmapDescriptorFactory.fromResource(R.drawable.caminos_icon)));

        LatLng edificacion = new LatLng(37.18125, -3.607028);
        mMap.addMarker(new MarkerOptions().position(edificacion).title(getString(R.string.f_edificacion)).icon(BitmapDescriptorFactory.fromResource(R.drawable.edificacion_icon)));

    }

    private void IniciarCampusAynadamar(){
        LatLng bellas_artes = new LatLng(37.195472, -3.626694);
        mMap.addMarker(new MarkerOptions().position(bellas_artes).title(getString(R.string.f_bellasasrtes)).icon(BitmapDescriptorFactory.fromResource(R.drawable.bellasartes_icon)));

        LatLng etsiit = new LatLng(37.197161, -3.624341);
        mMap.addMarker(new MarkerOptions().position(etsiit).title(getString(R.string.f_informatica)).icon(BitmapDescriptorFactory.fromResource(R.drawable.etsiit_icon)));

    }

    private void IniciarCampusPTS(){
        LatLng medicina = new LatLng(37.148472, -3.605194);
        mMap.addMarker(new MarkerOptions().position(medicina).title(getString(R.string.f_medicina)).icon(BitmapDescriptorFactory.fromResource(R.drawable.medicina_icon)));

        LatLng cs_salud = new LatLng(37.148994, -3.604434);
        mMap.addMarker(new MarkerOptions().position(cs_salud).title(getString(R.string.f_salud)).icon(BitmapDescriptorFactory.fromResource(R.drawable.medicina_icon)));

    }

    private void IniciarCampusCeuta(){
        LatLng educanomiatecnoCeuta = new LatLng(35.890458731822214, -5.2994904813840495);
        mMap.addMarker(new MarkerOptions().position(educanomiatecnoCeuta).title(getString(R.string.f_ceuta_educaeconomtecno)).icon(BitmapDescriptorFactory.fromResource(R.drawable.economia_icon)));

        LatLng saludCeuta = new LatLng(35.89039734743646, -5.298176387341509);
        mMap.addMarker(new MarkerOptions().position(saludCeuta).title(getString(R.string.f_ceuta_salud)).icon(BitmapDescriptorFactory.fromResource(R.drawable.medicina_icon)));
    }

    private void IniciarCampusMelilla(){
        LatLng socialesjuridicasMelilla = new LatLng(35.28966542252381, -2.9530317718171184);
        mMap.addMarker(new MarkerOptions().position(socialesjuridicasMelilla).title(getString(R.string.f_melilla_socialesjuridicas)).icon(BitmapDescriptorFactory.fromResource(R.drawable.economia_icon)));

        LatLng saludMelilla = new LatLng(35.29009890892716, -2.952739411034495);
        mMap.addMarker(new MarkerOptions().position(saludMelilla).title(getString(R.string.f_melilla_salud)).icon(BitmapDescriptorFactory.fromResource(R.drawable.medicina_icon)));

        LatLng deporteMelilla = new LatLng(35.28989311264125, -2.952245884575755);
        mMap.addMarker(new MarkerOptions().position(deporteMelilla).title(getString(R.string.f_melilla_deporte)).icon(BitmapDescriptorFactory.fromResource(R.drawable.deporte)));

    }


    @Override
    public boolean onMarkerClick(  Marker marker) {
        Integer clickCount = (Integer) marker.getTag();

        if (clickCount != null && marker.equals(mCampusCentro)) {
            IniciarCampusCentro();
        } else if (clickCount != null && marker.equals(mCampusCartuja)) {
            IniciarCampusCartuja();
        } else if (clickCount != null && marker.equals(mCampusFuentenueva)) {
            IniciarCampusFuentenueva();
        } else if (clickCount != null && marker.equals(mCampusAynadamar)) {
            IniciarCampusAynadamar();
        } else if (clickCount != null && marker.equals(mCampusCeuta)) {
        IniciarCampusCeuta();
        } else if (clickCount != null && marker.equals(mCampusMelilla)) {
        IniciarCampusMelilla();
        }else if (clickCount != null && marker.equals(mCampusPTS)) {
            IniciarCampusPTS();
        }


        return false;
    }



}