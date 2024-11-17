/*
 * this Object is saved to the json file. it contaisn all information about user and class data
 * 4 different constructors forr different applications to avoid complexity in different cases
 */
import java.util.ArrayList;


public class SavedFile {
    //variables where information is saved in
    private final String className;
    private final int semCounter; 
    private final boolean splitMath;
    private final int mathNumber;
    private final int mathICoeff;
    private final int mathIICoeff;
    private final Subject[] subjectArray;
    ArrayList<ArrayList<ArrayList<Exam>>> examArrayList;
    ArrayList<ArrayList<Integer>> bonuArrayList;

    //Dafault Constructor without bonusArrayList
    public SavedFile(String className, int semCounter, boolean splitMath, int mathNumber, int mathICoeff, int mathIICoeff, Subject[] subjectArray, ArrayList<ArrayList<ArrayList<Exam>>> examArrayList) {
        this.className = className;
        this.semCounter = semCounter;
        this.splitMath = splitMath;
        this.mathNumber = mathNumber; 
        this.mathICoeff = mathICoeff;
        this.mathIICoeff = mathIICoeff;
        this.subjectArray = subjectArray;
        this.examArrayList=examArrayList;
    }
    //subjectArrayList instead of Subject[]
    public SavedFile(String className, int semCounter, boolean splitMath, int mathNumber, int mathICoeff, int mathIICoeff, ArrayList<Subject> subjectArrayList, ArrayList<ArrayList<ArrayList<Exam>>> examArrayList, ArrayList<ArrayList<Integer>> bonuArrayList) {
        this.className = className;
        this.semCounter = semCounter;
        this.splitMath = splitMath;
        this.mathNumber = mathNumber; 
        this.mathICoeff = mathICoeff;
        this.mathIICoeff = mathIICoeff;
            int size = subjectArrayList.size();
            Subject[] tempArray = new Subject[size];
            for(int i=0; i<subjectArrayList.size();i++){
                tempArray[i]=subjectArrayList.get(i);
            }
        this.subjectArray = tempArray;
        this.examArrayList=examArrayList;
        this.bonuArrayList=bonuArrayList;
    }
    //default with BonusArrayList
    public SavedFile(String className, int semCounter, boolean splitMath, int mathNumber, int mathICoeff, int mathIICoeff, Subject[] subjectArray, ArrayList<ArrayList<ArrayList<Exam>>> examArrayList, ArrayList<ArrayList<Integer>> bonuArrayList) {
        this.className = className;
        this.semCounter = semCounter;
        this.splitMath = splitMath;
        this.mathNumber = mathNumber; 
        this.mathICoeff = mathICoeff;
        this.mathIICoeff = mathIICoeff;
        this.subjectArray = subjectArray;
        this.examArrayList=examArrayList;
        this.bonuArrayList=bonuArrayList;
    }
    //subjectArrayList instead of Subject[] without bonusArrayList
    public SavedFile(String className, int semCounter, boolean splitMath, int mathNumber, int mathICoeff, int mathIICoeff, ArrayList<Subject> subjectArrayList) {
        this.className = className;
        this.semCounter = semCounter;
        this.splitMath = splitMath;
        this.mathNumber = mathNumber;
        this.mathICoeff = mathICoeff;
        this.mathIICoeff = mathIICoeff; 
            int size = subjectArrayList.size();
            Subject[] tempArray = new Subject[size];
            for(int i=0; i<subjectArrayList.size();i++){
                tempArray[i]=subjectArrayList.get(i);
            }
        this.subjectArray = tempArray;
    }
    //getters and setters
    public String getClassName() {
        return className;
    }

    public int getSemCounter() {
        return semCounter;
    }

    public boolean isSplitMath() {
        return splitMath;
    }

    public int getMathNumber() {
        return mathNumber;
    }

    public Subject[] getSubjectArray() {
        return subjectArray;
    }

    public ArrayList<ArrayList<ArrayList<Exam>>> getExamArrayList() {
        return examArrayList;
    }

    public ArrayList<ArrayList<Integer>> getBonuArrayList() {
        return bonuArrayList;
    }

    public int getMathICoeff() {
        return mathICoeff;
    }

    public int getMathIICoeff() {
        return mathIICoeff;
    }
}
