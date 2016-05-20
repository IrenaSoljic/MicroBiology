package microbiology.taman.microbiology;

import java.io.Serializable;

/**
 * Created by Xerric on 11/25/2015.
 */
public class Bacteria implements Serializable {
    private static final long serialVersionUID = 1L;
    private String property;
    private boolean isSelected;

    public Bacteria(){}

    public Bacteria(String property) {
        this.property = property;
    }

    public Bacteria(String property, boolean isSelected) {
        this.property = property;
        this.isSelected = isSelected;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
