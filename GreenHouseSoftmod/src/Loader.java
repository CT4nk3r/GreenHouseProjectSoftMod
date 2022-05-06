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
        GreenHouseList ghList = new GreenHouseList();
        List<Greenhouse> greenhouses = new ArrayList<>();
        int idx = filename.lastIndexOf('.');
        String extension = "";
        if (idx > 0) {
            extension = filename.substring(idx + 1);
        }

        if (extension.toLowerCase().equals("xml")) {
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new File(filename));
                doc.getDocumentElement().normalize();
                NodeList list = doc.getElementsByTagName("greenhouseList");
                for (int i = 0; i < list.getLength(); i++) {
                    Node node = list.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element elem = (Element) node;
                        String ghId = elem.getElementsByTagName("ghId").item(0).getTextContent();
                        String description = elem.getElementsByTagName("description").item(0).getTextContent();
                        int temperature_min = Integer.parseInt(elem.getElementsByTagName("temperature_min").item(0).getTextContent());
                        int temperature_opt = Integer.parseInt(elem.getElementsByTagName("temperature_opt").item(0).getTextContent());
                        int humidity_min = Integer.parseInt(elem.getElementsByTagName("humidity_min").item(0).getTextContent());
                        int volume = Integer.parseInt(elem.getElementsByTagName("volume").item(0).getTextContent());
                        Greenhouse newGreenhouse = new Greenhouse();
                        newGreenhouse.ghId = ghId;
                        newGreenhouse.description = description;
                        newGreenhouse.temperature_min = temperature_min;
                        newGreenhouse.temperature_opt = temperature_opt;
                        newGreenhouse.humidity_min = humidity_min;
                        newGreenhouse.volume = volume;
                        greenhouses.add(newGreenhouse);
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (extension.toLowerCase().equals("json")) {
            try {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                BufferedReader reader = new BufferedReader(new FileReader(filename));
                ghList = gson.fromJson(reader, GreenHouseList.class);
                return ghList;
            } catch (IOException ex) {
                System.out.println("Error while trying to read file!");
            }
        }
        ghList.setGreenhouseList(greenhouses);
        return ghList;
    }
}   

