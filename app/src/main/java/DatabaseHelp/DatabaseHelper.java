package DatabaseHelp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import DataGetAndSet.InternalDatabaseGetAndSet;
import microbiology.taman.microbiology.Bacteria;
import microbiology.taman.microbiology.BacteriaName;
import microbiology.taman.microbiology.Loading;
import microbiology.taman.microbiology.MainActivity;

import microbiology.taman.microbiology.Student;

/**
 * Created by Taman on 8/17/2015.
 * offline Database
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    //Database and Table Name
    List<Student> studentList= new ArrayList<Student>();
    List<Student> studentList1= new ArrayList<Student>();
    List<Student> studentList2= new ArrayList<Student>();
    public static final String DATABASE_NAME = "microbiology_schema.db";
    public static final String TABLE_NAME_DA = "differential_antibiotic";
    public static final String TABLE_NAME_ER = "enzymatic_reactions";
    public static final String TABLE_NAME_F = "fragmentation";
    public static final String TABLE_NAME_MT = "microbiology_table";
    String[] column_name1=null;
    String[] column_name=null;
    String [] property_name=null;
    String [] property_name1=null;

    static int  size_DA,size_ER,size_F,size_MT ;
    public static HashMap<String,String> enzymaticReaction= new HashMap<String,String>();
    public static HashMap<String,String> differentialAntibiotics= new HashMap<String,String>();
    public static HashMap<String,String> microBiology= new HashMap<String,String>();
    public static HashMap<String,String> Fermentation= new HashMap<String,String>();

    public static void setColumnNameForEnzymaticReaction(List<String> keys){
        size_ER=keys.size()-1;
        for(int i=0;i<keys.size();i++) {
            enzymaticReaction.put("COL_ER" + i, keys.get(i));
        }
    }

    public static void setColumnNameForFragmentation(List<String> keys){
        for(int i=0;i<keys.size();i++) {
            Fermentation.put("COL_F" + i, keys.get(i));
        }
        size_F=keys.size()-1;
    }

    public static void setColumnNameForMicroBiologyTable(List<String> keys){

        for(int i=0;i<keys.size();i++) {
            microBiology.put("COL_MT" + i, keys.get(i));
        }
        size_MT=keys.size()-1;
    }

    public static void setColumnNameForDifferentialAntibiotics(List<String> keys){
        size_DA=keys.size()-1;
        for(int i=0;i<keys.size();i++) {
            differentialAntibiotics.put("COL_DA" + i, keys.get(i));
        }
    }
    public DatabaseHelper(Context context,boolean load) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating table differential_antibiotic

        db.execSQL("create table " + TABLE_NAME_DA +" ( "+makeDifferentialAntiBioticsTable()+" );");

        //creating table enzymatic_reactions

        db.execSQL("create table " + TABLE_NAME_ER +" ( "+makeEnzymaticReactionTable()+" );");
        //creating table fragmentation

        db.execSQL("create table " + TABLE_NAME_F +" ( "+makeFermentationTable()+" );");
        //creating table microbiology_table

        db.execSQL("create table " + TABLE_NAME_MT +" ( "+makeMicroBiologyTable()+" );");

    }
    public String makeDifferentialAntiBioticsTable(){
        String differentialAntibioticsMakeTableQuery="";
        for(int i=0;i<differentialAntibiotics.size();i++){
            differentialAntibioticsMakeTableQuery=differentialAntibioticsMakeTableQuery +differentialAntibiotics.get("COL_DA" + i)+" varchar(50),";
        }
        differentialAntibioticsMakeTableQuery= differentialAntibioticsMakeTableQuery.substring(0,differentialAntibioticsMakeTableQuery.length()-1);
        return differentialAntibioticsMakeTableQuery;
    }
    public String makeEnzymaticReactionTable(){
        String makeTableQuery="";
        for(int i=0;i<enzymaticReaction.size();i++){
            makeTableQuery=makeTableQuery +enzymaticReaction.get("COL_ER" + i)+" varchar(50),";
        }
        makeTableQuery= makeTableQuery.substring(0,makeTableQuery.length()-1);
        return makeTableQuery;
    }
    public String makeFermentationTable(){
        String makeTableQuery="";
        for(int i=0;i<Fermentation.size();i++){
            makeTableQuery=makeTableQuery +Fermentation.get("COL_F" + i)+" varchar(50),";
        }
        makeTableQuery= makeTableQuery.substring(0,makeTableQuery.length()-1);
        return makeTableQuery;
    }
    public String makeMicroBiologyTable(){
        String makeTableQuery="";
        for(int i=0;i<microBiology.size();i++){
            makeTableQuery=makeTableQuery +microBiology.get("COL_MT" + i)+" varchar(50),";
        }
        makeTableQuery= makeTableQuery.substring(0,makeTableQuery.length()-1);
        return makeTableQuery;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP DATABASE " + DATABASE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_F);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MT);;
        onCreate(db);
    }

    public boolean insertData_DA(List<String> values){//String Bacteria, String Novobiocin, String Bacitracin, String Optochin, String Streptomysin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for(int i=0;i<values.size();i++) {
            contentValues.put(differentialAntibiotics.get("COL_DA"+i), values.get(i));
        }
        long result = db.insert(TABLE_NAME_DA, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertData_ER(List<String> values) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for(int i=0;i<values.size();i++) {
            contentValues.put(enzymaticReaction.get("COL_ER"+i), values.get(i));
        }
        long result = sqLiteDatabase.insert(TABLE_NAME_ER, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertData_F(List<String> values) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for(int i=0;i<values.size();i++) {
            contentValues.put(Fermentation.get("COL_F"+i), values.get(i));
        }
        long result = sqLiteDatabase.insert(TABLE_NAME_F, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }

    public boolean insertData_MT(List<String> values) {
        long result;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        try {

            for(int i=0;i<values.size();i++) {
                contentValues.put(microBiology.get("COL_MT"+i), values.get(i));
            }

             result = sqLiteDatabase.insert(TABLE_NAME_MT, null, contentValues);
        }catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }
    public List<Student> getAllContacts(Context context) {

        int total=size_DA+size_MT+size_F+size_ER;
        // Select All Query
        String selectQuery ="SELECT * FROM microbiology_table Natural join enzymatic_reactions Natural join differential_antibiotic Natural join fragmentation WHERE 1" ;//"SELECT * FROM differential_antibiotic,enzymatic_reactions";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                    column_name= cursor.getColumnNames();
                    column_name1=cursor.getColumnNames();
            } while (cursor.moveToNext());

        }
        cursor.close();
        final List<String> list =  new ArrayList<String>();
        Collections.addAll(list, column_name);

        list.remove("Bacteria");
        list.remove("Bacteria");
        list.remove("Bacteria");
        column_name = list.toArray(new String[list.size()]);

        list.clear();

        Collections.addAll(list, column_name1);
        list.remove("Bacteria");
        list.remove("Bacteria");
        list.remove("Bacteria");
        column_name1 = list.toArray(new String[list.size()]);

        for(int i=0;i<column_name.length;i++){
            String input = column_name[i];
            String regx = "_";
            char[] ca = regx.toCharArray();
            for (char c : ca) {
                input = input.replace(""+c, " ");
            }
            column_name[i]=input;
        }
//        Toast.makeText(context, column_name.length,Toast.LENGTH_LONG).show();
        Log.d("total Key Value", "key: " + column_name.length);// + " Value: " + jsonValues);
        for(int i=0;i<column_name.length;i++){
            Student st = new Student(column_name[i], false);
            studentList.add(st);
        }

        return studentList;
    }
    public List<Student> getCheckBoxDataColumn(Context context,int bacteria_number){

        List<String> stringsList = new ArrayList<String>();
        List<String> secondColumn= new ArrayList<String>();
        List<String> thirdColumn= new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        for(int i=0;i<column_name1.length;i++) {
            String selectQuery = "SELECT " + column_name1[i] + " FROM " + TABLE_NAME_MT +" Natural join "+TABLE_NAME_ER+ " Natural join "+TABLE_NAME_DA +" Natural join "+TABLE_NAME_F +" WHERE " + column_name1[i] + " is not null";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {

                    stringsList.add(cursor.getString(cursor.getColumnIndex(column_name1[i])));

                } while (cursor.moveToNext());

            }
                cursor.close();
//        stringsList.add(cursor.getString(cursor.getColumnIndex(COL_DA1)));
//            Log.d("total Key Value", "size of StringList: " + column_name1[i]);
//           Log.d("total Key Value", "size of StringList: " + stringsList + "size: " + stringsList.size()+ column_name1[i]);// + " Value: " + jsonValues);
        }

        try {
            for (int i = 0; i < stringsList.size(); i = i+bacteria_number) {
                if (stringsList.get(i).equals("Positive") | stringsList.get(i + 1).equals("Positive") | stringsList.get(i + 2).equals("Positive") | stringsList.get(i + 3).equals("Positive") | stringsList.get(i + 4).equals("Positive")) {
                    secondColumn.add("Positive");
                    thirdColumn.add("Negative");
                }else if (stringsList.get(i).equals("Sensitive") | stringsList.get(i + 1).equals("Sensitive") | stringsList.get(i + 2).equals("Sensitive") | stringsList.get(i + 3).equals("Sensitive") | stringsList.get(i + 4).equals("Sensitive")) {
                    secondColumn.add("Sensitive");
                    thirdColumn.add("Non-Sensitive");
                } else if (stringsList.get(i).equals("Resistant") | stringsList.get(i + 1).equals("Resistant") | stringsList.get(i + 2).equals("Resistant") | stringsList.get(i + 3).equals("Resistant") | stringsList.get(i + 4).equals("Resistant")) {
                    secondColumn.add("Resistant");
                    thirdColumn.add("Non-Resistant");
                } else if (stringsList.get(i).equals("Fermentative") | stringsList.get(i + 1).equals("Fermentative") | stringsList.get(i + 2).equals("Fermentative") | stringsList.get(i + 3).equals("Fermentative") | stringsList.get(i + 4).equals("Fermentative")) {
                    secondColumn.add("Fermentative");
                    thirdColumn.add("Non-Fermentative");
                } else if (stringsList.get(i).equals("Alpha") | stringsList.get(i + 1).equals("Alpha") | stringsList.get(i + 2).equals("Alpha") | stringsList.get(i + 3).equals("Alpha") | stringsList.get(i + 4).equals("Alpha")) {
                    secondColumn.add("Alpha");
                    thirdColumn.add("Beta");
                } else if (stringsList.get(i).equals("Flagellated") | stringsList.get(i + 1).equals("Flagellated") | stringsList.get(i + 2).equals("Flagellated") | stringsList.get(i + 3).equals("Flagellated") | stringsList.get(i + 4).equals("Flagellated")) {
                    secondColumn.add("Flagellated");
                    thirdColumn.add("Non-Flagellated");
                } else if (stringsList.get(i).equals("Non-Sporing") | stringsList.get(i + 1).equals("Non-Sporing") | stringsList.get(i + 2).equals("Non-Sporing") | stringsList.get(i + 3).equals("Non-Sporing") | stringsList.get(i + 4).equals("Non-Sporing")) {
                    secondColumn.add("Sporing");
                    thirdColumn.add("Non-Sporing");
                } else if (stringsList.get(i).equals("Capsulated") | stringsList.get(i + 1).equals("Capsulated") | stringsList.get(i + 2).equals("Capsulated") | stringsList.get(i + 3).equals("Capsulated") | stringsList.get(i + 4).equals("Capsulated")) {
                    secondColumn.add("Capsulated");
                    thirdColumn.add("Non-Capsulated");
                } else if (stringsList.get(i).equals("Motile") | stringsList.get(i + 1).equals("Motile") | stringsList.get(i + 2).equals("Motile") | stringsList.get(i + 3).equals("Motile") | stringsList.get(i + 4).equals("Motile")) {
                    secondColumn.add("Motile");
                    thirdColumn.add("Non-Motile");
                } else if (stringsList.get(i).equals("Cocci") | stringsList.get(i + 1).equals("Cocci") | stringsList.get(i + 2).equals("Cocci") | stringsList.get(i + 3).equals("Cocci") | stringsList.get(i + 4).equals("Cocci")) {
                    secondColumn.add("Cocci");
                    thirdColumn.add("Rods");
                } else if (stringsList.get(i).equals("Negative") | stringsList.get(i + 1).equals("Negative") | stringsList.get(i + 2).equals("Negative") | stringsList.get(i + 3).equals("Negative") | stringsList.get(i + 4).equals("Negative")) {
                    secondColumn.add("Positive");
                    thirdColumn.add("Negative");
                } else {
                    secondColumn.add("Positive");
                    thirdColumn.add("Negative");
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d("total Key Value", "2nd " +secondColumn );
        Log.d("total Key Value", "3rd " +thirdColumn );

        for(int i=0;i<secondColumn.size();i++){
            Student st = new Student(secondColumn.get(i), false);
            studentList1.add(st);
        }
        for(int i=0;i<thirdColumn.size();i++){
            Student st = new Student(thirdColumn.get(i), false);
            studentList2.add(st);
        }


        return studentList1;
    }
    public List<String > finalQuery(String query){
        query= query.substring(0,query.length()-4);
        String selectQuery = "SELECT * FROM "+TABLE_NAME_MT +" Natural join "+TABLE_NAME_ER + " Natural join "+TABLE_NAME_DA +" Natural join "+TABLE_NAME_F+" WHERE "+query+" ;";
        List<String> bacteria = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                bacteria.add(cursor.getString(cursor.getColumnIndex("Bacteria")));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return bacteria;
    }
    public List<BacteriaName> getNameOfAllBacteriaForBacteriaList(Context context){
        String bacteria = null;
        List<BacteriaName> bacteriaList= new ArrayList<BacteriaName>();
        String selectQuery = "SELECT Bacteria from fragmentation Natural join microbiology_table Natural join differential_antibiotic Natural join enzymatic_reactions where 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                 bacteria= cursor.getString(0);
                BacteriaName bacteria1= new BacteriaName(bacteria);
                bacteriaList.add(bacteria1);

            }while(cursor.moveToNext());
        }
        cursor.close();

        return bacteriaList;
    }
    public List<BacteriaName> getBacteriaPropertyAccordingToBacteria(Context context,String BacteriaName){
        List<BacteriaName> bacteriaPropertyList= new ArrayList<BacteriaName>();
        String selectQuery = "SELECT * from fragmentation Natural join microbiology_table Natural join differential_antibiotic Natural join enzymatic_reactions where Bacteria='"+BacteriaName+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                property_name= cursor.getColumnNames();

            } while (cursor.moveToNext());

        }
        cursor.close();
        final List<String> list =  new ArrayList<String>();
        Collections.addAll(list, property_name);

        list.remove("Bacteria");

        property_name = list.toArray(new String[list.size()]);
        property_name1= list.toArray(new String[list.size()]);
        for(int i=0;i<property_name1.length;i++){
            String input = property_name1[i];
            String regx = "_";
            char[] ca = regx.toCharArray();
            for (char c : ca) {
                input = input.replace(""+c, " ");
            }
            property_name1[i]=input;
        }

        for(int i=0;i<property_name1.length;i++) {

                BacteriaName bacteria2 = new BacteriaName(property_name1[i]);
                bacteriaPropertyList.add(bacteria2);

        }
        return bacteriaPropertyList;
    }
    public List<BacteriaName> getBacteriaPropertyValueAccordingToBacteria(Context context,String bacteriaName){
        String propertyValue=null;
        List<BacteriaName> propertyValueList= new ArrayList<BacteriaName>();
        SQLiteDatabase db = this.getWritableDatabase();
        for(int i=0;i<property_name.length;i++){
            String selectQuery = "SELECT " + property_name[i] + " FROM " + TABLE_NAME_DA +" Natural join "+TABLE_NAME_ER+ " Natural join "+TABLE_NAME_MT +" Natural join "+TABLE_NAME_F +" WHERE Bacteria= '"+bacteriaName+"'";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {

                    propertyValue=cursor.getString(cursor.getColumnIndex(property_name[i]));
                    BacteriaName bacteria2= new BacteriaName(propertyValue);
                    propertyValueList.add(bacteria2);

                } while (cursor.moveToNext());

            }
        }
        return propertyValueList;
    }

    public List<Student> getThirdList(Context context){
        return studentList2;
    }
}
