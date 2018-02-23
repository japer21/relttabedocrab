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
import fr.mbds.codebarrebattler.model.Equipement;

public class EquipmentDetailActivity extends AppCompatActivity {

    ImageView imgE;
    Button supprimerEqp;

    TextView nomEq;
    TextView contrib;
    TextView belongs;
    TextView setNom;
    TextView setContri;
    TextView setBelongs;
    DataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_detail);

        imgE = (ImageView)findViewById(R.id.imgEquipment);
        supprimerEqp = (Button)findViewById(R.id.supprimerEquip);
        nomEq = (TextView)findViewById(R.id.nomEquipDetail);
        contrib = (TextView)findViewById(R.id.contribuEquipDetail);
        belongs = (TextView)findViewById(R.id.belongsToDetail);
        setNom = (TextView)findViewById(R.id.setNomEquip);
        setContri=(TextView)findViewById(R.id.setContrib);
        setBelongs=(TextView)findViewById(R.id.setBelongs);
        db = new DataBase(this);
        Intent i = getIntent();

        final Equipement e = i.getExtras().getParcelable("detailEquip");
        setNom.setText(e.getTitle());
        setBelongs.setText(String.valueOf(e.getBelongsTo()));
        setContri.setText(String.valueOf(e.getContribution()));
        supprimerEqp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    db.deleteEquipmentById(e.getId());
                   // db.deleteCreatureById(c.getId());
                    Toast.makeText(getApplicationContext(), "Equipement avec id "+e.getId()+" supprimée!", Toast.LENGTH_SHORT).show();
                    finish();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
