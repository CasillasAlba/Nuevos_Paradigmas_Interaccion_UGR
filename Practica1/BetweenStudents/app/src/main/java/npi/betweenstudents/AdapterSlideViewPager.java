package npi.betweenstudents;

import androidx.viewpager.widget.PagerAdapter;
import androidx.annotation.NonNull;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterSlideViewPager extends PagerAdapter {
    Context ctx;

    public AdapterSlideViewPager (Context ctx){
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object){
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater layoutInflater = (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slide_screen, container, false);

        ImageView logo=view.findViewById(R.id.logo);
        ImageView p1= view.findViewById(R.id.p1);
        ImageView p2= view.findViewById(R.id.p2);
        ImageView p3= view.findViewById(R.id.p3);
        ImageView p4= view.findViewById(R.id.p4);
        ImageView p5= view.findViewById(R.id.p5);
        ImageView p6= view.findViewById(R.id.p6);
        ImageView p7= view.findViewById(R.id.p7);
        ImageView p8= view.findViewById(R.id.p8);

        TextView title=view.findViewById(R.id.title);
        TextView subtitle = view.findViewById(R.id.subtitle);

        ImageView next=view.findViewById(R.id.ic_next);
        ImageView back=view.findViewById(R.id.ic_back);
        Button btnGetStarted =view.findViewById(R.id.btnGetStarted);

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, ActivityMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivitySlide.viewPager.setCurrentItem(position+1);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivitySlide.viewPager.setCurrentItem(position-1);
            }
        });

        switch (position){
            case 0:
                logo.setImageResource(R.drawable.logotipo);
                p3.setImageResource(R.drawable.selec);
                p2.setImageResource(R.drawable.unselec);
                p1.setImageResource(R.drawable.unselec);
                p4.setImageResource(R.drawable.unselec);
                p5.setImageResource(R.drawable.unselec);
                p6.setImageResource(R.drawable.unselec);
                p7.setImageResource(R.drawable.unselec);
                p8.setImageResource(R.drawable.unselec);
                title.setText("Bienvenidos a BEST");
                back.setVisibility(View.GONE);
                next.setVisibility(View.VISIBLE);
                break;
            case 1:
                logo.setImageResource(R.drawable.logo2);
                p3.setImageResource(R.drawable.unselec);
                p2.setImageResource(R.drawable.selec);
                p1.setImageResource(R.drawable.unselec);
                p4.setImageResource(R.drawable.unselec);
                p5.setImageResource(R.drawable.unselec);
                p6.setImageResource(R.drawable.unselec);
                p7.setImageResource(R.drawable.unselec);
                p8.setImageResource(R.drawable.unselec);
                title.setText("Informacion sobre la UGR");
                subtitle.setText("Informate sobre la ubicacion y disposicion de los centros de importancia estudiantil de la UGR");
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                break;
            case 2:
                logo.setImageResource(R.drawable.mov);
                p3.setImageResource(R.drawable.unselec);
                p2.setImageResource(R.drawable.unselec);
                p1.setImageResource(R.drawable.selec);
                p4.setImageResource(R.drawable.unselec);
                p5.setImageResource(R.drawable.unselec);
                p6.setImageResource(R.drawable.unselec);
                p7.setImageResource(R.drawable.unselec);
                p8.setImageResource(R.drawable.unselec);
                title.setText("Principales movimientos tactiles");
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                break;
            case 3:
                logo.setImageResource(R.drawable.deslizararriba);
                p3.setImageResource(R.drawable.unselec);
                p2.setImageResource(R.drawable.unselec);
                p1.setImageResource(R.drawable.unselec);
                p4.setImageResource(R.drawable.selec);
                p5.setImageResource(R.drawable.unselec);
                p6.setImageResource(R.drawable.unselec);
                p7.setImageResource(R.drawable.unselec);
                p8.setImageResource(R.drawable.unselec);
                title.setText("Busqueda filtrada de facultades");
                subtitle.setText("Desliza con el dedo en el menu hacia arriba para realizar una búsqueda filtrada de las facultades de la UGR");
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                break;
            case 4:
                logo.setImageResource(R.drawable.logomapa);
                p3.setImageResource(R.drawable.unselec);
                p2.setImageResource(R.drawable.unselec);
                p1.setImageResource(R.drawable.unselec);
                p4.setImageResource(R.drawable.unselec);
                p5.setImageResource(R.drawable.selec);
                p6.setImageResource(R.drawable.unselec);
                p7.setImageResource(R.drawable.unselec);
                p8.setImageResource(R.drawable.unselec);
                title.setText("Pincha en el botón de mapas");
                subtitle.setText("Toca una vez para mostrar el mapa de Granada, deja pulsado y te llevará al de Cuta y Melilla");
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                break;
            case 5:
                logo.setImageResource(R.drawable.mano);
                p3.setImageResource(R.drawable.unselec);
                p2.setImageResource(R.drawable.unselec);
                p1.setImageResource(R.drawable.unselec);
                p4.setImageResource(R.drawable.unselec);
                p5.setImageResource(R.drawable.unselec);
                p6.setImageResource(R.drawable.selec);
                p7.setImageResource(R.drawable.unselec);
                p8.setImageResource(R.drawable.unselec);
                title.setText("Elige una facultad para saber mas!");
                subtitle.setText("Pasa la mano por el sensor de la camará para obtener información sobre dicha facultad");
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                break;
            case 6:
                logo.setImageResource(R.drawable.gestosalida);
                p3.setImageResource(R.drawable.unselec);
                p2.setImageResource(R.drawable.unselec);
                p1.setImageResource(R.drawable.unselec);
                p4.setImageResource(R.drawable.unselec);
                p5.setImageResource(R.drawable.unselec);
                p6.setImageResource(R.drawable.unselec);
                p7.setImageResource(R.drawable.selec);
                p8.setImageResource(R.drawable.unselec);
                title.setText("Salir de la aplicacion");
                subtitle.setText("Sencillamente agita el movil hacia la izquierda y se cerrará la applicación o dandole al boton para salir!");
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                break;
            case 7:
                logo.setImageResource(R.drawable.interrogacion);
                p3.setImageResource(R.drawable.unselec);
                p2.setImageResource(R.drawable.unselec);
                p1.setImageResource(R.drawable.unselec);
                p4.setImageResource(R.drawable.unselec);
                p5.setImageResource(R.drawable.unselec);
                p6.setImageResource(R.drawable.unselec);
                p7.setImageResource(R.drawable.unselec);
                p8.setImageResource(R.drawable.selec);
                title.setText("Ver Tutorial");
                subtitle.setText("Pulsa el botón de información en el menú principal para leer esta sección de nuevo");
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.GONE);
                break;

        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
