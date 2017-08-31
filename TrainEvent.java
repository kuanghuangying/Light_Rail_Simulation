import java.util.ArrayList;
import java.lang.Math;

/**
 * Created by mohamedmohamud on 4/24/16.
 */
//update on process time, u =1
public class TrainEvent implements Event {

    Train thetrain;
    int processTime;
    double maxwaittime = 0;

    public TrainEvent(Train e) {
        thetrain = e;
    }

    //a method that gets the number of people getting off the train
    public int getOffNumber(){
        int offNumber = 0;
        for (int i = 0; i<thetrain.cars.size(); i++) {
            offNumber = thetrain.cars.get(i).numberpplonTrain();
        }
        return offNumber;
    }

    //a method that gets the number of people getting on the train
    public int getOnNumber(){
        int onNumber = 0;
        onNumber += thetrain.getStop().eastPassenger.length();
        onNumber += thetrain.getStop().westPassenger.length();
        return onNumber;
    }


    //a method that calculates the processing time ( 180 + 15 + maybe ( 2*off + 1*on))
    public int getProcessTime(){
        if (getOnNumber() + 2*getOffNumber() >15){
            processTime = 180 + getOnNumber() + 2*getOffNumber();
        }
        else {
            processTime = 195;
        }
        return processTime;
    }

    public void pickUpWestPass() {
        boolean continuation;
        for (int y = 0; y < thetrain.cars.size(); y++) {
            continuation = true;
            while (continuation && thetrain.getStop().westPassenger.length() > 0) {
                Passenger a = (Passenger) thetrain.getStop().westPassenger.remove();
                double waittime =   Math.abs(GreenLineSim.agenda.getCurrentTime() - a.getArrivalTime()) ;
                if( waittime > maxwaittime){ maxwaittime = waittime;} //Get max wait time
                if (a != null) {
                    thetrain.cars.get(y).pushtoTrainCar(a);
                    Stat.updateTotalWaitTime(GreenLineSim.agenda.getCurrentTime() - a.getArrivalTime());
                    if (thetrain.cars.get(y).numberpplonTrain() == 50) {
                        continuation = false;
                    }
                }
                else{
                    System.out.println("a ========null");
                }
            }
        }
    }
    public void pickUpEastPass(){
        boolean continuation;
        for (int y = 0; y < thetrain.cars.size(); y++) {
            continuation = true;
            while (continuation && thetrain.getStop().eastPassenger.length()>0 ) { // >1 because we
                // Here changed to "thetrain.getStop()" and fixed the problem!!!!
                Passenger a = (Passenger) thetrain.getStop().eastPassenger.remove();
                double waittime =   Math.abs(GreenLineSim.agenda.getCurrentTime() - a.getArrivalTime()) ;
                if( waittime > maxwaittime){ maxwaittime = waittime;} //Get max wait time
                if (a != null) {
                    thetrain.cars.get(y).pushtoTrainCar(a);
                    if (thetrain.cars.get(y).numberpplonTrain() == 50) {
                        continuation = false;
                    }
                }
            }
        }
    }


    public void getOffPass(){
//        System.out.println("-----------------Current train at" + " stop name: " + thetrain.getStop().getStopName());
        int offNumber = 0;
        ArrayList<Passenger> offList = new ArrayList<>();
        for (int u = 0; u < thetrain.cars.size(); u++) { //iterates through the train cars, through their passengerlists to see if the...
            if (thetrain.cars.get(u) != null) {
//                System.out.println("No. " + u + " car passengerlist size: ----------------" + thetrain.cars.get(u).passengerslist.size());
                //create a list of people who need to get off
                offList.clear();
                for (int y = 0; y < thetrain.cars.get(u).passengerslist.size(); y++) {//..passenger is supposed to get off, removes them if so

                    Passenger p = thetrain.cars.get(u).passengerslist.get(y);
                    if (p != null) {
                        String passDestination = p.getDestination();
                        //System.out.println("the " + y + " passenger going to " + passDestination + " from " + p.getOrigin());

                        if (passDestination.equals(thetrain.getStopName())) {

                            offList.add(p);
                            offNumber++;
                        }
                    }
                } //after for
                for(int p = 0; p < offList.size(); p++){
                    GreenLineSim.triptimes.add((double)GreenLineSim.agenda.getCurrentTime() - (double)offList.get(p).getArrivalTime()); //Subtracting current time with..
                                                                                                //when the passenger got on, adding it to th GreenlineSim triptimes array
                }
                //remove these people
                for (int x = 0; x < offList.size(); x++) {
                    thetrain.cars.get(u).passengerslist.remove(offList.get(x));
                }
            }
        }
        System.out.println(offNumber + " people got off at the stop" );
    }

    public void run() {


        System.out.println("\n" + "Train " + thetrain.getID() + " " + thetrain.getDirection() + " bound at " + thetrain.getStopName() + " " + "with " + thetrain.getNumoftraincars() + " cars");
        for(int a=0; a < thetrain.getNumoftraincars(); a++){
            System.out.println("Train car " + (a+1) + " has " + thetrain.cars.get(a).numberpplonTrain() + " on it");
        }
        GreenLineSim.ttime++;

        //get off method
        getOffPass();

        //get on method
        if(thetrain.getDirection().equals("west")){
        System.out.println("West passenger waiting at the stop : " + Integer.toString(thetrain.getStop().westPassenger.length()) + "\b");}
        Stat.updatePassengerNumbAtStop(thetrain.getStop().westPassenger.length());
        if(thetrain.getDirection().equals("east")){
            System.out.println("East passenger waiting at the stop : " + Integer.toString(thetrain.getStop().eastPassenger.length()) + "\b");}
        Stat.updatePassengerNumbAtStop(thetrain.getStop().eastPassenger.length());
        //west train
        if (thetrain.getDirection().contains("west")) {
            //if at Target field, the direction will be west, but we need to pick upt the east passenger
            if (thetrain.getStop().getStopName().equals("Target Field")){
                pickUpEastPass();
            }
            //otherwise, as normal, pick up west
            else {
                pickUpWestPass();
            }
        }

        //east train
        else if (thetrain.getDirection().contains("east")) {
            //if at Union Depot, we pick up west passenger
            if (thetrain.getStop().getStopName().equals("Union Depot")){
                pickUpWestPass();
            }
            //otherwise, as normal
            else {
                pickUpEastPass();
            }
        }


        GreenLineSim.agenda.add(new TrainEvent(thetrain), getProcessTime());
        thetrain.nextStop();
        System.out.println("Max wait time is " + maxwaittime);
    }

    }

