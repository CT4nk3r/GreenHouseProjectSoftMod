import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        try {
            Log log = new Log("log_main.txt");
            log.logger.setLevel(Level.ALL);

            Loader loader = new Loader("greenhouses.json");
            List<Greenhouse> greenhouses = loader.loadGreenHouses().getGreenhouseList();
            Monitor monitorService = new Monitor();
            Controller controller = new Controller();
            System.out.println("Greenhouse Data:");
            log.logger.info("Greenhouse Data:");
            for (Greenhouse greenhouse : greenhouses) {
                System.out.println(greenhouse.toString());
                log.logger.info(greenhouse.toString());
            }
            System.out.println("Fetching Sensor Data:");
            log.logger.info("Fetching Sensor Data:");
            for (Greenhouse greenhouse : greenhouses) {
                SensorData data = monitorService.getSensorData(greenhouse.ghId);
                controller.control(data, greenhouse);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
