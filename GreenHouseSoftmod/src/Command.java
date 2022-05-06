import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;

public class Command {
    public String ghId;
    public String boilerCommand;
    public String sprinklerCommand;

    public Command(String _ghId, String _boilerCommand, String _sprinklerCommand) {
        this.ghId = _ghId;
        this.boilerCommand = _boilerCommand;
        this.sprinklerCommand = _sprinklerCommand;
    }

    public int send(String token) {
        int statusCode = 0;
        try {

            URL url = new URL("http://193.6.19.58:8181/greenhouse/" + token);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setDoOutput(true);
            String parameters = "{\n" + "\"ghId\" : \"" + ghId + "\",\r\n" + "\"boilerCommand\" : " + "\"" + boilerCommand + "\"," + "\r\n" + "\"sprinklerCommand\" : " + "\"" + sprinklerCommand + "\"" + "\n}";

            OutputStream os = conn.getOutputStream();
            os.write(parameters.getBytes());
            os.flush();
            os.close();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader((new InputStreamReader(conn.getInputStream())));
                String inputLine;
                while ((inputLine = reader.readLine()) != null)
                    statusCode = Integer.valueOf(inputLine);

                return statusCode;
            }
        } catch (Exception e) {
            System.out.println("URL hiba!");
        }
        //Ha nem küld vissza más státuszkódot, akkor küldje vissza azt, hogy nem talált üvegházat.
        return 106;
    }
}