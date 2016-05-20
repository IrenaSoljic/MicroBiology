package microbiology.taman.microbiology;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xerric on 12/7/2015.
 */
public class DataWrapper implements Serializable {

    private List<BacteriaName> parliaments;

    public DataWrapper(List<BacteriaName> data) {
        this.parliaments = data;
    }

    public List<BacteriaName> getParliaments() {
        return this.parliaments;
    }

}
