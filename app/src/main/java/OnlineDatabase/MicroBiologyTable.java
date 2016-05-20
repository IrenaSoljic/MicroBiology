package OnlineDatabase;

import android.content.Context;
import android.content.Intent;
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
import microbiology.taman.microbiology.MainActivity;

/**
 * Created by Xerric on 11/18/2015.
 */
public class MicroBiologyTable extends AsyncTask<String,String,String> {

        boolean isInserted;
    DatabaseHelper databaseHelper;
    Context context;
    List<String> jsonKeys=new ArrayList<String>();
    List<String>jsonValues=new ArrayList<String>();
    public List<String> differentialAntibioticsList;
    public List<String> enzymaticreactionList;
    public List<String> fragmentationList;
    private static String url = "http://sagararyal.com/micro/db_connect.php?action=list_MT";
    int number_of_Bacteria;
    public MicroBiologyTable(Context context, List<String> differentialAntibioticsList, List<String> enzymaticreactionList, List<String> fragmentationList) {
        this.context = context;
        this.differentialAntibioticsList = differentialAntibioticsList;
        this.enzymaticreactionList = enzymaticreactionList;
        this.fragmentationList = fragmentationList;
    }

    @Override
    protected String doInBackground(String... params) {
        String [] mtTableProperties={"Bacteria","Gram_Staining","Shape","Motility","Capsule","Spore","Flagella"
        ,"Catalase","Oxidase","MR","VP","OF","Indole","Citrate","Urease","Nitrate_Reduction","H2S","Gas","PYR","CAMP","Gelatin","Niacin","Coagulase"
        ,"Hemolysis","Nagler","String_Test","Pigment","Bile_Solubility"};
        String finalJsonStr;
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
        Log.d("number_of_Bacteria", "number_of_Bacteria: " + number_of_Bacteria);
        DatabaseHelper.setColumnNameForMicroBiologyTable(jsonKeys);
//        Toast.makeText(context,"successfully pulled from MT",Toast.LENGTH_SHORT).show();
        databaseHelper = new DatabaseHelper(context,true);
//                for(int i=0;i<enzymaticreactionList.size();i=i+16){
//                    isInserted = databaseHelper.insertData_ER(enzymaticreactionList.get(i),enzymaticreactionList.get(i+1),enzymaticreactionList.get(i+2),enzymaticreactionList.get(i+3),enzymaticreactionList.get(i+4),enzymaticreactionList.get(i+5),
//                            enzymaticreactionList.get(i+6),enzymaticreactionList.get(i+7),enzymaticreactionList.get(i+8),enzymaticreactionList.get(i+9),enzymaticreactionList.get(i+10),enzymaticreactionList.get(i+11),
//                            enzymaticreactionList.get(i+12),
//                            enzymaticreactionList.get(i+13),enzymaticreactionList.get(i+14),enzymaticreactionList.get(i+15));
//                    isInserted=true & isInserted;
//                }
//
//                for(int i=0;i<differentialAntibioticsList.size();i=i+5) {
//                    isInserted = databaseHelper.insertData_DA(differentialAntibioticsList.get(i), differentialAntibioticsList.get(i + 1), differentialAntibioticsList.get(i + 2), differentialAntibioticsList.get(i + 3),
//                            differentialAntibioticsList.get(i + 4));
//                    isInserted=true & isInserted;
//                }

//        isInserted = databaseHelper.insertData_MT(jsonValues.get(0), jsonValues.get(1),jsonValues.get(2),jsonValues.get(3),jsonValues.get(4),jsonValues.get(5),jsonValues.get(6),jsonValues.get(7),jsonValues.get(8),jsonValues.get(9),jsonValues.get(10),jsonValues.get(11),jsonValues.get(12),jsonValues.get(13),jsonValues.get(14),jsonValues.get(15),jsonValues.get(16),jsonValues.get(17),jsonValues.get(18),jsonValues.get(19),jsonValues.get(20),jsonValues.get(21),jsonValues.get(22),jsonValues.get(23),jsonValues.get(24),jsonValues.get(25),jsonValues.get(26),jsonValues.get(27));
//        isInserted = databaseHelper.insertData_MT(jsonValues.get(28), jsonValues.get(29),jsonValues.get(30),jsonValues.get(31),jsonValues.get(32),jsonValues.get(33),jsonValues.get(34),jsonValues.get(35),jsonValues.get(36),jsonValues.get(37),jsonValues.get(38),jsonValues.get(39),jsonValues.get(40),jsonValues.get(41),jsonValues.get(42),jsonValues.get(43),jsonValues.get(44),jsonValues.get(45),jsonValues.get(46),jsonValues.get(47),jsonValues.get(48),jsonValues.get(49),jsonValues.get(50),jsonValues.get(51),jsonValues.get(52),jsonValues.get(53),jsonValues.get(54),jsonValues.get(55));
//        isInserted = databaseHelper.insertData_MT(jsonValues.get(56), jsonValues.get(57),jsonValues.get(58),jsonValues.get(59),jsonValues.get(60),jsonValues.get(61),jsonValues.get(62),jsonValues.get(63),jsonValues.get(64),jsonValues.get(65),jsonValues.get(66),jsonValues.get(67),jsonValues.get(68),jsonValues.get(69),jsonValues.get(70),jsonValues.get(71),jsonValues.get(72),jsonValues.get(73),jsonValues.get(74),jsonValues.get(75),jsonValues.get(76),jsonValues.get(77),jsonValues.get(78),jsonValues.get(79),jsonValues.get(80),jsonValues.get(81),jsonValues.get(82),jsonValues.get(83));
//        isInserted = databaseHelper.insertData_MT(jsonValues.get(84), jsonValues.get(85),jsonValues.get(86),jsonValues.get(87),jsonValues.get(88),jsonValues.get(89),jsonValues.get(90),jsonValues.get(91),jsonValues.get(92),jsonValues.get(93),jsonValues.get(94),jsonValues.get(95),jsonValues.get(96),jsonValues.get(97),jsonValues.get(98),jsonValues.get(99),jsonValues.get(100),jsonValues.get(101),jsonValues.get(102),jsonValues.get(103),jsonValues.get(104),jsonValues.get(105),jsonValues.get(106),jsonValues.get(107),jsonValues.get(108),jsonValues.get(109),jsonValues.get(110),jsonValues.get(111));
//        isInserted = databaseHelper.insertData_MT(jsonValues.get(112), jsonValues.get(113),jsonValues.get(114),jsonValues.get(115),jsonValues.get(116),jsonValues.get(117),jsonValues.get(118),jsonValues.get(119),jsonValues.get(120),jsonValues.get(121),jsonValues.get(122),jsonValues.get(123),jsonValues.get(124),jsonValues.get(125),jsonValues.get(126),jsonValues.get(127),jsonValues.get(128),jsonValues.get(129),jsonValues.get(130),jsonValues.get(131),jsonValues.get(132),jsonValues.get(133),jsonValues.get(134),jsonValues.get(135),jsonValues.get(136),jsonValues.get(137),jsonValues.get(138),jsonValues.get(139));
//        }
//        isInserted = databaseHelper.insertData_F(fragmentationList.get(0), fragmentationList.get(1), fragmentationList.get(2),fragmentationList.get(3), fragmentationList.get(4), fragmentationList.get(5), fragmentationList.get(6), fragmentationList.get(7), fragmentationList.get(8), fragmentationList.get(9), fragmentationList.get(10), fragmentationList.get(11), fragmentationList.get(12),fragmentationList.get(13), fragmentationList.get(14), fragmentationList.get(15), fragmentationList.get(16));
//        isInserted = databaseHelper.insertData_F(fragmentationList.get(17), fragmentationList.get(18), fragmentationList.get(19),fragmentationList.get(20), fragmentationList.get(21), fragmentationList.get(22), fragmentationList.get(23), fragmentationList.get(24), fragmentationList.get(25), fragmentationList.get(26), fragmentationList.get(27), fragmentationList.get(28), fragmentationList.get(29),fragmentationList.get(30), fragmentationList.get(31), fragmentationList.get(32), fragmentationList.get(33));
//        isInserted = databaseHelper.insertData_F(fragmentationList.get(34), fragmentationList.get(35), fragmentationList.get(36),fragmentationList.get(37), fragmentationList.get(38), fragmentationList.get(39), fragmentationList.get(40), fragmentationList.get(41), fragmentationList.get(42), fragmentationList.get(43), fragmentationList.get(44), fragmentationList.get(45), fragmentationList.get(46),fragmentationList.get(47), fragmentationList.get(48), fragmentationList.get(49), fragmentationList.get(50));
//        isInserted = databaseHelper.insertData_F(fragmentationList.get(51), fragmentationList.get(52), fragmentationList.get(53),fragmentationList.get(54), fragmentationList.get(55), fragmentationList.get(56), fragmentationList.get(57), fragmentationList.get(58), fragmentationList.get(59), fragmentationList.get(60), fragmentationList.get(61), fragmentationList.get(62), fragmentationList.get(63),fragmentationList.get(64), fragmentationList.get(65), fragmentationList.get(66), fragmentationList.get(67));
//        isInserted = databaseHelper.insertData_F(fragmentationList.get(68), fragmentationList.get(69), fragmentationList.get(70),fragmentationList.get(71), fragmentationList.get(72), fragmentationList.get(73), fragmentationList.get(74), fragmentationList.get(75), fragmentationList.get(76), fragmentationList.get(77), fragmentationList.get(78), fragmentationList.get(79), fragmentationList.get(80),fragmentationList.get(81), fragmentationList.get(82), fragmentationList.get(83), fragmentationList.get(84));



        for (int start = 0; start < enzymaticreactionList.size(); start += (enzymaticreactionList.size()/number_of_Bacteria)) {
            int end = Math.min(start + (enzymaticreactionList.size()/number_of_Bacteria), enzymaticreactionList.size());
            List<String> sublist = enzymaticreactionList.subList(start, end);
            Log.d("Key Value", "sublist: " + sublist);
            isInserted = databaseHelper.insertData_ER(sublist);
        }
        for (int start = 0; start < differentialAntibioticsList.size(); start += (differentialAntibioticsList.size()/number_of_Bacteria)) {
            int end = Math.min(start + (differentialAntibioticsList.size()/number_of_Bacteria), differentialAntibioticsList.size());
            List<String> sublist = differentialAntibioticsList.subList(start, end);
            Log.d("Key Value", "sublist: " + sublist);
            isInserted = databaseHelper.insertData_DA(sublist);
        }
        for (int start = 0; start < fragmentationList.size(); start += (fragmentationList.size()/number_of_Bacteria)) {
            int end = Math.min(start + (fragmentationList.size()/number_of_Bacteria), fragmentationList.size());
            List<String> sublist = fragmentationList.subList(start, end);
            Log.d("Key Value", "sublist: " + sublist);
            isInserted = databaseHelper.insertData_F(sublist);
        }
        for (int start = 0; start < jsonValues.size(); start += (jsonValues.size()/number_of_Bacteria)) {
            int end = Math.min(start + (jsonValues.size()/number_of_Bacteria), jsonValues.size());
            List<String> sublist = jsonValues.subList(start, end);
            Log.d("Key Value", "sublist: " + sublist);
            isInserted = databaseHelper.insertData_MT(sublist);
        }

        databaseHelper.getAllContacts(context);

        Intent intent = new Intent(context,MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//        intent.putExtra("number_of_bacteria",number_of_Bacteria);
        context.startActivity(intent);
    }

}
