package microbiology.taman.microbiology;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import OtherHelperClass.SessionManager;
import OtherHelperClass.checkconnection;

public class SplashScreen extends Activity {

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        session = new SessionManager(getApplicationContext());
        if (session.checkLogin()) {

//            Toast.makeText(getApplicationContext(), "not 1st time", Toast.LENGTH_LONG).show();
            Thread timer = new Thread() {
                public void run() {
                    try {

                        sleep(2000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                        startActivity(intent);
                    }
                }
            };
            timer.start();
        } else if(checkconnection.checkInternetConnection(this)) {
            session.createLoginSession();
//            Toast.makeText(getApplicationContext(), "1st time", Toast.LENGTH_LONG).show();

            Thread timer = new Thread() {
                public void run() {
                    try {

                        sleep(2000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        Intent mainpage = new Intent(SplashScreen.this, Loading.class);
                        startActivity(mainpage);
                    }
                }
            };
            timer.start();
        }else {
                final Dialog dialog = new Dialog(SplashScreen.this);
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




    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }


}
