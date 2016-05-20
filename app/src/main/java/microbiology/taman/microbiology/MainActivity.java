package microbiology.taman.microbiology;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DatabaseHelp.DatabaseHelper;
import OtherHelperClass.SessionManager;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    static DatabaseHelper  myDB;
    public static Context mContext;
    private ProgressDialog pDialog;
    public static TextView json,staticText;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
    Boolean offlineDatabase = false, onlineDatabase = false;
    String myJSON;
    String log = null;
    String result;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static Button btnSelection;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String firstUpdate = "firstUpdate";
    SharedPreferences sharedpreferences;
    static List<String> propertyList= new ArrayList<String>();
    static List<String> subPropertyList= new ArrayList<String>();

    // URL to get contacts JSON
    private static String url = "http://travels2nepal.com/micro/db_connect.php?action=list_DA";
    public static List<Student> studentList=new ArrayList<Student>();
    public List<Student> studentList1= new ArrayList<Student>();
    public List<Student> studentList2= new ArrayList<Student>();
    public List<BacteriaName> bacteriaNameList= new ArrayList<BacteriaName>();
    SessionManager session;
    static String query="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

     toolbar.setLogo(R.drawable.logo);
        session = new SessionManager(getApplicationContext());
//        Intent mintent = getIntent();
//        int number_of_bacteria = mintent.getIntExtra("number_of_bacteria",0);//.getInt("number_of_bacteria");
        if (session.checkLogin()) {
            try {
                myDB = new DatabaseHelper(this, false);
                bacteriaNameList=myDB.getNameOfAllBacteriaForBacteriaList(this);
                studentList = myDB.getAllContacts(this);
                studentList1=myDB.getCheckBoxDataColumn(this,bacteriaNameList.size());
                studentList2=myDB.getThirdList(this);

                BacteriaList_activity bacteriaList_activity= new BacteriaList_activity(myDB,bacteriaNameList);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        onlineDatabase = true;

        btnSelection = (Button) findViewById(R.id.btnShow);
        staticText= (TextView) findViewById(R.id.textView2);
        staticText.setText("Possible Bacteria");
//        DataWrapper dw = (DataWrapper) getIntent().getSerializableExtra("data");
//        studentList=dw.getParliaments();
        mContext = getBaseContext();
//        for (int i = 1; i <= 15; i++) {
//            Student st = new Student("property " + i, false);
//            studentList.add(st);
//        }

//        for (int i = 1; i <= 19; i++) {
//            Student st = new Student("subproperty " + i, false);
//            studentList1.add(st);
//        }


//        studentList2 = new ArrayList<Student>();
//        for (int i = 1; i <= 15; i++) {
//            Student st = new Student("subproperty " + i, false);
//            studentList2.add(st);
//        }
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//         create an Object for Adapter
     mAdapter = new CardViewDataAdapter(studentList, studentList1, studentList2);

//         set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);
        btnSelection.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String data = "";
                List<Student> btList1 = ((CardViewDataAdapter) mAdapter).getStudentist1();
                List<Student> btList2 = ((CardViewDataAdapter) mAdapter).getStudentist2();
                for (int i = 0; i < btList1.size(); i++) {
                    Student singleproperty1 = btList1.get(i);
                    Student singleproperty2 = btList2.get(i);
                    if (singleproperty1.isSelected() == true) {
                        data = data + "\n" + singleproperty1.getName().toString();
                    } else if (singleproperty2.isSelected() == true) {
                        data = data + "\n" + singleproperty2.getName().toString();
                    }
                }
//                Toast.makeText(MainActivity.this,"Selected Students: \n" + data, Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public static Context getContext() {
        return mContext;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_list) {
//            Toast.makeText(this,"Bacteria List",Toast.LENGTH_SHORT).show();
            Intent bacteria_list_intent =new Intent(MainActivity.this, BacteriaList_activity.class);
            startActivity(bacteria_list_intent);
        }else if(id==R.id.action_aboutUs){
//            Toast.makeText(this,"About US",Toast.LENGTH_SHORT).show();
            about_us();

        }else if (id==R.id.action_update){
            Toast.makeText(this,"Update",Toast.LENGTH_SHORT).show();
            Intent update= new Intent(MainActivity.this,Loading.class);
            startActivity(update);
        }

        return super.onOptionsItemSelected(item);
    }
//    public static void sendList(List<Student> studentList3){
//        studentList2=studentList3;
//    }

    public static List<NameValuePair> nameValuePairListCreator() {
        BasicNameValuePair action = new BasicNameValuePair("action", "list_DA");
//        BasicNameValuePair q = new BasicNameValuePair("q", "E. Coli");
        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
        nameValuePairList.add(action);
//        nameValuePairList.add(q);
        return nameValuePairList;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public static void makeQueryString(String property,String subProperty,boolean checkBoxState,Context context) {
        String possibleStr="Possible Bacteria";

        String query= "";
        String resultStr="";
        btnSelection.setText("");
            String input = property;
            String regx = " ";
            char[] ca = regx.toCharArray();
            for (char c : ca) {
                input = input.replace(""+c, "_");
            }
        property=input;

        if(checkBoxState){
            propertyList.add(property);
            subPropertyList.add(subProperty);
        }else{
            propertyList.remove(property);
            subPropertyList.remove(subProperty);
        }

//        Toast.makeText(context,subProperty +" of " + property +" "+" is "+checkBoxState,Toast.LENGTH_LONG).show();
        for(int i=0;i<propertyList.size();i++){
             query= query + propertyList.get(i)+" = "+"'"+ subPropertyList.get(i)+"'"+" AND ";
        }
//        Toast.makeText(context,query,Toast.LENGTH_LONG).show();
        myDB = new DatabaseHelper(context, false);
        List<String> result=myDB.finalQuery(query);
        for(int i=0;i<result.size();i++){
            resultStr= resultStr + result.get(i)+" ,";
        }
        try {
            resultStr = resultStr.substring(0, resultStr.length() - 1);
        }catch (Exception e){
            e.printStackTrace();
        }
        btnSelection.setText(resultStr);
    }
    public void about_us(){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.aboutus);
        dialog.setTitle("ABOUT US");


        TextView developer,concept;
        developer= (TextView) dialog.findViewById(R.id.developer_name);
        concept= (TextView) dialog.findViewById(R.id.name);
//        developer.setText(Html.fromHtml( "<a href=\"http://www.tamanneupane.com.np\">Taman Neupane</a> "));
//        developer.setMovementMethod(LinkMovementMethod.getInstance());
        Button button = (Button) dialog.findViewById(R.id.dismissabout);
        developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.tamanneupane.com.np"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        concept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://sagararyal.com"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                
            }
        });
        dialog.show();

    }
}
