/*
 * instances of this class are saved to examArrayList
 */

public class Subject {
    //attributes
    private String name;
    private String shortName;
    private int coefficient;

    //constructor
    public Subject(String name, String shortName, int coefficient) {
        this.name = name;
        this.shortName = shortName;
        this.coefficient = coefficient;
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    @Override
    public String toString(){
        return "Name: "+getName()+" ("+getShortName()+"), Coefficient: "+getCoefficient();
    }
}
