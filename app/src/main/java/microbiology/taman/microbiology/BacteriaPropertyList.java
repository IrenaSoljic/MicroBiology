package microbiology.taman.microbiology;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BacteriaPropertyList extends AppCompatActivity implements Serializable{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    public  List<BacteriaName> bacteriaPropertyList=new ArrayList<BacteriaName>();
    public  List<BacteriaName> bacteriaValueList=new ArrayList<BacteriaName>();
    public BacteriaPropertyList(){

    }
    public BacteriaPropertyList(List<BacteriaName> bacteriaPropertyList,List<BacteriaName> bacteriaValueList){
        this.bacteriaPropertyList=bacteriaPropertyList;
        this.bacteriaValueList= bacteriaValueList;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bacteria_property_list);
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
        Intent intent = getIntent();
        String bacteria = intent.getExtras().getString("Bacteria");
        getSupportActionBar().setTitle(bacteria);
        DataWrapper dw = (DataWrapper) getIntent().getSerializableExtra("data1");
        bacteriaPropertyList= dw.getParliaments();
        DataWrapper dw1 = (DataWrapper) getIntent().getSerializableExtra("data2");
        bacteriaValueList=dw1.getParliaments();
        int pos= bacteriaPropertyList.indexOf("Bacteria");
//        bacteriaPropertyList.remove("Bacteria");
//        bacteriaValueList.remove(pos);
//        for (int i = 1; i <= 15; i++) {
//            BacteriaName bt = new BacteriaName("Bacteria Property"+i);
//            bacteriaPropertyList.add(bt);
//        }
//        for (int i = 1; i <= 15; i++) {
//            BacteriaName bt = new BacteriaName("Bacteria Value"+i);
//            bacteriaValueList.add(bt);
//        }
        mRecyclerView = (RecyclerView) findViewById(R.id.bacteria_property_list_rv);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.abc_list_divider_mtrl_alpha)));
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new BacteriaPropertyListAdapter(bacteriaPropertyList,bacteriaValueList,this);

//         set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);


    }

}
