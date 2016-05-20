package microbiology.taman.microbiology;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import DatabaseHelp.DatabaseHelper;

/**
 * Created by Xerric on 12/4/2015.
 *
 */

public class BacteriaListAdapter extends RecyclerView.Adapter<BacteriaListAdapter.MyViewHolder> implements Serializable {
    public List<BacteriaName> propertyNameList= new ArrayList<BacteriaName>();
    public List<BacteriaName> propertyValueList= new ArrayList<BacteriaName>();
    private List<BacteriaName> btList;
    public DatabaseHelper myDB;
    public Context context;
    public BacteriaListAdapter(List<BacteriaName> bacterias, DatabaseHelper myDB,Context context) {
        this.btList = bacterias;
        this.myDB=myDB;
        this.context=context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bacterialist_single_row, null);
        MyViewHolder myViewHolder= new MyViewHolder(itemLayoutView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final int pos = position;
        holder.bacteria_Name.setText(btList.get(pos).getBacteria_name());
        holder.bacteria_Name.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),btList.get(pos).getBacteria_name(),Toast.LENGTH_SHORT).show();
                propertyNameList=myDB.getBacteriaPropertyAccordingToBacteria(context, btList.get(pos).getBacteria_name());
                propertyValueList=myDB.getBacteriaPropertyValueAccordingToBacteria(context, btList.get(pos).getBacteria_name());
                Log.d("total Key Value", "size of property and value: " + propertyNameList.size()+" "+propertyValueList.size());
                BacteriaPropertyList bacteriaPropertyList= new BacteriaPropertyList(propertyNameList,propertyValueList);
                Intent intent = new Intent(context,BacteriaPropertyList.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("data1", new DataWrapper(propertyNameList));
                intent.putExtra("data2", new DataWrapper(propertyValueList));
                intent.putExtra("Bacteria",btList.get(pos).getBacteria_name());
//        intent.putExtra("data", new DataWrapper(list));
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return btList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView bacteria_Name;
        public MyViewHolder(View itemView) {
            super(itemView);
            bacteria_Name = (TextView) itemView.findViewById(R.id.bac_name);
        }
    }

}

