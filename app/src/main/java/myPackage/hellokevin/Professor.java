package myPackage.hellokevin;

/**
 * Created by Kevin Cepria on 7/26/2015.
 */
public class Professor {

    private String Name;
    private String Position;

    public Professor(){
        Name="";
        Position="";
    }
    public void setName(String NAME){
        Name=NAME;
    }

    public void setPosition(String POSITION){
        Position=POSITION;
    }

    public String getName(){
        return Name;
    }

    public String getPosition(){
        return Position;
    }
}
