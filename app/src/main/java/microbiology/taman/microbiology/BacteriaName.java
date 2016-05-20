package microbiology.taman.microbiology;

import java.io.Serializable;

/**
 * Created by Xerric on 12/4/2015.
 */
public class BacteriaName implements Serializable {
    private static final long serialVersionUID = 1L;

    private String bacteria_name;

    public BacteriaName() {

    }

    public BacteriaName(String name) {

        this.bacteria_name = name;


    }

    public String getBacteria_name() {
        return bacteria_name;
    }

    public void setBacteria_name(String bacteria_name) {
        this.bacteria_name = bacteria_name;
    }
}
