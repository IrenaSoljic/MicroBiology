package microbiology.taman.microbiology;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xerric on 12/6/2015.
 */
public class BacteriaPropertyListAdapter extends RecyclerView.Adapter<BacteriaPropertyListAdapter.BacteriaPropertyListViewHolder> {
    public List<BacteriaName> propertyNameList= new ArrayList<BacteriaName>();
    public List<BacteriaName> propertyValueList= new ArrayList<BacteriaName>();
    public Context context;
    public BacteriaPropertyListAdapter(List<BacteriaName> propertyNameList, List<BacteriaName> propertyValueList,Context context) {
        this.propertyNameList = propertyNameList;
        this.propertyValueList = propertyValueList;
        this.context=context;
    }
        //testing
    @Override
    public BacteriaPropertyListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_of_bacteria_propertylist, null);
        BacteriaPropertyListViewHolder myViewHolder= new BacteriaPropertyListViewHolder(itemLayoutView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(BacteriaPropertyListViewHolder holder, int position) {
        final int pos = position;
        holder.bacteria_property_Name.setText(propertyNameList.get(pos).getBacteria_name());
        holder.bacteria_property_value.setText(propertyValueList.get(pos).getBacteria_name());
    }

    @Override
    public int getItemCount() {
        return propertyNameList.size();
    }
    public static class BacteriaPropertyListViewHolder extends RecyclerView.ViewHolder {
        public TextView bacteria_property_Name;
        public TextView bacteria_property_value;
        public BacteriaPropertyListViewHolder(View itemView) {
            super(itemView);
            bacteria_property_Name = (TextView) itemView.findViewById(R.id.Property);
            bacteria_property_value= (TextView) itemView.findViewById(R.id.Value);
        }
    }
}
