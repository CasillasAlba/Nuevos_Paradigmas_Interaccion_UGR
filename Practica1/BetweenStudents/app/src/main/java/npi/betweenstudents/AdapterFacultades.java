package npi.betweenstudents;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//Extenderá de RecyclerView, compiladas anteriormente las dependencias
//Implementara la interfaz OnClickListener para controlar que ocurre al pulsar sobre una vista
public class AdapterFacultades extends RecyclerView.Adapter<AdapterFacultades.FacultadesViewHolder> implements View.OnClickListener{

    //Este array se llenara con los datos obtenidos en la actividad que llame a esta
    private ArrayList<Facultad> datos;

    //Este listener se encargara de gestionar las acciones de cada vista
    private View.OnClickListener listener;

    public AdapterFacultades(ArrayList<Facultad> datos) {
        this.datos = datos;
    }

    public static class FacultadesViewHolder extends RecyclerView.ViewHolder {

        TextView tv_Nom;
        ImageView iv_Ima;


        public FacultadesViewHolder(View itemView) {
            super(itemView);

            tv_Nom = (TextView) itemView.findViewById(R.id.fac_nombre);
            iv_Ima = (ImageView) itemView.findViewById(R.id.fac_imagen);
        }

    }

    @Override
    public FacultadesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_facultad, viewGroup, false);

        FacultadesViewHolder facultadesVH = new FacultadesViewHolder(itemView);

        //En caso de clickar sobre una vista, se ejecutara la accion personalizada
        itemView.setOnClickListener(this);

        return facultadesVH;
    }

    @Override
    public void onBindViewHolder(FacultadesViewHolder viewHolder, int pos) {
        Facultad itemFacultad = datos.get(pos);

        viewHolder.tv_Nom.setText(itemFacultad.getNombre());
        viewHolder.iv_Ima.setImageResource(itemFacultad.getImagen());

    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }
}
