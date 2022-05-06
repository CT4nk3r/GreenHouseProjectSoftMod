import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Driver implements IDriver{

    @Override
    public int sendCommand(GreenHouse greenhouse, String token, double boilerValue, double sprinklerValue) {
        if (token != null) {
            String boilerCommand = "";
            String sprinklerCommand = "";
            String ghId = greenhouse.ghId;
            if (boilerValue != 0)
                boilerCommand = "bup" + Math.round(boilerValue) + "c";
            if (sprinklerValue != 0)
                sprinklerCommand = "son" + Math.round(sprinklerValue) + "l";
            Command cmd = new Command(ghId, boilerCommand, sprinklerCommand);
            if(!boilerCommand.equals(""))
                System.out.println("Command definition for the boiler - " + boilerCommand);
            if(!sprinklerCommand.equals(""))
                System.out.println("Command definition for the sprinkler - " + sprinklerCommand);
            System.out.println("Sending message to the cloud...");
            return declareCommand(token, cmd);
        }
        return 105;
    }
    public int declareCommand(String token, Command cmd){
        int statusCode = 107; //Generic error
        try {
            URL url = new URL("http://193.6.19.58:8181/greenhouse/" + token);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setDoOutput(true);
            String parameters = "{\n" + "\"ghId\" : \"" + cmd.ghId + "\",\r\n" + "\"boilerCommand\" : " + "\"" + cmd.boilerCommand + "\"," + "\r\n" + "\"sprinklerCommand\" : " + "\"" + cmd.sprinklerCommand + "\"" + "\n}";

            OutputStream os = conn.getOutputStream();
            os.write(parameters.getBytes());
            os.flush();
            os.close();
            int responseCode = conn.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK)
            {
                BufferedReader reader = new BufferedReader((new InputStreamReader(conn.getInputStream())));
                String inputLine;
                while((inputLine = reader.readLine()) != null)
                    statusCode = Integer.parseInt(inputLine);

                return statusCode;
            }
        } catch (Exception e) {
            System.out.println("URL error!");
        }
        return 106;//Greenhouse not found
    }
}
