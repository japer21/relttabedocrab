package fr.mbds.codebarrebattler;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import fr.mbds.codebarrebattler.database.DataBase;
import fr.mbds.codebarrebattler.model.Creature;
import fr.mbds.codebarrebattler.model.Equipement;
import fr.mbds.codebarrebattler.utils.BarcodeParser;
import fr.mbds.codebarrebattler.utils.EquipmentsAdapterHelper;

public class ListEquipmentsPerCreatureActivity extends AppCompatActivity {
    IntentIntegrator zxing_scan = new IntentIntegrator(this);
    ListAdapter adapter = null;
    ListView listEquipments;
    TextView count;
    private List<Integer> comportements = Arrays.asList(-1,1,0);
    private List<String> titles = Arrays.asList("Epee","Arbalète","Snyper");
    private Random randomGenerator;
    public ArrayList<Equipement> liste_creatures=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_equipments_per_creature);
        listEquipments = (ListView)findViewById(R.id.listEquipments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent in=getIntent();
        Creature myc = in.getExtras().getParcelable("creature");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zxing_scan.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                zxing_scan.initiateScan();
            }
        });
        adapter =new EquipmentsAdapterHelper(this,myc.getId());
        listEquipments.setAdapter(adapter);
        listEquipments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Intent i=getIntent();
        Equipement equipement;
        DataBase db = new DataBase(this);
        BarcodeParser barcodeParser = new BarcodeParser(this);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        randomGenerator = new Random();
        int index = randomGenerator.nextInt(comportements.size());
        int comportmentEquipment = comportements.get(index);
        String title = titles.get(index);
        Creature boss = i.getExtras().getParcelable("creature");
        if(result!=null){
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                barcodeParser.parseToInt(result.getContents());
                char [] initialValues = Integer.toString(barcodeParser.getMy_val_sum()).toCharArray();
                if(initialValues.length < 3){
                    Toast.makeText(this, "Invalide code barre! Essayez de nouveau !", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        //ime random
                        equipement=new Equipement(title,comportmentEquipment,boss.getId());
                        db.insertEquipment(equipement);

                        Toast.makeText(this, "Equipment ajouté "+boss.getId(), Toast.LENGTH_SHORT).show();
                    }catch (android.database.SQLException e){
                        e.printStackTrace();
                        Toast.makeText(this, "Equipment probleme ", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Intent i=getIntent();
        finish();
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);

    }
}
