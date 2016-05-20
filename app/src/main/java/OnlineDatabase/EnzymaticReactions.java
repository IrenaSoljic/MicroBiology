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
 * Created by Xerric on 11/15/2015.
 */
public class EnzymaticReactions extends AsyncTask<String,String,String>  {
    boolean isInserted;
    DatabaseHelper databaseHelper;
    Context context;
    public List<String> differentialAntibioticsList;
  List<String> jsonKeys=new ArrayList<String>();
    List<String>jsonValues=new ArrayList<String>();
    private static String url = "http://sagararyal.com/micro/db_connect.php?action=list_ER";
    public EnzymaticReactions(Context context,List<String> differentialAntibioticsList) {
        this.context = context;
        this.differentialAntibioticsList=differentialAntibioticsList;
    }
    @Override
    protected String doInBackground(String... params) {
        String finalJsonStr;
        int number_of_Bacteria=0;
            JSONArray newArr;
            JSONObject jsonObj;
            JSONObject newob;
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
        DatabaseHelper.setColumnNameForEnzymaticReaction(jsonKeys);
//        Toast.makeText(context,"successfully pulled from ER",Toast.LENGTH_SHORT).show();
        new Fragmentation(context,differentialAntibioticsList,jsonValues).execute();
    }
    //    boolean isInserted;
//    DatabaseHelper databaseHelper;
//    Context context;
//    List<String> jsonKeys=new ArrayList<String>();
//    List<String>jsonValues=new ArrayList<String>();
//    private static String url = "http://travels2nepal.com/micro/db_connect.php?action=list_ER";
//
//    public EnzymaticReactions(DatabaseHelper myDB, Context context) {
//        this.databaseHelper = myDB;
//        this.context = context;
//    }
//    public void getDataER(final List<String> differentialAntibioticsValue){
//        class GetDataJSON extends AsyncTask<String, Void, String> {
//            String finalJsonStr;
//            JSONArray newArr;
//            JSONObject jsonObj;
//            JSONObject newob;
//            @Override
//            protected String doInBackground(String... params) {
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
//
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                Log.d("Key Value", "key: " + jsonKeys + " Value: " + jsonValues);
//                DatabaseHelper.setColumnNameForEnzymaticReaction(jsonKeys);
//                Fragmentation fragmentation= new Fragmentation(databaseHelper,context);
//                fragmentation.getDataF(differentialAntibioticsValue,jsonValues);
//
////                for(int i=0;i<jsonValues.size();i=i+16){
////                    isInserted = databaseHelper.insertData_ER(jsonValues.get(i),jsonValues.get(i+1),jsonValues.get(i+2),jsonValues.get(i+3),jsonValues.get(i+4),jsonValues.get(i+5),
////                            jsonValues.get(i+6),jsonValues.get(i+7),jsonValues.get(i+8),jsonValues.get(i+9),jsonValues.get(i+10),jsonValues.get(i+11),
////                            jsonValues.get(i+12),
////                            jsonValues.get(i+13),jsonValues.get(i+14),jsonValues.get(i+15));
////                    isInserted=true & isInserted;
////                }
////
////                if(isInserted){
////                    Toast.makeText(context, "inserted in ER", Toast.LENGTH_LONG).show();
////                }else{
////                    Toast.makeText(context,"not inserted ER",Toast.LENGTH_LONG).show();
////                }
////                for(int i=0;i<differentialAntibioticsValue.size();i=i+5) {
////                    isInserted = databaseHelper.insertData_DA(differentialAntibioticsValue.get(i), differentialAntibioticsValue.get(i + 1), differentialAntibioticsValue.get(i + 2), differentialAntibioticsValue.get(i + 3),
////                            differentialAntibioticsValue.get(i + 4));
////                    isInserted=true & isInserted;
////                }
////                if(isInserted){
////                    Toast.makeText(context, "inserted in DA", Toast.LENGTH_LONG).show();
////                }else{
////                    Toast.makeText(context,"not inserted DA",Toast.LENGTH_LONG).show();
////                }
//
//            }
//        }
//        GetDataJSON g = new GetDataJSON();
//        g.execute();
//    }

}
