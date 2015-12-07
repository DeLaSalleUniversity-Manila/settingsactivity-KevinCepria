package myPackage.hellokevin;

/**
 * Created by cobalt on 8/27/14.
 */
public class MyThesis {

    private int ID;
    private String TITLE;
    private String AUTHOR;
    private String ADVISER;
    private String AREA;
    private String COURSE;
    private String STATUS;
    private String AY;
    private String TERM;
    private String CHAIROFPANEL;
    private String PANELIST1;
    private String PANELIST2;

    public MyThesis() {
        ID = 0;
        TITLE = "";
        AUTHOR = "";
        ADVISER = "";
        AREA = "";
       COURSE = "";
        CHAIROFPANEL="";
        PANELIST1="";
        PANELIST2 ="";
        STATUS="";
        AY="";
        TERM="";
    }

    public MyThesis(String sTATUS, String aY, String tERM, String tITLE, String aUTHOR, String aDVISER, String aREA, String cOURSE,String cHAIROFPANEL,String pANELIST1,String pANELIST2) {
// no ID???
         TITLE = tITLE;
        AUTHOR = aUTHOR;
        ADVISER = aDVISER;
        AREA = aREA;
       COURSE = cOURSE;
        CHAIROFPANEL=cHAIROFPANEL;
        PANELIST1=pANELIST1;
        PANELIST2=pANELIST2;
        STATUS=sTATUS;
        AY=aY;
        TERM=tERM;

    }

    public int getID() {
        return ID;
    }

    public String getTITLE() {
        return TITLE;
    }

    public String getADVISER() {
        return ADVISER;
    }

    public String getAREA() {
        return AREA;
    }

    public String getCHAIROFPANEL() {
        return CHAIROFPANEL;
    }
    public String getPANELIST1() {
        return PANELIST1;
    }
    public String getPANELIST2() {
        return PANELIST2;
    }
    public String getCOURSE() {
        return COURSE;
    }

    public String getAUTHOR() {
        return AUTHOR;
    }

    public String getSTATUS() {
        return STATUS;
    }
    public String getAY() {
        return AY;
    }
    public String getTERM() {return TERM;  }

    public void setID(int id) {
        ID = id;
    }

    public void setTITLE(String tITLE) {TITLE = tITLE; }

    public void setSTATUS(String sTATUS) {
        STATUS = sTATUS;
    }
    public void setAY(String aY) {
        AY = aY;
    }
    public void setTERM(String tERM) {
        TERM = tERM;
    }

    public void setADVISER(String aDVISER) {
        ADVISER= aDVISER;
    }

    public void setAREA(String  aREA) {
        AREA = aREA;
    }

    public void setCOURSE(String cOURSE) {
        COURSE = cOURSE;
    }

    public void setAUTHOR(String aUTHOR) {
        AUTHOR = aUTHOR;
    }

    public void setCHAIROFPANEL(String cHAIROFPANEL) {
        CHAIROFPANEL = cHAIROFPANEL;
    }

    public void setPANELIST1(String pANELIST1) {
        PANELIST1 =  pANELIST1;
    }

    public void setPANELIST2(String pANELIST2) {
        PANELIST2 = pANELIST2;
    }


}
