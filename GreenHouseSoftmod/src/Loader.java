import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Loader implements ILoader{
    private String filename;
    public Loader(String _filename){
        _filename =  filename;
    }

    @Override
    public GreenHouseList loadGreenHouses(){
        GreenHouseList greenHouseList = new GreenHouseList();
        int index = filename.lastIndexOf('.');
        String extension = "";
        if (index > 0){
            extension = filename.substring(index+1);
        }
        List<GreenHouse> greenHouseListReturn = new ArrayList<>();
        if(extension.toLowerCase(Locale.ROOT).equals("json")){
            try {
                //http://193.6.19.58:8181/greenhouse/{ghId}
                URL url = new URL("http://193.6.19.58:8181/greenhouse/");
                HttpURLConnection conn;
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.setInstanceFollowRedirects(false);
                int status = conn.getResponseCode();
                BufferedReader bufferedReader = null;
                if (status > 299) {
                    //handle the error code, if necessary
                    bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                } else {
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    greenHouseList = gson.fromJson((Reader) bufferedReader, (Type) GreenHouse.class);
                }
                conn.disconnect();

                return greenHouseList;
            }catch (IOException ex){
                System.out.println("Error reading file!");
            }
        }
        greenHouseList.setGreenHouseList(greenHouseListReturn);
        return greenHouseList;
    }
}
