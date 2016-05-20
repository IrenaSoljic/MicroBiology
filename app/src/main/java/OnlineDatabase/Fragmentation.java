package OnlineDatabase;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import DatabaseHelp.DatabaseHelper;

/**
 * Created by Xerric on 11/16/2015.
 */
public class Fragmentation extends AsyncTask<String,String,String> {
    boolean isInserted;
    DatabaseHelper databaseHelper;
    Context context;
    List<String> jsonKeys=new ArrayList<String>();
    List<String>jsonValues=new ArrayList<String>();
    public List<String> differentialAntibioticsList;
    public List<String> enzymaticreactionList;
    private static String url = "http://sagararyal.com/micro/db_connect.php?action=list_F";

    public Fragmentation(Context context,List<String> differentialAntibioticsList,List<String> enzymaticreactionList){
        this.context=context;
        this.differentialAntibioticsList= differentialAntibioticsList;
        this.enzymaticreactionList=enzymaticreactionList;
    }
    @Override
    protected String doInBackground(String... params) {
        String finalJsonStr;
            JSONArray newArr;
            JSONObject jsonObj;
            JSONObject newob;
        int number_of_Bacteria=0;
        OnlineDatabaseAccess onlineDatabaseAccess = new OnlineDatabaseAccess();
        finalJsonStr = onlineDatabaseAccess.jsonParser("http://sagararyal.com/micro/db_connect.php?action=list");

        try {
            jsonObj= new JSONObject(finalJsonStr);
            newArr = jsonObj.getJSONArray("data");
            number_of_Bacteria=newArr.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
                finalJsonStr = onlineDatabaseAccess.jsonParser(url);
                try {
                    jsonObj= new JSONObject(finalJsonStr);
                    newArr = jsonObj.getJSONArray("data");
                    for(int i=0;i<(newArr.length()/number_of_Bacteria);i++) {
                        newob = newArr.getJSONObject(i);
                        Iterator iter = newob.keys();
                        while (iter.hasNext()) {
                            String key = (String) iter.next();
                            jsonKeys.add(key);
                        }
                        Collections.sort(jsonKeys);

                    }
                    for(int i=0;i<newArr.length();i++) {
                        newob = newArr.getJSONObject(i);
                        Iterator <String> iterator = jsonKeys.iterator();
                        while (iterator.hasNext()) {
                            String value = newob.getString(iterator.next());
                            jsonValues.add(value);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("Key Value", "key: " + jsonKeys + " Value: " + jsonValues);
//        Toast.makeText(context,"successfully pulled from F",Toast.LENGTH_SHORT).show();
        DatabaseHelper.setColumnNameForFragmentation(jsonKeys);
        new MicroBiologyTable(context,differentialAntibioticsList,enzymaticreactionList,jsonValues).execute();
    }
    //    boolean isInserted;
//    DatabaseHelper databaseHelper;
//    Context context;
//    List<String> jsonKeys=new ArrayList<String>();
//    List<String>jsonValues=new ArrayList<String>();
//    private static String url = "http://travels2nepal.com/micro/db_connect.php?action=list_F";
//    Fragmentation(DatabaseHelper myDB, Context context){
//        this.databaseHelper = myDB;
//        this.context = context;
//    }
//    public void getDataF(final List<String> differentialAntibioticsValue, final List<String> enzymaticReactionValue){
//        class GetDataJSON extends AsyncTask<String, Void, String> {
//            String finalJsonStr;
//            JSONArray newArr;
//            JSONObject jsonObj;
//            JSONObject newob;
//            @Override
//            protected String doInBackground(String... params) {
//
//                OnlineDatabaseAccess onlineDatabaseAccess = new OnlineDatabaseAccess();
//                finalJsonStr = onlineDatabaseAccess.jsonParser(url);
//                try {
//                    jsonObj= new JSONObject(finalJsonStr);
//                    newArr = jsonObj.getJSONArray("data");
//                    for(int i=0;i<(newArr.length()/5);i++) {
//                        newob = newArr.getJSONObject(i);
//                        Iterator iter = newob.keys();
//                        while (iter.hasNext()) {
//                            String key = (String) iter.next();
//                            jsonKeys.add(key);
//                        }
//                        Collections.sort(jsonKeys);
//
//                    }
//                    for(int i=0;i<newArr.length();i++) {
//                        newob = newArr.getJSONObject(i);
//                        Iterator <String> iterator = jsonKeys.iterator();
//                        while (iterator.hasNext()) {
//                            String value = newob.getString(iterator.next());
//                            jsonValues.add(value);
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                Log.d("Key Value", "key: " + jsonKeys + " Value: " + jsonValues);
//                DatabaseHelper.setColumnNameForFragmentation(jsonKeys);
//                MicroBiologyTable microBiologyTable= new MicroBiologyTable(databaseHelper,context);
//                microBiologyTable.getDataMT(differentialAntibioticsValue,enzymaticReactionValue,jsonValues);
//            }
//        }
//        GetDataJSON g = new GetDataJSON();
//        g.execute();
//    }
}
