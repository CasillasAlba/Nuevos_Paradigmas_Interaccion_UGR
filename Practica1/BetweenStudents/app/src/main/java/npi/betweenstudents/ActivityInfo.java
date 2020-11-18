package npi.betweenstudents;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ActivityInfo extends AppCompatActivity {

    CardView cvBecas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        cvBecas = findViewById(R.id.grid_becas);
        cvBecas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent becas = new Intent(getApplicationContext(), ActivityBecas.class);
                startActivity(becas);
            }
        });

    }

    @Override
    public void onBackPressed(){
        Intent principal = new Intent(getApplicationContext(), ActivityMain.class);
        finish();
        startActivity(principal);


    }

}