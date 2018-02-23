package fr.mbds.codebarrebattler.utils;

import android.content.Context;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * Created by jperk on 07/02/2018.
 */

public class BarcodeParser {
    public HashMap<String, Integer> tableConversion;
    public Integer my_val_sum=0;

    public BarcodeParser(Context ctx){
        tableConversion = new HashMap<String, Integer>();
        tableConversion.put("A",0);
        tableConversion.put("B",1);
        tableConversion.put("C",2);
        tableConversion.put("D",3);
        tableConversion.put("E",4);
        tableConversion.put("F",5);
        tableConversion.put("G",6);
        tableConversion.put("H",7);
        tableConversion.put("I",8);
        tableConversion.put("J",9);
        tableConversion.put("K",10);
        tableConversion.put("L",11);
        tableConversion.put("M",12);
        tableConversion.put("N",13);
        tableConversion.put("O",14);
        tableConversion.put("P",15);
        tableConversion.put("Q",16);
        tableConversion.put("R",17);
        tableConversion.put("S",18);
        tableConversion.put("T",19);
        tableConversion.put("U",20);
        tableConversion.put("V",21);
        tableConversion.put("W",22);
        tableConversion.put("X",23);
        tableConversion.put("Y",24);
        tableConversion.put("Z",25);
        tableConversion.put("0",26);
        tableConversion.put("1",27);
        tableConversion.put("2",28);
        tableConversion.put("3",29);
        tableConversion.put("4",30);
        tableConversion.put("5",31);
        tableConversion.put("6",32);
        tableConversion.put("7",33);
        tableConversion.put("8",34);
        tableConversion.put("9",35);
    }

    public HashMap<String, Integer> getTableConversion() {
        return tableConversion;
    }


    public void setTableConversion(HashMap<String, Integer> tableConversion) {
        this.tableConversion = tableConversion;
    }

    public Integer getMy_val_sum() {
        return my_val_sum;
    }
    private Integer pow(Integer value, int poids){
        Integer res=1;
        for(int i=0;i<poids;i++){
            res=res*poids;
        }
        return res;
    }

    public void parseToInt(String bcResult){
        char [] bcChar = bcResult.toCharArray();
        Integer internalVal=0;
        for(int i=0;i<bcChar.length;i++){
            if( (String.valueOf(bcChar[i])!="(") && (String.valueOf(bcChar[i])!=")") && (String.valueOf(bcChar[i])!="#") ){
                internalVal=internalVal+((pow(35,i))*tableConversion.get(String.valueOf(bcChar[i])));
            }
            //my_val_sum=my_val_sum.add(tableConversion.get(String.valueOf(bcChar[i])).multiply(BigInteger.valueOf(35).pow(i)));


            //my_val_sum=((BigInteger.valueOf(35).pow(i)).multiply(tableConversion.get(bcChar[i]))).add(initval);
        }
        my_val_sum=Math.abs(internalVal);

    }



}
