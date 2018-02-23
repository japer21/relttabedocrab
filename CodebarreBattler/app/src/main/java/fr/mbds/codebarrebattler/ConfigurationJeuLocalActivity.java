package fr.mbds.codebarrebattler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import fr.mbds.codebarrebattler.database.DataBase;
import fr.mbds.codebarrebattler.model.Creature;
import fr.mbds.codebarrebattler.model.Equipement;
import fr.mbds.codebarrebattler.utils.CreaturesAdapterHelper;
import fr.mbds.codebarrebattler.utils.EquipmentsAdapterHelper;

public class ConfigurationJeuLocalActivity extends AppCompatActivity {
    TextView intro;
    TextView creat1label;
    TextView creat2label;
    Spinner creat1spin;
    Spinner creat2spin;
    Button valider;
    Button retourner;
    ArrayList<Creature> listCreat1;
    Creature creaturePlayer1;
    Creature creaturePlayer2;
    ArrayList<Creature> listCreat2;
    ArrayAdapter<Creature> adapter1 = null;
    ArrayAdapter<Creature> adapter2=null;
    TextView txtSpin;
    DataBase db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration_jeu_local);
        intro = (TextView)findViewById(R.id.textViewIntroJL);
        creat1label = (TextView)findViewById(R.id.textViewCreat1Local);
        creat2label=(TextView)findViewById(R.id.textViewCreat2Local);
        creat1spin = (Spinner)findViewById(R.id.spinListCreatures1);
        creat2spin = (Spinner)findViewById(R.id.spinListCreatures2);
        valider=(Button)findViewById(R.id.buttonValiderConf);
        retourner=(Button)findViewById(R.id.buttonRetournerMain);
        db = new DataBase(this);
        listCreat1=db.getCreatures();

        listCreat2=db.getCreatures();
       // creaturePlayer1=listCreat1.get(0);
       // creaturePlayer2=listCreat1.get(0);
        adapter1 = new ArrayAdapter<Creature>(this,R.layout.simple_spinner_item, listCreat1);
        adapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        adapter2 = new ArrayAdapter<Creature>(this,R.layout.simple_spinner_item,listCreat2);
        adapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        creat1spin.setAdapter(adapter1);
        creat1spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                creaturePlayer1=(Creature)adapterView.getItemAtPosition(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        creat2spin.setAdapter(adapter2);
        creat2spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                creaturePlayer2=(Creature)adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lancerJeu = new Intent(getApplicationContext(),PlayLocal.class);
                lancerJeu.putExtra("creaturePlayer1",creaturePlayer1);
                lancerJeu.putExtra("creaturePlayer2",creaturePlayer2);
                startActivity(lancerJeu);
                finish();

            }
        });

    }
}
