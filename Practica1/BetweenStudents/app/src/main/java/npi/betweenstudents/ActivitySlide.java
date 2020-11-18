package npi.betweenstudents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class ActivitySlide extends AppCompatActivity {


    public static ViewPager viewPager;
    AdapterSlideViewPager adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        viewPager=findViewById(R.id.viewpager);
        adapter=new AdapterSlideViewPager(getApplicationContext());
        viewPager.setAdapter(adapter);
        if (isOpenAlread()){
            Intent intent = new Intent(ActivitySlide.this, ActivityMain.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else{
            SharedPreferences.Editor editor=getSharedPreferences("slide", MODE_PRIVATE).edit();
            editor.putBoolean("slide", true);
            editor.apply();

        }
    }

    private boolean isOpenAlread(){
        SharedPreferences sharedPreferences = getSharedPreferences("slide", MODE_PRIVATE);
        return sharedPreferences.getBoolean("slide", false);
    }
}