package fr.mbds.codebarrebattler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    ImageButton btnListCreat;
    ImageButton btnJeuLocal;
    ImageView btnJeuReseau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnListCreat = (ImageButton)findViewById(R.id.btnListCreat);
        btnJeuLocal = (ImageButton)findViewById(R.id.buttonLocalJeu);
        btnJeuReseau = (ImageButton)findViewById(R.id.buttonReseauJeu);
        btnListCreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ListCreaturesActivity.class);
                startActivity(i);
            }
        });
        btnJeuLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l = new Intent(getApplicationContext(),ConfigurationJeuLocalActivity.class);
               // Intent l = new Intent(getApplicationContext(),PlayLocal.class);
                startActivity(l);
            }
        });
        btnJeuReseau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent r = new Intent(getApplicationContext(),ConfigurationJeuReseauActivity.class);
                startActivity(r);
            }
        });

    }

}
