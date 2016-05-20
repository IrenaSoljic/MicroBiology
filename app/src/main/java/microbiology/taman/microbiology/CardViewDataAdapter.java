package microbiology.taman.microbiology;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class CardViewDataAdapter extends
        RecyclerView.Adapter<CardViewDataAdapter.ViewHolder> {

    private List<Student> stList;
    private List<Student> stList1;
    private List<Student> stList2;

    public CardViewDataAdapter(List<Student> students,List<Student> students1,List<Student> students2) {
        this.stList = students;
        this.stList1= students1;
        this.stList2= students2;
    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_row, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        final int pos = position;
        try {
            viewHolder.property.setText(stList.get(position).getName());
            viewHolder.subProperty1.setText(stList1.get(position).getName());
            viewHolder.subProperty2.setText(stList2.get(position).getName());
            viewHolder.chkSelected1.setChecked(stList1.get(position).isSelected());
            viewHolder.chkSelected1.setTag(stList1.get(position));

        viewHolder.chkSelected1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Student contact = (Student) cb.getTag();

                contact.setSelected(cb.isChecked());
                stList1.get(pos).setSelected(cb.isChecked());

//                Toast.makeText(
//                        v.getContext(),
//                        "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked() + " "+stList1.get(position).getName()+" of"+ stList.get(position).getName()  , Toast.LENGTH_LONG).show();
                try {
                    MainActivity.makeQueryString(stList.get(position).getName(), stList1.get(position).getName(), cb.isChecked(), v.getContext());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            viewHolder.chkSelected2.setChecked(stList2.get(position).isSelected());
            viewHolder.chkSelected2.setTag(stList2.get(position));

        viewHolder.chkSelected2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Student contact = (Student) cb.getTag();

                contact.setSelected(cb.isChecked());
                stList2.get(pos).setSelected(cb.isChecked());

//                Toast.makeText(
//                        v.getContext(),
//                        "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked() +" "+ stList2.get(position).getName()+" of"+ stList.get(position).getName(), Toast.LENGTH_LONG).show();
                try {
                    MainActivity.makeQueryString(stList.get(position).getName(), stList2.get(position).getName(), cb.isChecked(), v.getContext());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return stList.size();
    }



//    // Return the size arraylist
//    @Override
//    public int getItemCount() {
//
//        return 0;
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView property;
        public TextView subProperty1, subProperty2;

        public CheckBox chkSelected1, chkSelected2;
        public Student singlestudent;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            property = (TextView) itemLayoutView.findViewById(R.id.propertyText);

            subProperty1 = (TextView) itemLayoutView.findViewById(R.id.subPropertyText1);
            subProperty2 = (TextView) itemLayoutView.findViewById(R.id.subPropertyText2);
            chkSelected1 = (CheckBox) itemLayoutView.findViewById(R.id.subPropertycheckBox1);
            chkSelected2 = (CheckBox) itemLayoutView.findViewById(R.id.subPropertycheckBox2);



        }

    }

    // method to access in activity after updating selection
    public List<Student> getStudentist1() {

        return stList1;
    }
    public List<Student> getStudentist2() {
        return  stList2;
    }

}
