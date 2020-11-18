package npi.betweenstudents;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.webkit.URLUtil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

public class ActivityQR extends AppCompatActivity {
    BarcodeDetector barcodeDetector;
    SparseArray<Barcode> barcodes;
    CameraSource cameraSource;
    SurfaceView cameraView;
    private final int PERMISSIONS_REQUEST_CAMERA = 0;
    private String token = "";
    private String tokenanterior = "";
    View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        layout = findViewById(R.id.qr_layout);


        // Se identifica la surfaceView creada en el Layout (que se usará como el visor de lo que la camara ve)
        cameraView = (SurfaceView) findViewById(R.id.qr_view);

        // Detector QR
        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build();

        // Camara
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setAutoFocusEnabled(true).build();

        CheckPermissions(); //De haber obtenido ya los permisos se inicia la camara con la funcionalidad de lector QR

    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSIONS_REQUEST_CAMERA) {
            // Se pregunta por los permisos
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Los permisos fueron concedidos, se inicia la actividad
                Snackbar.make(layout, R.string.camera_permission_granted, Snackbar.LENGTH_SHORT).show();
                onRestart();
            } else {
                // Los permisos fueron denegados
                Snackbar.make(layout, R.string.camera_permission_denied, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void CheckPermissions(){

        // Comprueba si los permisos fueron garantizados para la App
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // Los permisos ya fueron concedidos, inicia la Actividad
            Snackbar.make(layout, R.string.camera_permission_available, Snackbar.LENGTH_SHORT).show();
            starQR();

        } else {
            // No hay permisos por lo que deben ser pedidos
            requestCameraPermission();
        }

    }

    // Se piden los permisos necesarios para usar la Camara
    private void requestCameraPermission() {
        // Los permisos no fueron garantizados y es obligatorio pedirlos
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

            // Muestra en la misma pantalla un pequeño Dialog para pedir los permisos
            Snackbar.make(layout, R.string.camera_access_required,
                    Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Solicita el aceptar los permisos
                    ActivityCompat.requestPermissions(ActivityQR.this,
                            new String[]{Manifest.permission.CAMERA},
                            PERMISSIONS_REQUEST_CAMERA);
                }
            }).show();

        } else {
            Snackbar.make(layout, R.string.camera_unavailable, Snackbar.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSIONS_REQUEST_CAMERA);
        }
    }

    private void listenerCameraView(){
        // Listener de la Camara (Recoge la funcionalidad)
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                // A partir de Android 6 hay que comprobar de esta forma si los permisos fueron concedidos
                if(ActivityCompat.checkSelfPermission(ActivityQR.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    // Los permisos ya han sido autorizados
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException ie) {
                        Log.e("CAMERA SOURCE", ie.getMessage());
                    }
                }else{ // Los permisos no fueron garantizados
                    requestCameraPermission();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
    }

    // Activacion del Detector QR
    private void setQRDetector(){
        // Activación del Detector QR
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {}

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                barcodes = detections.getDetectedItems();

                if (barcodes.size() > 0) { //Entonces ha leido algo
                    // Se toma el token (Texto, URL, etc)
                    token = barcodes.valueAt(0).displayValue;

                    // Si el token tomado es identico al anterior no se hace nada evitando llamadas repetidas
                    if (!token.equals(tokenanterior)) {

                        // Se actualiza el valor anterior
                        tokenanterior = token;

                        // Se comprueba si lo analizado es una URL
                        if (URLUtil.isValidUrl(token)) {
                            //De serlo, abre el navegador
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(token));
                            startActivity(browserIntent);
                        } else {
                            // Intenta abrirlo en otra app
                            Intent shareIntent = new Intent();
                            shareIntent.setAction(Intent.ACTION_SEND);
                            shareIntent.putExtra(Intent.EXTRA_TEXT, token);
                            shareIntent.setType("text/plain");
                            startActivity(shareIntent);
                        }

                        new Thread(new Runnable() { // Con esto, la ACtividy se mantiene a la espera hasta que lee algo
                            public void run() {
                                try {
                                    synchronized (this) {
                                        wait(5000);
                                        // limpiamos el token
                                        tokenanterior = "";
                                    }
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    Log.e("Error", "Waiting didnt work!!");
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                    }
                }
            }
        });
    }

    private void starQR(){
        // Se inicia el Listener de la Camara (permite visualizar y tomar datos desde la camara
        listenerCameraView();

        // Se activa del Detector QR
        setQRDetector();
    }

}
