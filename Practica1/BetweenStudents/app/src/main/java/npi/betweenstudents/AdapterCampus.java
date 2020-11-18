package npi.betweenstudents;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterCampus extends RecyclerView.Adapter<AdapterCampus.CampusViewHolder> implements View.OnClickListener{

    // Se llenará con los datos de los campus desde el MainActivity
    private ArrayList<Campus> listaCampus;
    //Este listener se encargara de gestionar las acciones de cada vista
    private View.OnClickListener listener;

    public AdapterCampus(ArrayList<Campus> cms) {
        this.listaCampus = cms;

    }

    public static class CampusViewHolder extends RecyclerView.ViewHolder {

        TextView tv_Nom;
        ImageView im_Ima;

        public CampusViewHolder(View itemView) {
            super(itemView);

            tv_Nom = itemView.findViewById(R.id.campus_nombre);
            im_Ima = itemView.findViewById(R.id.campus_image);
        }

    }

    @Override
    public CampusViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_campus, viewGroup, false);

        CampusViewHolder campusVH = new CampusViewHolder(itemView);

        //En caso de clickar sobre una vista, se ejecutara la accion personalizada
        itemView.setOnClickListener(this);

        return campusVH;
    }

    @Override
    public void onBindViewHolder(CampusViewHolder viewHolder, int pos) {
        Campus itemCampus = listaCampus.get(pos);

        viewHolder.tv_Nom.setText(itemCampus.getNombre());
        viewHolder.im_Ima.setImageResource(itemCampus.getImage());
    }

    @Override
    public int getItemCount() {
        return listaCampus.size();
    }


    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }
}
