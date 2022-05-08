import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;

public class Controller {
    private final Driver driver;

    public Controller() {
        driver = new Driver();

    }

    private double controlHelper(int param) {
        double returnVal = 0.0;
        switch (param) {
            case 0 -> returnVal = 0;
            case 20 -> returnVal = 17.3;
            case 21 -> returnVal = 18.5;
            case 22 -> returnVal = 19.7;
            case 23 -> returnVal = 20.9;
            case 24 -> returnVal = 22.1;
            case 25 -> returnVal = 23.3;
            case 26 -> returnVal = 24.7;
            case 27 -> returnVal = 26.1;
            case 28 -> returnVal = 27.5;
            case 29 -> returnVal = 28.9;
            case 30 -> returnVal = 30.3;
            case 31 -> returnVal = 31.9;
            case 32 -> returnVal = 33.5;
            case 33 -> returnVal = 35.1;
            case 34 -> returnVal = 36.7;
            case 35 -> returnVal = 38.3;
            default -> returnVal = 0.0;
        }
        return returnVal;
    }

    public void control(SensorData data, Greenhouse gh) {
        try {
            Log log = new Log("log_controller.txt");
            log.logger.setLevel(Level.ALL);

        System.out.println(gh.ghId + " Managing Greenhouse...");
        log.logger.info(gh.ghId + " Managing Greenhouse...");
        if (data.boiler_on)
            System.out.println("Boiler is currently working, sending blank command to the cloud...");
            log.logger.warning("Boiler is currently working, sending blank command to the cloud...");
        if (data.sprinkler_on)
            System.out.println("Sprinkler is currently working, sending blank command to the cloud...");
            log.logger.warning("Sprinkler is currently working, sending blank command to the cloud...");
        double sprinklerValue = 0;
        double boilerValue = 0;
        if ((data.temperature_act - gh.temperature_min >= 5) || (data.humidity_act - gh.humidity_min >= 20)) {
            String errorFileName = "log.txt";
            if ((data.temperature_act - gh.temperature_min >= 5)) {
                System.out.println("Error with the boiler! Logging...");
                log.logger.info("Error with the boiler! Logging...");
                try {
                    PrintWriter writer = new PrintWriter(errorFileName);
                    writer.println("BoilerError - " + data.temperature_act + "C° temperature");
                    log.logger.info("BoilerError - " + data.temperature_act + "C° temperature");
                    writer.flush();
                    writer.close();
                } catch (FileNotFoundException e) {
                    System.out.println("File not found: " + errorFileName);
                    log.logger.info("File not found: " + errorFileName);
                }

            }

            if (data.humidity_act - gh.humidity_min >= 20) {
                System.out.println("Error with the sprinkler! Logging...");
                log.logger.info("Error with the sprinkler! Logging...");
                try {
                    PrintWriter writer = new PrintWriter(errorFileName);
                    writer.println("SprinklerError - " + data.humidity_act + "% humidity");
                    log.logger.info("SprinklerError - " + data.humidity_act + "% humidity");
                    writer.flush();
                    writer.close();
                } catch (FileNotFoundException e) {
                    System.out.println("File not found: " + errorFileName);
                    log.logger.info("File not found: " + errorFileName);
                }
            }
        } else {
            if (data.boiler_on || data.sprinkler_on) {
                boilerValue = 0;
                sprinklerValue = 0;
            } else {
                if (data.temperature_act < gh.temperature_min) {
                    boilerValue = (gh.temperature_opt - data.temperature_act);
                    double humidityOptPercentage = 0;
                    double humidityPercentage = 0;
                    System.out.println("Calculating...");
                    log.logger.info("Calculating...");
                    humidityPercentage = (controlHelper((int) Math.round(data.temperature_act)) * (data.humidity_act / 100)); //16.31
                    humidityOptPercentage = (controlHelper(Math.round(gh.temperature_opt)) * 0.6);
                    sprinklerValue = ((humidityOptPercentage - humidityPercentage)) / 0.01;
                    sprinklerValue = (sprinklerValue * gh.volume) / 1000;
                } else {
                    boilerValue = 0;
                    sprinklerValue = 0;
                }
                System.out.println("Forwarding command...");
                log.logger.info("Forwarding command...");
                int cmdStatus = driver.sendCommand(gh, data.token, boilerValue, sprinklerValue);

                switch (cmdStatus) {
                    case 100 -> {
                        System.out.println("Command was successfully done!");
                        log.logger.severe("Command was successfully done!");
                    }
                    case 101 -> {
                        System.out.println("Wrong calculation!");
                        log.logger.severe("Wrong calculation!");
                    }
                    case 102 -> {
                        System.out.println("The command was sent to an already working unit!");
                        log.logger.severe("The command was sent to an already working unit!");
                    }
                    case 103 -> {
                        System.out.println("Wrong command was sent out to the boiler!");
                        log.logger.severe("Wrong command was sent out to the boiler!");
                    }
                    case 104 -> {
                        System.out.println("Wrong command was sent out to the sprinkler!");
                        log.logger.severe("Wrong command was sent out to the sprinkler!");
                    }
                    case 105 -> {
                        System.out.println("Invalid token!");
                        log.logger.severe("Invalid token!");
                    }
                    case 106 -> {
                        System.out.println("The GreenHouse was not found!");
                        log.logger.severe("The GreenHouse was not found!");
                    }
                    case 107 -> {
                        System.out.println("General message processing error!");
                        log.logger.severe("General message processing error!");
                    }
                    default -> {
                        System.out.println("Error, command cannot be completed!");
                        log.logger.severe("Error, command cannot be completed!");
                    }
                }

            }
        }
        System.out.println();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
