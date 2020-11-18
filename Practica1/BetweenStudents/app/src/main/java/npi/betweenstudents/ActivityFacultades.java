package npi.betweenstudents;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActivityFacultades extends AppCompatActivity {

    private ArrayList<Facultad> listaFacultades;
    private AdapterFacultades adapter;
    private RecyclerView rvFacultades;

    Facultad f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facultades);

        //Se recoge el RecyclerView del Layout de la activity
        rvFacultades = findViewById(R.id.rv_facultades);
        LinearLayoutManager rvLinear = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        String campus = getIntent().getStringExtra("campusSeleccionado");
        listaFacultades = ActivityMain.database.ReadFACULTADESWHERE(campus);

        //Se inicializa el adaptador del RecyclerView
        //Se le pasa el array con los datos
        adapter = new AdapterFacultades(listaFacultades);

        //Servirá para asociar el listener real al del adaptador al momento de crearlo
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se recoge el objeto en cuestion en la posicion en la que se clickó
                f = listaFacultades.get(rvFacultades.getChildAdapterPosition(v));
                // Crea un intent con la noticia y la envía a FacultadActivity para mostrarla
                Intent visualizar = new Intent (getApplicationContext(), ViewFacultad.class);
                //Se añade un contenido extra al Intent, el objeto Facultad
                visualizar.putExtra("objeto_Facultad", f);
                //Se inicializa
                startActivity(visualizar);
            }
        });

        //Se le aplica el adaptador personalizado al RecyclerView
        rvFacultades.setAdapter(adapter);
        //Se indica que el RecyclerView se visualizara como un layout linear
        rvFacultades.setLayoutManager(rvLinear);

    }

}