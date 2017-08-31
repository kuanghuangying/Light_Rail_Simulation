import java.util.Arrays;

/**
 * Created by mohamedmohamud on 4/16/16.
 */
public class Stop {
    String stopName;
    Q1<Passenger> eastPassenger;
    Q1<Passenger> westPassenger;
    int ID;//in case we need access ID, we don't have to write the code every time

    public Stop(String whatStop){
        stopName = whatStop;
        eastPassenger = new Q1<>();
        westPassenger = new Q1<>();
        ID = (Arrays.asList(GreenLineSim.StopsArray).indexOf(stopName)); //need change if using ArrayList
        }

    public String getStopName(){return stopName;}




}
