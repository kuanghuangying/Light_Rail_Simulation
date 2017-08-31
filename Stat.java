/**
 * Created by Huangying on 4/23/2016.
 */
public class Stat {
//current time
    public static double passengerNumAtStop = 0;
    public static double totalWaitTime = 0;

    public static void updatePassengerNumbAtStop (int newNum){
        passengerNumAtStop += (double)newNum;
    }
    public static double getAveNumPass(){
        double aveNumPass =  passengerNumAtStop/GreenLineSim.ttime;
        return aveNumPass;
    }

    public static void updateTotalWaitTime (double newNum){
        totalWaitTime += newNum;
    }
    public static double getAveWaitTime(){
        double aveWaitTime =  totalWaitTime/passengerNumAtStop;
        return aveWaitTime;
    }

}
