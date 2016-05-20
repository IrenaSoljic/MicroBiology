package microbiology.taman.microbiology;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import DatabaseHelp.DatabaseHelper;

public class BacteriaList_activity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    static DatabaseHelper  myDB;
    public static List<BacteriaName> bacteriaList=new ArrayList<BacteriaName>();
    public BacteriaList_activity(){

    }
    public BacteriaList_activity(DatabaseHelper myDB,List<BacteriaName> bacteriaList){
        this.bacteriaList=bacteriaList;
        this.myDB=myDB;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_bacteria_list_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        for (int i = 1; i <= 15; i++) {
//            BacteriaName bt = new BacteriaName("Bacteria Name"+i);
//            bacteriaList.add(bt);
//        }
        mRecyclerView = (RecyclerView) findViewById(R.id.bacteria_list_rv);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.abc_list_divider_mtrl_alpha)));
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new BacteriaListAdapter(bacteriaList,myDB,this);

//         set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);

    }


}
