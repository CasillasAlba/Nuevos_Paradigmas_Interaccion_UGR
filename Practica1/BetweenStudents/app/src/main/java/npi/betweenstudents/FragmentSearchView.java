package npi.betweenstudents;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class FragmentSearchView extends FragmentActivity {

    private SearchView searchView;
    private SearchManager searchManager;

    private Facultad f;
    private ArrayList<Facultad> facultades;
    private AdapterFiltro filtrar;
    private RecyclerView rvFiltro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_searchview);

        //Se recoge el RecyclerView del Layout de la activity
        rvFiltro = findViewById(R.id.search_recycler);
        rvFiltro.setHasFixedSize(true);
        LinearLayoutManager rvLinear = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        facultades = ActivityMain.database.ReadFACULTADES();

        filtrar = new AdapterFiltro(facultades);

        //Servirá para asociar el listener real al del adaptador al momento de crearlo
        filtrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se recoge el objeto en cuestion en la posicion en la que se clickó
                f = facultades.get(rvFiltro.getChildAdapterPosition(v));
                // Crea un intent con la noticia y la envía a FacultadActivity para mostrarla
                Intent visualizar = new Intent (getApplicationContext(), ViewFacultad.class);
                //Se añade un contenido extra al Intent, el objeto Facultad
                visualizar.putExtra("objeto_Facultad", f);
                //Se inicializa
                startActivity(visualizar);
            }
        });

        //Se indica que el RecyclerView se visualizara como un layout linear
        rvFiltro.setLayoutManager(rvLinear);
        //Se le aplica el adaptador personalizado al RecyclerView
        rvFiltro.setAdapter(filtrar);

        searchView = findViewById(R.id.searchview_toolbar);
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtrar.getFilter().filter(newText);
                return false;
            }
        });

    }
}
