package fr.mbds.codebarrebattler;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import fr.mbds.codebarrebattler.database.DataBase;
import fr.mbds.codebarrebattler.model.Creature;
import fr.mbds.codebarrebattler.utils.BarcodeParser;
import fr.mbds.codebarrebattler.utils.CreaturesAdapterHelper;

public class ListCreaturesActivity extends AppCompatActivity {

    IntentIntegrator zxing_scan = new IntentIntegrator(this);
    ListAdapter adapter = null;
    ListView listCreatures;
    TextView count;
    private List<String> titles = Arrays.asList("Miamol","Kutus","Liany","Jukevan","Motus","Neatle","Kungvu","Propzee","Quotopi","Linkec");
    private Random randomGenerator;
   public ArrayList<Creature> liste_creatures=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_creatures);
        listCreatures = (ListView)findViewById(R.id.listCreatures);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zxing_scan.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                zxing_scan.initiateScan();
            }
        });
        adapter = new CreaturesAdapterHelper(this);
        listCreatures.setAdapter(adapter);
        listCreatures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Creature c = (Creature)adapter.getItem(i);

                Toast.makeText(getApplicationContext(), "adapter id db "+c.getId(), Toast.LENGTH_LONG).show();
                Intent detailCreatureIntent = new Intent(ListCreaturesActivity.this,CreatureDetailActivity.class);
                detailCreatureIntent.putExtra("detailCreature",c);
                startActivity(detailCreatureIntent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Creature ct;
        DataBase db = new DataBase(this);
        BarcodeParser barcodeParser = new BarcodeParser(this);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        randomGenerator = new Random();
        int index = randomGenerator.nextInt(titles.size());
        String creaTitle = titles.get(index);

        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {//od tuka

               barcodeParser.parseToInt(result.getContents());
                char [] initialValues = Integer.toString(barcodeParser.getMy_val_sum()).toCharArray();
                if(initialValues.length < 3){
                    Toast.makeText(this, "Invalide code barre! Essayez de nouveau !", Toast.LENGTH_LONG).show();
                }else if(initialValues.length<6){
                    try {
                        ct=new Creature(Integer.parseInt(String.valueOf(initialValues[0])),Integer.parseInt(String.valueOf(initialValues[1])),Integer.parseInt(String.valueOf(initialValues[2])),creaTitle);
                        db.insertCreature(ct);
                        Toast.makeText(this, "Creature créé 3*1 ", Toast.LENGTH_LONG).show();
                        liste_creatures.add(ct);
                    }catch (SQLException e){
                        e.printStackTrace();
                        Toast.makeText(this, "Creature probleme ", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    try {
                        ct=new Creature(Integer.parseInt(String.valueOf(initialValues[0]))+Integer.parseInt(String.valueOf(initialValues[2])),Integer.parseInt(String.valueOf(initialValues[1]))+Integer.parseInt(String.valueOf(initialValues[3])),Integer.parseInt(String.valueOf(initialValues[4]))+Integer.parseInt(String.valueOf(initialValues[5])),creaTitle);
                        db.insertCreature(ct);
                        Toast.makeText(this, "Creature créé 3*2 ", Toast.LENGTH_LONG).show();
                        liste_creatures.add(ct);
                    }catch (SQLException e){
                        e.printStackTrace();
                        Toast.makeText(this, "Creature probleme ", Toast.LENGTH_LONG).show();
                    }
                }

            }//do tuka
            //finish();
        } else {
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
