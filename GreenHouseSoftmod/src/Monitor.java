import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Monitor implements IMonitor{
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
            BufferedReader bufferedReader;
            bufferedReader = null;
            if (status > 299) {
                //handle the error code, if necessary
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                receivedData = gson.fromJson(bufferedReader, SensorData.class);
            }
            conn.disconnect();
        } catch (Exception ex) {
            System.out.println("URL Error!");
        }

        return receivedData;
    }
}
