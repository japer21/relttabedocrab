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
import fr.mbds.codebarrebattler.model.Equipement;

/**
 * Created by jperk on 10/02/2018.
 */

public class EquipmentsAdapterHelper implements ListAdapter {
    private ArrayList<DataSetObserver> observers = new ArrayList<DataSetObserver>();
    Context ctx;
    ArrayList<Equipement> l_equipments;
    ImageView img;
    TextView nom;
    TextView contrib;
    TextView belongsto;
    TextView setNom;
    TextView setContrib;
    TextView setBelonsTo;
    public DataBase dbHelper;

    public EquipmentsAdapterHelper(Context context, int index){
        super();
        this.ctx=context;
        dbHelper = new DataBase(context);
        //l_equipments = dbHelper.getEquipments();
        l_equipments=dbHelper.getEquipmentsByCreature(index);
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
        return this.l_equipments.size();
    }

    @Override
    public Object getItem(int i) {
        return l_equipments.get(i);
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
            returnView=View.inflate(ctx, R.layout.equipment , null);
        }
        else {
            returnView = view;
        }
        if(l_equipments.get(i).getImg()!=null){
            img=(ImageView)returnView.findViewById(R.id.imageViewEquip);
            img.setImageBitmap(l_equipments.get(i).getImg());
        }


        nom=(TextView)returnView.findViewById(R.id.textViewNomEquip);
        setNom=(TextView)returnView.findViewById(R.id.textViewGetNomE);
        setNom.setText(l_equipments.get(i).getTitle());

        contrib=(TextView)returnView.findViewById(R.id.textViewContribution);
        setContrib=(TextView)returnView.findViewById(R.id.textViewGetContribE);
        setContrib.setText(String.valueOf(l_equipments.get(i).getContribution()));

        belongsto=(TextView)returnView.findViewById(R.id.textViewBelongsTo);
        setBelonsTo=(TextView)returnView.findViewById(R.id.textViewGetBelongsTo);
        setBelonsTo.setText(String.valueOf(l_equipments.get(i).getBelongsTo()));
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
        if(l_equipments.isEmpty())
            return true;
        else
            return false;
    }
}
