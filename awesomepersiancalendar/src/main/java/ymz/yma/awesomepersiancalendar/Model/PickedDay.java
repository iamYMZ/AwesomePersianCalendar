package ymz.yma.awesomepersiancalendar.Model;

import java.io.Serializable;

public class PickedDay implements Serializable {
    private int year,month ;
    private int id;
    private static PickedDay instance = null;

    public static PickedDay  getInstance(int y,int m , int i){
                if(instance !=null )
                 return instance;
                else
                    return new PickedDay(y,m,i);
    }

    private PickedDay(int y,int m,int i){
                 this.year = y;
                 this.month = m;
                 this.id = i;
    }

    
    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getId() {
        return id;
    }

}
