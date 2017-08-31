import java.util.Arrays;

/**
 * Created by mohamedmohamud on 4/16/16.
 */
//Each stop has one passenger maker, each passenger maker has the same distribution, unless it is downtown/campus
public class PassengerMaker implements Event {
    private Stop passStop; //stop associated with the maker
    private int arrivalInt; //the time interval until next passenger arrives
    private String desti;  //destination of the passenger made
    private String direction; //direction of the passenger made
    int origiNumber; //the stop ID of the stop associated with the maker

    public PassengerMaker(Stop stop) {
        passStop = stop;
        origiNumber = (GreenLineSim.stopArray.indexOf(passStop));
    }

    private int intRandomInterval(int low, int high) { //borrowed from lecture
        return (int) Math.floor((high - low) * Math.random() + low + 0.5); //generate a number in range [low,high]
    }

    public int genRandomArrivalInt(){ //randomness distribution method
        int IntervalNumber = intRandomInterval(1,100); //generate a number from 1-100

        if (IntervalNumber <= 10){ // 10% of the time: 75% above average arrival interval (30 + .75 * 30)
            arrivalInt =  (int) 52.5;
        }
        else if(IntervalNumber <= 25){ //15% of the time: 50% above average arrival interval (30 + .50 * 30)
            arrivalInt =  (int) 45;
        }
        else if(IntervalNumber <= 45){ //20% of the time: 20% above average arrival interval (30 + .20 * 30)
            arrivalInt =  (int)(36);
        }
        else if(IntervalNumber <= 55){ //10% of the time: right at average arrival interval (30)
            arrivalInt =  30;
        }
        else if(IntervalNumber <= 75){ //20% of the time: 20% below average arrival interval (30 - .20 * 30)
            arrivalInt =  (int)(24);
        }
        else if(IntervalNumber <= 90){ //15% of the time: 50% below average arrival interval (30 - .50 * 30)
            arrivalInt =  (int)(15);
        }else{ //10% of the time: 75% below average arrival interval (30 - .75 * 30)
            arrivalInt =  (int)(7.5);
        }

        if (Arrays.asList(GreenLineSim.downTown).contains(passStop.getStopName())){ //refer to http://www.programcreek.com/2014/04/check-if-array-contains-a-value-java/
            arrivalInt = 20; //if it's a downtown stop, the arrival time is 10 seconds earlier
        }
        else if (Arrays.asList(GreenLineSim.campus).contains(passStop.getStopName())){
            arrivalInt = 25;
        }

        return arrivalInt;
    }

    public String genRandomDesti() { //generate a random destination give different stops have different weight
        boolean success = false;
        while (!success) {
            int IntervalNumber = intRandomInterval(1,69);//generate a number from 1-69
            //System.out.println("Interval Number ----------------------" + String.valueOf(IntervalNumber));
            if (IntervalNumber <= 5) {
                desti = GreenLineSim.StopsArray[0];
            } else if (IntervalNumber <= 10) {
                desti = GreenLineSim.StopsArray[1];
            } else if (IntervalNumber <= 15) {
                desti = GreenLineSim.StopsArray[2];
            } else if (IntervalNumber <= 20) {
                desti = GreenLineSim.StopsArray[3];
            } else if (IntervalNumber <= 25) {
                desti = GreenLineSim.StopsArray[4];
            } else if (IntervalNumber <= 28) {
                desti = GreenLineSim.StopsArray[5];
            } else if (IntervalNumber <= 31) {
                desti = GreenLineSim.StopsArray[6];
            } else if (IntervalNumber <= 34) {
                desti = GreenLineSim.StopsArray[7];
            } else if (IntervalNumber == 35) {
                desti = GreenLineSim.StopsArray[8];
            } else if (IntervalNumber == 36) {
                desti = GreenLineSim.StopsArray[9];
            } else if (IntervalNumber == 37) {
                desti = GreenLineSim.StopsArray[10];
            } else if (IntervalNumber == 38) {
                desti = GreenLineSim.StopsArray[11];
            } else if (IntervalNumber == 39) {
                desti = GreenLineSim.StopsArray[12];
            } else if (IntervalNumber == 40) {
                desti = GreenLineSim.StopsArray[13];
            } else if (IntervalNumber == 41) {
                desti = GreenLineSim.StopsArray[14];
            } else if (IntervalNumber == 42) {
                desti = GreenLineSim.StopsArray[15];
            } else if (IntervalNumber == 43) {
                desti = GreenLineSim.StopsArray[16];
            } else if (IntervalNumber == 44) {
                desti = GreenLineSim.StopsArray[17];
            } else if (IntervalNumber <= 49) {
                desti = GreenLineSim.StopsArray[18];
            } else if (IntervalNumber <= 54) {
                desti = GreenLineSim.StopsArray[19];
            } else if (IntervalNumber <= 59) {
                desti = GreenLineSim.StopsArray[20];
            } else if (IntervalNumber <= 64) {
                desti = GreenLineSim.StopsArray[21];
            } else if (IntervalNumber <= 69) {
                desti = GreenLineSim.StopsArray[22];
            }
            //System.out.println(desti);
            if (desti.equals(passStop.getStopName()) || desti == null) { // avoid the case where the destination is where they get on the train
                success = false;
            }
            else{
                success = true;
            }
        }//while loop

        return desti;
    }

    public String getDirection(){ //check if the current stop ID is bigger/smaller than destination stop's ID
        int destNumber = (Arrays.asList(GreenLineSim.StopsArray).indexOf(desti));
        if ( origiNumber < destNumber ){
            direction = "east";
        }
        else {
            direction = "west";
        }
        return direction;
    }

    public void run() {
        //Version 1 ----- advanced. Each time PassengerMaker runs, we generate new arrivalTime, destination, and direction randomly
        int arrive = genRandomArrivalInt();
        String destination = genRandomDesti();
        String direct = getDirection();

//        //Version 2 ----- use simple arrivalTime, destination, and direction
//        int arrive = 30;
//        String destination = "Union Depot";
//        String direct = "east";

        //actual run
        GreenLineSim.ptime ++;

        GreenLineSim.agenda.add(new PassengerMaker(passStop),arrive); //re-schedule the Maker at this stop
//        Passenger newPassenger = new Passenger((int)GreenLineSim.agenda.getCurrentTime(), destination,direct);//create a new passenger
        Passenger newPassenger = new Passenger((int)GreenLineSim.agenda.getCurrentTime(),passStop.getStopName(),destination,direct);//create a new passenger
        GreenLineSim.passtotal += 1;

        //push the passenger into the correct queue of the stop
        if (direct.equals("east")){
            GreenLineSim.stopArray.get(origiNumber).eastPassenger.add(newPassenger);
            //System.out.println(GreenLineSim.stopArray.get(origiNumber).eastPassenger.getFront().getData());
        }
        else if (direct.equals("west")){
            GreenLineSim.stopArray.get(origiNumber).westPassenger.add(newPassenger);
            //System.out.println(GreenLineSim.stopArray.get(origiNumber).eastPassenger.getFront().getData());//try printing something
        }


    }
}
