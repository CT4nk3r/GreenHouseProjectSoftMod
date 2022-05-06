import java.util.List;
public class Main {

    public static void main(String[] args) {
        Loader loader = new Loader("greenhouses.json");
        List<Greenhouse> greenhouses = loader.loadGreenHouses().getGreenhouseList();
        Monitor monitorService = new Monitor();
        Controller controller = new Controller();
        System.out.println("Greenhouse Data:");
        for (Greenhouse greenhouse : greenhouses) {
            System.out.println(greenhouse.toString());
        }
        System.out.println("Fetching Sensor Data:");
        for (Greenhouse greenhouse : greenhouses) {
            SensorData data = monitorService.getSensorData(greenhouse.ghId);
            controller.control(data, greenhouse);
        }
    }
    
}
