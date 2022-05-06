import javax.sound.midi.ControllerEventListener;
import java.util.List;
//Bősze Máté, Braghini Benjamin Matthew, Bognár Balázs, Gyenti Kristóf
//Szoftmod beadandó program
public class Main {
    public static void main(String[] args){
        Loader loader = new Loader("greenhouse.json");
        List<GreenHouse> greenHouses = loader.loadGreenHouses().getGreenHouseList();
        Monitor monitor = new Monitor();
        Controller controller = new Controller();
        System.out.println("Greenhouse data:");
        for (GreenHouse greenHouse: greenHouses){
            greenHouse.print();
            System.out.println();
        }
        System.out.println("Sensor state fetching...");
        for (GreenHouse greenHouse: greenHouses){
            SensorData sensorData = monitor.getSensorData(greenHouse.ghId);
        }
    }
}
