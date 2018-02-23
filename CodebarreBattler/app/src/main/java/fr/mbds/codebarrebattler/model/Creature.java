package fr.mbds.codebarrebattler.model;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import fr.mbds.codebarrebattler.utils.DbBitmapUtility;

/**
 * Created by jperk on 02/02/2018.
 */

public class Creature implements Parcelable {
    public int id;
    public Integer vies;
    public Integer capAttq;
    public  Integer capDef;
    public String title;
    public transient Bitmap creaImg;
    public List<Equipement> listEquipments=new ArrayList<>();

    public Creature(){

    }
    public Creature(int nbvies,int att, int def, String title){
        this.title=title;
        this.capAttq=att;
        this.capDef=def;
        this.vies=nbvies;
    }
    public Creature(int nbvies,int att, int def, String title, Bitmap img){
        this.title=title;
        this.capAttq=att;
        this.capDef=def;
        this.vies=nbvies;
        this.creaImg=img;
    }

    public Creature(int id,int nbvies,int att, int def, String title){
        this.id=id;
        this.title=title;
        this.capAttq=att;
        this.capDef=def;
        this.vies=nbvies;
    }
    public int getId() {
        return id;
    }

    public Integer getVies() {
        if(listEquipments.size()>0){
            for(int i=0;i<listEquipments.size();i++){
                if(listEquipments.get(i).getContribution()==0){
                    this.vies+=1;
                }
            }
        }

        return vies;
    }

    public Integer getCapAttq() {
        if(!listEquipments.isEmpty())
        {
            for(int i=0;i<listEquipments.size();i++){
                if(listEquipments.get(i).getContribution()==1){
                    this.capAttq+=1;
                }
            }
        }

        return capAttq;
    }

    public Integer getCapDef() {
        if(!listEquipments.isEmpty() && this.capDef>1){
            for(int i=0;i<listEquipments.size();i++){
                if(listEquipments.get(i).getContribution()==-1){
                    this.capDef-=1;
                }
            }
        }

        return capDef;
    }

    public String getTitle() {
        return title;
    }

    public Bitmap getCreaImg() {
        return creaImg;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVies(Integer vies) {
        this.vies = vies;
    }

    public void setCapAttq(Integer capAttq) {
        this.capAttq = capAttq;
    }

    public void setCapDef(Integer capDef) {
        this.capDef = capDef;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreaImg(Bitmap creaImg) {
        this.creaImg = creaImg;
    }

    @Override
    public String toString() {
        return this.title;
    }

    @SuppressWarnings("unchecked")
    protected Creature(Parcel in) {
        id = in.readInt();
        vies = in.readInt();
        capAttq = in.readInt();
        capDef = in.readInt();
        title = in.readString();
        listEquipments = (ArrayList<Equipement>) in.readArrayList(Equipement.class.getClassLoader());
        if(this.creaImg!=null ){
            this.creaImg=Bitmap.CREATOR.createFromParcel(in);
        }

    }

    public static final Creator<Creature> CREATOR = new Creator<Creature>() {
        @Override
        public Creature createFromParcel(Parcel in) {
            return new Creature(in);
        }

        @Override
        public Creature[] newArray(int size) {
            return new Creature[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(vies);
        parcel.writeInt(capAttq);
        parcel.writeInt(capDef);
        parcel.writeString(title);
        parcel.writeList(listEquipments);
        if(creaImg!=null){
            creaImg.writeToParcel(parcel,0);
        }

    }
}
