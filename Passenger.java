/**
 * Created by Huangying on 4/23/2016.
 */
//I think three variables here are straightforward
public class Passenger {
    private int arrivalTime;
    private String destination;
    private String direction;
    private String origin;

    public Passenger(){    }

    public Passenger(int t, String passorigin,String desti, String direct){
        arrivalTime = t;
        origin = passorigin;
        destination = desti;
        direction = direct;
    }
    public boolean exist(){return true;}
    public String getDestination(){return destination;}
    public String getDirection(){return direction;}
    public String getOrigin(){return origin;}
    public int getArrivalTime(){return arrivalTime;}

}
