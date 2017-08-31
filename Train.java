/**
 * Created by mohamedmohamud on 4/24/16.
 */
import java.util.ArrayList;
import java.util.Random;
public class Train {

    public int numoftraincars = 1;
    public ArrayList<TrainCar> cars = new ArrayList<>();
    private String direction;
    private Stop stop;
    private int trainid;
    private int carnum;

    public Train(String traindirection, Stop currentstop, int numCar, int id) { //currentstop and stopname in the Stop class need to be the same, check
        trainid = id;
        GreenLineSim.traintotal += 1;
        direction = traindirection;
        stop = currentstop;

        numoftraincars = numCar;
        for (int i = 0; i < numoftraincars; i++) {
            cars.add(new TrainCar());
        }

        carnum = numCar;
    }
    public int getRandom(){
        int a = (int) (Math.random()*(4-1))+1;
        return a;
    }
    public int getNumoftraincars(){
        return carnum;
    }
    public int getID(){
        return trainid;
    }
    public void nextStop(){ //will send the train to the next stop
        if (getStop().getStopName().equals("Target Field")){
            changeDirection("east");
        }
        else if (getStop().getStopName().equals("Union Depot")){
            changeDirection("west");
        }
        int u = this.getStop().ID;
        if(direction.contains("west")) {
            stop = GreenLineSim.stopArray.get(u - 1);
        }
        if(direction.contains("east")) {
            stop = GreenLineSim.stopArray.get(u + 1);
        }
    }
    public Stop getStop(){return stop;}
    public String getStopName(){return stop.getStopName();}

    public String getDirection(){return direction;}
    public void changeDirection(String newDirection){
        direction = newDirection;
    }




    public static void main(String[] args){}
}
