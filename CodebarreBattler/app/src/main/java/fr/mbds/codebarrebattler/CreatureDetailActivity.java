package fr.mbds.codebarrebattler;

import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import fr.mbds.codebarrebattler.database.DataBase;
import fr.mbds.codebarrebattler.model.Creature;

public class CreatureDetailActivity extends AppCompatActivity {
    TextView nom;
    TextView setNom;
    TextView vies;
    TextView setVies;
    TextView attaque;
    TextView setAttaque;
    TextView defense;
    TextView setDefense;
    ImageView creatImg;
    Button supprimerCreat;
    Button listeEquipments;
    DataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creature_detail);
        db = new DataBase(this);
        Intent i =getIntent();
        final Creature c = i.getExtras().getParcelable("detailCreature");
       // Toast.makeText(this, "adapter getitem "+c.getId(), Toast.LENGTH_SHORT).show();
        nom=(TextView)findViewById(R.id.textViewDetailNom);
        setNom=(TextView)findViewById(R.id.textViewDetailSetNom);
        setNom.setText(c.getTitle());
        vies = (TextView)findViewById(R.id.textViewDetailNbVies);
        setVies = (TextView)findViewById(R.id.textViewDetailSetNbVies);
        setVies.setText(String.valueOf(c.getVies()));
        attaque = (TextView)findViewById(R.id.textViewDetailAttaque);
        setAttaque = (TextView)findViewById(R.id.textViewDetailSetAttaque);
        setAttaque.setText(String.valueOf(c.getCapAttq()));
        defense = (TextView)findViewById(R.id.textViewDetailDefense);
        setDefense = (TextView)findViewById(R.id.textViewDetailSetDefense);
        setDefense.setText(String.valueOf(c.getCapDef()));
        supprimerCreat=(Button)findViewById(R.id.buttonDeleteCreature);
        supprimerCreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    db.deleteCreatureById(c.getId());
                    Toast.makeText(getApplicationContext(), "Creature avec id "+c.getId()+" supprimée!", Toast.LENGTH_SHORT).show();
                    finish();
                }catch (SQLException e){
                    e.printStackTrace();
                }


            }
        });
        creatImg=(ImageView)findViewById(R.id.imageViewDetailCreature);
        if(c.getCreaImg()!=null){
            creatImg.setImageBitmap(c.getCreaImg());
        }
        listeEquipments=(Button)findViewById(R.id.buttonVersEquipments);
        listeEquipments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ListEquipmentsPerCreatureActivity.class);
                intent.putExtra("creature",c);
                startActivity(intent);
            }
        });

    }
}
