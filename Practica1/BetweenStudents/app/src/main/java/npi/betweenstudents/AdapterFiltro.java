package npi.betweenstudents;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//Extenderá de RecyclerView, compiladas anteriormente las dependencias
//Implementara la interfaz OnClickListener para controlar que ocurre al pulsar sobre una vista
public class AdapterFiltro extends RecyclerView.Adapter<AdapterFiltro.FiltroViewHolder> implements View.OnClickListener, Filterable {

    //Este array se llenara con los datos obtenidos en la actividad que llame a esta
    private ArrayList<Facultad> datosFull;
    private ArrayList<Facultad> datosFiltrados;

    //Este listener se encargara de gestionar las acciones de cada vista
    private View.OnClickListener listener;

    public AdapterFiltro(ArrayList<Facultad> datos) {
        this.datosFull = new ArrayList<>(datos);
        this.datosFiltrados = datos;
    }

    class FiltroViewHolder extends RecyclerView.ViewHolder {
        TextView tv_Nom;
        ImageView iv_Ima;

        FiltroViewHolder(View itemView) {
            super(itemView);

            tv_Nom = (TextView) itemView.findViewById(R.id.sv_nombre);
            iv_Ima = (ImageView) itemView.findViewById(R.id.sv_imagen);
        }

    }

    @Override
    public FiltroViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_searchview, viewGroup, false);

        FiltroViewHolder filtroVH = new FiltroViewHolder(itemView);

        //En caso de clickar sobre una vista, se ejecutara la accion personalizada
        itemView.setOnClickListener(this);

        return filtroVH;
    }

    @Override
    public void onBindViewHolder(FiltroViewHolder viewHolder, int pos) {
        Facultad itemFacultad = datosFiltrados.get(pos);

        viewHolder.tv_Nom.setText(itemFacultad.getNombre());
        viewHolder.iv_Ima.setImageResource(itemFacultad.getImagen());

    }

    @Override
    public int getItemCount() {
        return datosFiltrados.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }

    private Filter filtro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Facultad> filtradas = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filtradas.addAll(datosFull);

            }else{
                // Se controla que la filtracion no sea afectada por mayúsculas o minúsculas
                String patron = constraint.toString().toLowerCase().trim();

                for(Facultad f : datosFull){
                    // Si al menos contiene una parte del patron en el nombre, se añade a la lista
                    if(f.getNombre().toLowerCase().contains(patron)){
                        filtradas.add(f);
                    }
                }
            }

            FilterResults filtracion = new FilterResults();
            filtracion.values = filtradas;

            return filtracion;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            datosFiltrados.clear(); // Aqui se añadiran solo los objetos que deben aparecer
            datosFiltrados.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public Filter getFilter() {
        return filtro;
    }

}
