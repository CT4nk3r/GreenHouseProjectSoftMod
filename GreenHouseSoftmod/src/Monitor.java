import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.lang.String;
public class Monitor implements IMonitor 
{
    @Override
    public SensorData getSensorData(String ghId) {
       SensorData receivedData = new SensorData();
        try {
            //http://193.6.19.58:8181/greenhouse/{ghId}
            URL url = new URL("http://193.6.19.58:8181/greenhouse/" + ghId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setInstanceFollowRedirects(false);
            int status = conn.getResponseCode();
            BufferedReader streamReader = null;
            if (status > 299) {
            streamReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            } else {
            streamReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                receivedData = gson.fromJson(streamReader, SensorData.class);

            }
            conn.disconnect();
           
        } catch (Exception ex) {
            System.out.println("URL error!");
        }
       return receivedData;
    }
}
