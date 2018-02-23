package fr.mbds.codebarrebattler.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import fr.mbds.codebarrebattler.model.Creature;
import fr.mbds.codebarrebattler.model.Equipement;
import fr.mbds.codebarrebattler.utils.DbBitmapUtility;

/**
 * Created by jperk on 09/02/2018.
 */

public class DataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BarcodeBattler.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_CREATURE="Creature";
    private static final String id_creature ="id";
    private static final String photo_creature ="photo";
    private static final String title_creature ="title";
    private static final String nb_vies ="nb_vies";
    private static final String defense ="defense";
    private static final String attaque ="attaque";
    private static final String CREATE_TABLE_CREATURE=" CREATE TABLE "+TABLE_CREATURE+" ("+
            id_creature+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +title_creature+" TEXT, "
            +nb_vies+" TEXT, "
            +attaque+" TEXT, "
            +defense+" TEXT, "
            +photo_creature+" BLOB "+");";


    private static final String TABLE_EQUIPEMENT="Equipement";
    private static final String id_equipement ="id";
    private static final String title_equipement ="title";
    private static final String contribute_equipement ="contribution";
    private static final String belongs_to ="belongs_to";
    private static final String photo_equipement ="photo";
    private static final String CREATE_TABLE_EQUIPEMENT=" CREATE TABLE "+TABLE_EQUIPEMENT+" ("+
            id_equipement+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +title_equipement+" TEXT, "
            +contribute_equipement+" TEXT, "
            +belongs_to+" TEXT, "
            +photo_equipement+" BLOB "+");";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_CREATURE);
        sqLiteDatabase.execSQL(CREATE_TABLE_EQUIPEMENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CREATURE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPEMENT);

        // Create tables again
        onCreate(sqLiteDatabase);
    }
    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_CREATURE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_EQUIPEMENT);
        onCreate(sqLiteDatabase);
    }

    /************ Creature Table Methods ****************/

    //inserer une creature
    public int insertCreature(Creature creature) throws SQLException{
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(title_creature,creature.getTitle());
        contentValues.put(nb_vies,creature.getVies());
        contentValues.put(attaque,creature.getCapAttq());
        contentValues.put(defense,creature.getCapDef());
        if(creature.getCreaImg()!=null){
            contentValues.put(photo_creature,DbBitmapUtility.getBytes(creature.getCreaImg()));
        }

        long id_cr = db.insert(TABLE_CREATURE,null,contentValues);
        db.close();
        return (int)id_cr;
    }
    //supprimer une creature
    public void deleteCreatureById(int id_creat) throws SQLException{
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_CREATURE,id_creature+ " =?",new String[]{String.valueOf(id_creat)});
            db.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //rechercher une creature
    public Creature findCreatureById(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        String QUERY = "SELECT "+title_creature +
                ", " +nb_vies +
                ", " +attaque +
                ", " +defense +
                ", " +photo_creature +" FROM "+TABLE_CREATURE+" WHERE "+id_creature+" =?"+
                ";";
        Cursor cursor=db.rawQuery(QUERY,new String[]{ String.valueOf(id)});
        Creature creature=new Creature();
        if(cursor.moveToFirst()){
            do {
                creature.setId(cursor.getInt(cursor.getColumnIndex(id_creature)));
                creature.setTitle(cursor.getString(cursor.getColumnIndex(title_creature)));
                creature.setVies(cursor.getInt(cursor.getColumnIndex(nb_vies)));
                creature.setCapAttq(cursor.getInt(cursor.getColumnIndex(attaque)));
                creature.setCapDef(cursor.getInt(cursor.getColumnIndex(defense)));
                if(cursor.getBlob(cursor.getColumnIndex(photo_creature))!=null){
                    creature.setCreaImg(DbBitmapUtility.getImage(cursor.getBlob(cursor.getColumnIndex(photo_creature))));
                }

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return creature;
    }


    //recuperer la liste des creatures depuis la bd
    public ArrayList<Creature> getCreatures(){
        ArrayList<Creature> listCreatures = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String QUERY ="SELECT " +id_creature +
                ", " +title_creature +
                ", " +nb_vies +
                ", " +attaque +
                ", " +defense +
                ", " +photo_creature +" FROM "+TABLE_CREATURE+
                " ;";
        Cursor cursor=db.rawQuery(QUERY,null);
        if(cursor.moveToFirst()){
            do {
                Creature creature = new Creature(cursor.getInt(cursor.getColumnIndex(id_creature)),cursor.getInt(cursor.getColumnIndex(nb_vies)),cursor.getInt(cursor.getColumnIndex(attaque)),cursor.getInt(cursor.getColumnIndex(defense)),cursor.getString(cursor.getColumnIndex(title_creature)));
                if(cursor.getBlob(cursor.getColumnIndex(photo_creature))!=null){
                    creature.setCreaImg(DbBitmapUtility.getImage(cursor.getBlob(cursor.getColumnIndex(photo_creature))));
                }
                listCreatures.add(creature);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listCreatures;
    }

    /************ Equipment Table Methods ****************/

    //inserer un equipement
    public int insertEquipment(Equipement equipement) throws SQLException{
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(title_equipement,equipement.getTitle());
        contentValues.put(contribute_equipement,equipement.getContribution());
        contentValues.put(belongs_to,equipement.getBelongsTo());
        if(equipement.getImg()!=null){
            contentValues.put(photo_equipement,DbBitmapUtility.getBytes(equipement.getImg()));
        }

        long id_eq = db.insert(TABLE_EQUIPEMENT,null,contentValues);
        db.close();
        return (int)id_eq;
    }
    //supprimer un equipment
    public void deleteEquipmentById(int id_equip) throws SQLException{
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_EQUIPEMENT,id_equipement+ " =?",new String[]{String.valueOf(id_equip)});
            db.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //rechercher un equipment
    public Equipement findEquipmentById(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        String QUERY = "SELECT "+title_equipement +
                ", " +photo_equipement +
                ", " +contribute_equipement +
                ", " +belongs_to +
                " FROM "+TABLE_EQUIPEMENT+" WHERE "+id_equipement+" =?"+
                ";";
        Cursor cursor=db.rawQuery(QUERY,new String[]{ String.valueOf(id)});
        Equipement equipement=new Equipement();
        if(cursor.moveToFirst()){
            do {

                equipement.setTitle(cursor.getString(cursor.getColumnIndex(title_equipement)));
                equipement.setContribution(cursor.getInt(cursor.getColumnIndex(contribute_equipement)));
                equipement.setBelongsTo(cursor.getInt(cursor.getColumnIndex(belongs_to)));
                if(cursor.getBlob(cursor.getColumnIndex(photo_creature))!=null){
                    equipement.setImg(DbBitmapUtility.getImage(cursor.getBlob(cursor.getColumnIndex(photo_creature))));
                }

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return equipement;
    }

    //recuperer la liste des equipements depuis la bd
    public ArrayList<Equipement> getEquipments(){
        ArrayList<Equipement> listEquipments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String QUERY ="SELECT " +id_equipement +
                ", " +title_equipement +
                ", " +contribute_equipement +
                ", " +belongs_to +
                ", " +photo_equipement +" FROM "+TABLE_EQUIPEMENT+
                " ;";
        Cursor cursor=db.rawQuery(QUERY,null);
        if(cursor.moveToFirst()){
            do {
                Equipement equipement = new Equipement(cursor.getInt(cursor.getColumnIndex(id_equipement)),cursor.getString(cursor.getColumnIndex(title_equipement)),cursor.getInt(cursor.getColumnIndex(contribute_equipement)),cursor.getInt(cursor.getColumnIndex(belongs_to)));
                if(cursor.getBlob(cursor.getColumnIndex(photo_equipement))!=null){
                    equipement.setImg(DbBitmapUtility.getImage(cursor.getBlob(cursor.getColumnIndex(photo_creature))));
                }

                listEquipments.add(equipement);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listEquipments;
    }
    //recuperer la liste des equipements depuis la bd
    public ArrayList<Equipement> getEquipmentsByCreature(int index){
        ArrayList<Equipement> listEquipments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String QUERY ="SELECT " +id_equipement +
                ", " +title_equipement +
                ", " +contribute_equipement +
                ", " +belongs_to +
                ", " +photo_equipement +" FROM "+TABLE_EQUIPEMENT+
                " WHERE "+belongs_to+" =?"+
                " ;";
        Cursor cursor=db.rawQuery(QUERY,new String[]{ String.valueOf(index)});
        if(cursor.moveToFirst()){
            do {
                Equipement equipement = new Equipement(cursor.getInt(cursor.getColumnIndex(id_equipement)),cursor.getString(cursor.getColumnIndex(title_equipement)),cursor.getInt(cursor.getColumnIndex(contribute_equipement)),cursor.getInt(cursor.getColumnIndex(belongs_to)));
                if(cursor.getBlob(cursor.getColumnIndex(photo_equipement))!=null){
                    equipement.setImg(DbBitmapUtility.getImage(cursor.getBlob(cursor.getColumnIndex(photo_creature))));
                }

                listEquipments.add(equipement);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listEquipments;
    }

}
