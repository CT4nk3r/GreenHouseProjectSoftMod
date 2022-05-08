import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Loader implements ILoader {

    private String filename;

    public Loader(String _filename) {
        this.filename = _filename;
    }

    @Override
    public GreenHouseList loadGreenHouses() {
        GreenHouseList greenHouseList = new GreenHouseList();
        List<Greenhouse> greenhouses = new ArrayList<>();
        int idx = filename.lastIndexOf('.');
        String extension = "";
        if (idx > 0) {
            extension = filename.substring(idx + 1);
        }

        if (extension.toLowerCase().equals("json")) {
            try {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                BufferedReader reader = new BufferedReader(new FileReader(filename));
                greenHouseList = gson.fromJson(reader, GreenHouseList.class);
                return greenHouseList;
            } catch (IOException ex) {
                System.out.println("Error while trying to read file!");
            }
        }
        greenHouseList.setGreenhouseList(greenhouses);
        return greenHouseList;
    }
}   

