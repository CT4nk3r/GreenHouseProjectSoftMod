import java.util.List;
public class Main {

    public static void main(String[] args) {
        Loader loader = new Loader("greenhouses.json");
        GreenHouseList greenhouseList = loader.loadGreenHouses();
        List<Greenhouse> greenhouses = greenhouseList.getGreenhouseList();
        Monitor monitorService = new Monitor();
        Controller controller = new Controller();
        System.out.println("Greenhouse Data:");
        for(int i = 0; i < greenhouses.size(); i++)
        {
            greenhouses.get(i).print();
            System.out.println();
        }
        System.out.println("Fetching Sensor Data:");
        for(int i = 0; i < greenhouses.size(); i++)
        {
            SensorData data = monitorService.getSensorData(greenhouses.get(i).ghId);
            controller.control(data, greenhouses.get(i));
        }
    }
    
}
