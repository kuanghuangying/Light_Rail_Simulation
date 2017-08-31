/**
 * Created by mohamedmohamud on 4/24/16.
 */
import java.util.ArrayList;
public class TrainCar {
    public ArrayList<Passenger> passengerslist; //holds the car passengers up to 50

    public TrainCar() {
        passengerslist = new ArrayList<>();
    }

    public int numberpplonTrain() {
        return passengerslist.size();
    } //Returns the # of passenger spots taken up in the Passengerlist

    public boolean pushtoTrainCar(Passenger m) { //returns true or false on whether passenger succesfully gets onto train car
        if (passengerslist.size() < 50) {
            passengerslist.add(m);
            return true;
        }
        else {return false;}
    }

}
