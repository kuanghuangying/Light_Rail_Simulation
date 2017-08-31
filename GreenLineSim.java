/**
 * Created by mohamedmohamud on 4/16/16.
 */
//MOST UP TO DATE
import java.util.ArrayList;
public class GreenLineSim {
    public static ArrayList<Double> triptimes = new ArrayList();
    public static int passtotal = 0;
    public static int traintotal = 0;
    public static int ptime = 0;
    public static int ttime = 0;
    public static ArrayList<Stop> stopArray;
    public static ArrayList<Train> trainArray = new ArrayList<>();
    static PQ agenda = new PQ();
    public static String[] StopsArray = {"Target Field", "Warehouse/Hennepin Ave","Nicollet Mall",
            "Government Plaza", "U.S. Bank Stadium","West Bank","East Bank",
            "Stadium Village", "Prospect Park", "Westgate", "Raymond Ave",
            "Fairview Ave","Snelling Ave","Hamline Ave","Lexington Pkwy",
            "Victoria St","Dale St","Western Ave","Capitol","Robert St",
            "10th St", "Central","Union Depot"};
    public static  String[] downTown = {"Target Field", "Warehouse/Hennepin Ave","Nicollet Mall",
            "Government Plaza", "U.S. Bank Stadium", "Capitol","Robert St",
            "10th St", "Central","Union Depot"   };
    public static  String[] campus = {"West Bank","East Bank", "Stadium Village"};

    //constructor
    public GreenLineSim(){
        //create an array of stop objects
//        stopArray = new ArrayList<Stop>(23);//I think this'll work as a stop object array
//        for(int u=0; u< StopsArray.length; u++){
//            Stop y = new Stop(StopsArray[u]);
//            stopArray.add(y);
//        }


    }


    public static void main(String[] args){

        //initiate stops and passengerMaker for each stop
        stopArray = new ArrayList<>(23);
        for(int u=0; u < StopsArray.length; u++){
            stopArray.add(new Stop(StopsArray[u]));
            agenda.add(new PassengerMaker(stopArray.get(u)),10);
        }
        int ure = 2;
        int ere = 22;
        //initiate trains and train event
        for(int u=0; u < 21; u++) {
            trainArray.add(new Train("east", stopArray.get(u),3, ure));
            trainArray.add(new Train("west", stopArray.get(stopArray.size() - u -1),3, ere));
        ure++;
            ere++;

        }
        System.out.println("past step 2");
        for(int a=0; a<trainArray.size(); a++){
            GreenLineSim.agenda.add(new TrainEvent(trainArray.get(a)), 180);
        }
        System.out.println("Train reached terminus");

        while (agenda.getCurrentTime() <= 20000) {
            agenda.remove().run();
        }
        System.out.println("\n" + "Running time: 12000");
        System.out.println("Number of trains: " + trainArray.size());
        System.out.println("Number of cars in each train: " + trainArray.get(0).numoftraincars);
        System.out.println("Average number of passengers waiting at each stop: " + Stat.getAveNumPass());
        System.out.println("Average wait time for each passenger at stops: "+ Stat.getAveWaitTime());
        System.out.println(" Passenger total through the system : " + passtotal + "  train total : " + traintotal);
        double triptimetotal = 0;
        double it = 0;
        for(int u = 0; u<triptimes.size(); u++){
            triptimetotal += triptimes.get(u);
            it++;  //Adding up all the triptimes from the passenegers
        }
        System.out.println(it);
        double avgtriptime = triptimetotal / passtotal; //Dividing by the number of passenger
        System.out.println("Average trip time was " + triptimetotal);

    }
}
