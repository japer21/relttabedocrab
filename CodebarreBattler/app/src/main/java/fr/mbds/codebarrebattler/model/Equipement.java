package fr.mbds.codebarrebattler.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jperk on 02/02/2018.
 */

public class Equipement implements Parcelable {
    public int id;
    public String title;
    public int contribution; // -1:malus defense,0:bonus vie,1:bonus attaque
    public int belongsTo;
    public Bitmap img;

    public Equipement(){

    }
    public Equipement( String t, int c, int bt){
        this.contribution=c;
        this.title=t;
        this.belongsTo=bt;
    }
    public Equipement(int id, String t, int c,int bt){
        this.id=id;
        this.title=t;
        this.contribution=c;
        this.belongsTo=bt;
    }

    public Equipement( String t, int c,Bitmap image, int bt){
        this.title=t;
        this.contribution=c;
        this.belongsTo=bt;
        this.img=image;
    }
    protected Equipement(Parcel in) {
        id = in.readInt();
        title = in.readString();
        contribution = in.readInt();
        belongsTo=in.readInt();
        img=Bitmap.CREATOR.createFromParcel(in);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Bitmap getImg() {
        return img;
    }

    public int getContribution() {
        return contribution;
    }

    public int getBelongsTo() {
        return belongsTo;
    }

    public void setContribution(int contribution) {
        this.contribution = contribution;
    }

    public void setBelongsTo(int belongsTo) {
        this.belongsTo = belongsTo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return this.title;
    }

    public static final Creator<Equipement> CREATOR = new Creator<Equipement>() {
        @Override
        public Equipement createFromParcel(Parcel in) {
            return new Equipement(in);
        }

        @Override
        public Equipement[] newArray(int size) {
            return new Equipement[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeInt(contribution);
        parcel.writeInt(belongsTo);
        //img.writeToParcel(parcel,i);
        img.writeToParcel(parcel,0);
    }
}
