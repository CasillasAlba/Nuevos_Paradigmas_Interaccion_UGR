package npi.betweenstudents;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ActivityBecas extends AppCompatActivity {

    WebView web;
    WebSettings webSettings;
    CardView ministerio, juntaandalucia, propiasugr;
    // El webview se mantiene en la misma actividad, por tanto a la hora de pulsar "back" vuelve a la activdad principal y no a becas
    // Con el bool se controla que si se está en el navegador, vuelva a su actividad Becas y no al MainActivity
    boolean onWeb = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_becas);

        //Se cargan las cardview que contienen el diseño de cada opcion
        ministerio = findViewById(R.id.cv_becasministerio);
        juntaandalucia = findViewById(R.id.cv_becasjuntaandalucia);
        propiasugr = findViewById(R.id.cv_becaspropiasugr);

        // Se carga la clase contenedor web, que permite visualizar paginas web en la propia app
        web = new WebView(getApplicationContext());

        // Esto permite implementar la compatibilidad con javascript
        // De esta forma las web se visualizarán correctamente
        webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);

        ministerio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onWeb = true;
                setContentView(web);
                web.loadUrl(getString(R.string.url_ministerio));
            }
        });

        juntaandalucia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onWeb = true;
                setContentView(web);
                web.loadUrl(getString(R.string.url_juntaandalucia));
            }
        });

        propiasugr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onWeb = true;
                setContentView(web);
                web.loadUrl(getString(R.string.url_propiasugr));
            }
        });

    }

    @Override
    public void onRestart(){
        super.onRestart();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        if(onWeb){
            onRestart();
        }else{
            Intent principal = new Intent(getApplicationContext(), ActivityInfo.class);
            finish();
            startActivity(principal);
        }

    }

}
