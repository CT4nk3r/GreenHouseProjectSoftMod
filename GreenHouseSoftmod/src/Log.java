import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
    public Logger logger;
    FileHandler fileHandler;

    public Log(String fileName) throws SecurityException, IOException{
        File f = new File(fileName);
        if (!(f.exists())){
            f.createNewFile();
        }
        fileHandler = new FileHandler(fileName, true);
        logger = Logger.getLogger("test");
        logger.addHandler(fileHandler);
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        fileHandler.setFormatter(simpleFormatter);
    }
}
