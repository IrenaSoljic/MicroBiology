package microbiology.taman.microbiology;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import DatabaseHelp.DatabaseHelper;
import OnlineDatabase.DifferentialAntibiotics;
import OtherHelperClass.checkconnection;

public class Loading extends MainActivity {
    TextView downloading;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_loading);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        downloading= (TextView) findViewById(R.id.textView);
        Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/seasrn.ttf");
        downloading.setTypeface(typeFace);
//        DifferentialAntibiotics differentialAntibiotics = new DifferentialAntibiotics(myDB, Loading.this);
//        differentialAntibiotics.getDataDA();
        if(checkconnection.checkInternetConnection(this)) {
            getApplicationContext().deleteDatabase("microbiology_schema.db");
            new DifferentialAntibiotics(getApplicationContext()).execute();
        }
        else{
            final Dialog dialog = new Dialog(Loading.this);
            dialog.setContentView(R.layout.nointernet);
            dialog.setTitle("OOPS!!!");

            Button button = (Button) dialog.findViewById(R.id.dismiss);

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                    finish();
                }
            });
            dialog.show();
        }

    }
//    class GetDataJSON extends AsyncTask<String, Void, String> {
//        DatabaseHelper myDB;
//
//        @Override
//        protected void onPreExecute() {
//            progressBar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            progressBar.setVisibility(View.GONE);
//           Intent maiIntent= new Intent(Loading.this,MainActivity.class);
//            startActivity(maiIntent);
//        }
//    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
