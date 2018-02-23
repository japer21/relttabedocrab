package fr.mbds.codebarrebattler.utils;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fr.mbds.codebarrebattler.R;
import fr.mbds.codebarrebattler.database.DataBase;
import fr.mbds.codebarrebattler.model.Creature;

/**
 * Created by jperk on 06/02/2018.
 */

public class CreaturesAdapterHelper implements ListAdapter {
    private ArrayList<DataSetObserver> observers = new ArrayList<DataSetObserver>();
    Context ctx;
    ArrayList<Creature> l_creatures;
    ImageView img;
    TextView nom;
    TextView vies;
    TextView att;
    TextView def;
    TextView setNom;
    TextView setVies;
    TextView setAtt;
    TextView setDef;
    public DataBase dbHelper;
    public CreaturesAdapterHelper(Context context){
        super();
        this.ctx=context;
        dbHelper = new DataBase(context);
        l_creatures=dbHelper.getCreatures();
    }
    public CreaturesAdapterHelper(Context context, ArrayList<Creature> lc){
        super();
        this.ctx=context;
        this.l_creatures=lc;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        observers.add(dataSetObserver);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        for (DataSetObserver observer: observers) {
            observer.onChanged();
        }
    }

    @Override
    public int getCount() {
        return this.l_creatures.size();
    }

    @Override
    public Object getItem(int i) {
        return l_creatures.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View returnView;
        if(view==null){
                //R.layout.creature go smeniv
            returnView=View.inflate(ctx, R.layout.creature , null);
        }
        else {
            returnView = view;
        }
        if(l_creatures.get(i).getCreaImg()!=null){
            img=(ImageView)returnView.findViewById(R.id.imageViewCreature);
            img.setImageBitmap(l_creatures.get(i).getCreaImg());
        }


        nom=(TextView)returnView.findViewById(R.id.textViewNomCreature);
        setNom=(TextView)returnView.findViewById(R.id.textViewGetNomC);
        setNom.setText(l_creatures.get(i).getTitle());

        vies=(TextView)returnView.findViewById(R.id.textViewNbVies);
        setVies=(TextView)returnView.findViewById(R.id.textViewGetNbviesC);
        setVies.setText(String.valueOf(l_creatures.get(i).getVies()));

        att=(TextView)returnView.findViewById(R.id.textViewAttaque);
        setAtt=(TextView)returnView.findViewById(R.id.textViewGetAttaque);
        setAtt.setText(String.valueOf(l_creatures.get(i).getCapAttq()));

        def=(TextView)returnView.findViewById(R.id.textViewDefense);
        setDef=(TextView)returnView.findViewById(R.id.textViewGetDefense);
        setDef.setText(String.valueOf(l_creatures.get(i).getCapDef()));
        return returnView;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        if(l_creatures.isEmpty())
            return true;
        else
        return false;
    }
}
