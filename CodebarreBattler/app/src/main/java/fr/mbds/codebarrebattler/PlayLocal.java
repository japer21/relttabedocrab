package fr.mbds.codebarrebattler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import fr.mbds.codebarrebattler.model.Creature;

public class PlayLocal extends AppCompatActivity {
    Button c1nutt;
    Button c2butt;
    TextView c1txtv;
    TextView c2ttxt;
    Integer val1=0;
    Integer val2=0;
    //creature 1
    TextView nomCreat1;
    TextView viesCreat1;
    TextView attaqueCreat1;
    TextView defenseCreat1;
    TextView setnomCreat1;
    TextView setviesCreat1;
    TextView setattaqueCreat1;
    TextView setdefenseCreat1;
    ImageView imageCreat1;
    //creature 2
    TextView nomCreat2;
    TextView viesCreat2;
    TextView attaqueCreat2;
    TextView defenseCreat2;
    TextView setnomCreat2;
    TextView setviesCreat2;
    TextView setattaqueCreat2;
    TextView setdefenseCreat2;
    ImageView getImageCreat2;
    Creature c1;
    Creature c2;
    static boolean jeton1;
    static boolean jeton2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_local);
        Intent i =getIntent();
        c1 = (Creature)i.getExtras().getParcelable("creaturePlayer1");
        c2 = (Creature)i.getExtras().getParcelable("creaturePlayer2");
        c1nutt=(Button)findViewById(R.id.c1button);
        c2butt=(Button)findViewById(R.id.c2button);

        jeton1=true;
        jeton2=true;
        nomCreat1 = (TextView)findViewById(R.id.nomCreat1Local);
        viesCreat1 = (TextView)findViewById(R.id.nbViesCreat1Local);
        attaqueCreat1 = (TextView)findViewById(R.id.attaqueCreat1Local);
        defenseCreat1 = (TextView)findViewById(R.id.defenseCreat1Local);
        imageCreat1 = (ImageView)findViewById(R.id.imgCreature1Local);
        setnomCreat1 = (TextView)findViewById(R.id.setNomCreat1Local);
        setnomCreat1.setText(c1.getTitle());
        setviesCreat1 = (TextView)findViewById(R.id.setNbViesCreat1Local);
        setviesCreat1.setText(String.valueOf(c1.getVies()));
        setattaqueCreat1 = (TextView)findViewById(R.id.setAttaqueCreat1Local);
        setattaqueCreat1.setText(String.valueOf(c1.getCapAttq()));
        setdefenseCreat1 = (TextView)findViewById(R.id.setDefenseCreat1Local);
        setdefenseCreat1.setText(String.valueOf(c1.getCapDef()));

        nomCreat2 = (TextView)findViewById(R.id.nomCreat2Local);
        viesCreat2 = (TextView)findViewById(R.id.nbViesCrea21Local);
        attaqueCreat2 = (TextView)findViewById(R.id.attaqueCreat2Local);
        defenseCreat2 = (TextView)findViewById(R.id.defenseCreat2Local);
        imageCreat1 = (ImageView)findViewById(R.id.imgCreature2Local);
        setnomCreat2 = (TextView)findViewById(R.id.setNomCreat2Local);
        setnomCreat2.setText(c2.getTitle());
        setviesCreat2 = (TextView)findViewById(R.id.setNbViesCrea21Local);
        setviesCreat2.setText(String.valueOf(c2.getVies()));
        setattaqueCreat2 = (TextView)findViewById(R.id.setAttaqueCreat2Local);
        setattaqueCreat2.setText(String.valueOf(c2.getCapAttq()));
        setdefenseCreat2 = (TextView)findViewById(R.id.setDefenseCreat2Local);
        setdefenseCreat2.setText(String.valueOf(c2.getCapDef()));


        c1nutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(jeton1)
                    attaque1(c1,c2,jeton1);
                else
                    Toast.makeText(getApplicationContext(),"Ce n'est pas votre tour",Toast.LENGTH_SHORT);

            }
        });
        c2butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(jeton2)
                    attaque2(c2,c1,jeton2);
                else
                    Toast.makeText(getApplicationContext(),"Ce n'est pas votre tour",Toast.LENGTH_SHORT);
            }
        });


    }
    //getters and setters
    public String getNomCreat1() {
        return nomCreat1.getText().toString();
    }

    public Integer getViesCreat1() {
        return Integer.valueOf(setviesCreat1.getText().toString());
    }

    public void setViesCreat1( Creature creature){
        setviesCreat1.setText(String.valueOf(creature.getVies()));
    }
    public Integer getAttaqueCreat1() {
        return Integer.valueOf(setattaqueCreat1.getText().toString());
    }
    public void setAttaqueCreat1(Integer i){
        setattaqueCreat1.setText(String.valueOf(i));
    }
    public Integer getDefenseCreat1() {
        return Integer.valueOf(setdefenseCreat1.getText().toString());
    }
    public void setDefenseCreat1(Integer i){
        setdefenseCreat1.setText(String.valueOf(i));
    }
    public void attaque1(Creature c1, Creature c2, boolean autorize){

        Random random = new Random();
        Integer valeurAleat = random.nextInt(c1.getCapAttq()+1);
        Integer malus = c2.getCapDef()-valeurAleat;
        if(malus>0){
            c2.setVies(malus);

        }
        else {
            c2.setVies(0);
            AlertDialog alertDialog = new AlertDialog.Builder(
                    PlayLocal.this).create();

            // Setting Dialog Title
            alertDialog.setTitle("Fin du jeu");

            // Setting Dialog Message
            alertDialog.setMessage("Le vainqueur c'est "+c1.getTitle());

            // Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    finish();
                }
            });

            // Showing Alert Message
            alertDialog.show();
        }
        autorize=false;
       // setViesCreat1(c1);
        setViesCreat2(c2);

    }

    public void attaque2(Creature c2, Creature c1, boolean autorize){

        Random random = new Random();
        Integer valeurAleat = random.nextInt(c2.getCapAttq()+2);
        Integer malus = c1.getCapDef()-valeurAleat;
        if(malus>0){
            c1.setVies(malus);

        }
        else {
            c1.setVies(0);
            AlertDialog alertDialog = new AlertDialog.Builder(
                    PlayLocal.this).create();

            // Setting Dialog Title
            alertDialog.setTitle("Fin du jeu");

            // Setting Dialog Message
            alertDialog.setMessage("Le vainqueur c'est "+c2.getTitle());

            // Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    finish();
                }
            });

            // Showing Alert Message
            alertDialog.show();
        }
        autorize=false;
         setViesCreat1(c1);
        //setViesCreat2(c2);

    }
    public Integer getViesCreat2() {
        return Integer.valueOf(setviesCreat2.getText().toString());
    }
    public void setViesCreat2(Creature creature){
        setviesCreat2.setText(String.valueOf(creature.getVies()));
    }
    public Integer getAttaqueCreat2() {
        return Integer.valueOf(setattaqueCreat2.getText().toString());
    }
    public void setAttaqueCreat2(Creature creature){
        setattaqueCreat2.setText(String.valueOf(creature.getVies()));
    }
    public Integer getDefenseCreat2() {
        return Integer.valueOf(setdefenseCreat2.getText().toString());
    }
    public void setDefenseCreat2(Creature creature){
        setdefenseCreat2.setText(String.valueOf(creature.getVies()));
    }

    /*

    public Button getC1nutt() {
        return c1nutt;
    }

    public Button getC2butt() {
        return c2butt;
    }

    public void setVal1(Integer val1) {
        this.val1 = val1;
        c1txtv.setText(String.valueOf(this.val1));
    }

    public void setVal2(Integer val2) {
        this.val2 = val2;
        c2ttxt.setText(String.valueOf(this.val2));
    }
    */

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent i=getIntent();
        finish();
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);

    }
}
