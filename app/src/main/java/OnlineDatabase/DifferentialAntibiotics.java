package OnlineDatabase;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import DatabaseHelp.DatabaseHelper;
import microbiology.taman.microbiology.Bacteria;
import microbiology.taman.microbiology.MainActivity;
import microbiology.taman.microbiology.Student;

/**
 * Created by Xerric on 11/15/2015.
 */
public class DifferentialAntibiotics extends AsyncTask<String,String,String> {
    public Context context;
    boolean isInserted;
    DatabaseHelper databaseHelper;

    private static String url = "http://sagararyal.com/micro/db_connect.php?action=list_DA";
    List<String>jsonKeys=new ArrayList<String>();
    List<String>jsonValues=new ArrayList<String>();
    public List<Bacteria> propertyList;


    public DifferentialAntibiotics(Context context) {
        this.context = context;
    }



    @Override
    protected String doInBackground(String... params) {
        String finalJsonStr;
            JSONArray newArr;
        int number_of_Bacteria = 0;
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
                    for(int i=0;i<newArr.length()/number_of_Bacteria;i++) {
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
        DatabaseHelper.setColumnNameForDifferentialAntibiotics(jsonKeys);
//        Toast.makeText(context,"successfully pulled from DA",Toast.LENGTH_SHORT).show();
//        for(int i=0;i<jsonValues.size();i=i+5) {
//            isInserted = databaseHelper.insertData_DA(jsonValues.get(i), jsonValues.get(i + 1), jsonValues.get(i + 2), jsonValues.get(i + 3),
//                    jsonValues.get(i + 4));
//            isInserted=true & isInserted;
//        }
//        if(isInserted){
//            Toast.makeText(context, "inserted in DA", Toast.LENGTH_LONG).show();
//        }else{
//            Toast.makeText(context,"not inserted DA",Toast.LENGTH_LONG).show();
//        }
        new EnzymaticReactions(context,jsonValues).execute();

    }
//    private RecyclerView mRecyclerView;
//    private RecyclerView.Adapter mAdapter;
//    private RecyclerView.LayoutManager mLayoutManager;
//
//    boolean isInserted;
//    DatabaseHelper databaseHelper;
//    Context context;
//    private static String url = "http://travels2nepal.com/micro/db_connect.php?action=list_DA";
//    List<String>jsonKeys=new ArrayList<String>();
//    List<String>jsonValues=new ArrayList<String>();
//    public List<Bacteria> propertyList;
//
//
//    public DifferentialAntibiotics(DatabaseHelper myDB, Context context){
//        this.databaseHelper=myDB;
//        this.context=context;
//    }
//
//
//
//        public class GetDataJSON extends AsyncTask<List<Bacteria>, Void, List<Bacteria >> {
//            String finalJsonStr;
//            JSONArray newArr;
//            JSONObject jsonObj;
//            JSONObject newob;
//
//            private Context context;
//            public  GetDataJSON(Context context){
//                this.context = context;
//            }
//
//        @Override
//            public List<Bacteria> doInBackground(List<Bacteria>... params) {
//                OnlineDatabaseAccess onlineDatabaseAccess = new OnlineDatabaseAccess();
//
//                finalJsonStr = onlineDatabaseAccess.jsonParser(url);
//                try {
//                    jsonObj= new JSONObject(finalJsonStr);
//                    newArr = jsonObj.getJSONArray("data");
//                    for(int i=0;i<newArr.length()/5;i++) {
//                        newob = newArr.getJSONObject(i);
//                        Iterator iter = newob.keys();
//                        while (iter.hasNext()) {
//                            String key = (String) iter.next();
//                          jsonKeys.add(key);
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
//                return propertyList;
//            }
//
//            @Override
//            protected void onPostExecute(List<Bacteria> strings) {
//                Log.d("Key Value", "key: " + jsonKeys + " Value: " + jsonValues);
//                DatabaseHelper.setColumnNameForDifferentialAntibiotics(jsonKeys);
//                EnzymaticReactions enzymaticReactions= new EnzymaticReactions(databaseHelper,context);
//                enzymaticReactions.getDataER(jsonValues);
//            }
//        }

    }
//                isInserted=databaseHelper.insertData_DA("Streptococcus pneumoniae","","","Sensitive","");
//                isInserted=databaseHelper.insertData_DA("Staphylococcus aureus","Resistant","","","");
//                isInserted=databaseHelper.insertData_DA("E. coli","","","","");
//                isInserted=databaseHelper.insertData_DA("Streptococcus pyogens","","Sensitive","","");
//                isInserted=databaseHelper.insertData_DA("Vibrio cholerae","","","","");
