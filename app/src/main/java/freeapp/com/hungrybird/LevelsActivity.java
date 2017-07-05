package freeapp.com.hungrybird;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import freeapp.com.hungrybird.framework.GameActivity;

public class LevelsActivity extends AppCompatActivity {

    private ImageButton jugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        jugar = (ImageButton) findViewById(R.id.btnInfinito);
        jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarTipoNivel(v);
            }
        });
    }

    public void seleccionarTipoNivel(View v) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}