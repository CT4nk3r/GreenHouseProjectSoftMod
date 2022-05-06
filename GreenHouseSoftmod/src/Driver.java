public class Driver implements IDriver {
    @Override
    public int sendCommand(Greenhouse greenHouse, String token, double boilerValue, double sprinklerValue) {
        if (token != null) {
            String boilerCommand = "";
            String sprinklerCommand = "";
            String ghId = greenHouse.ghId;
            if (boilerValue != 0)
                boilerCommand = "bup" + Math.round(boilerValue) + "c";
            if (sprinklerValue != 0)
                sprinklerCommand = "son" + Math.round(sprinklerValue) + "l";
            Command cmd = new Command(ghId, boilerCommand, sprinklerCommand);
            if (boilerCommand != "")
                System.out.println("Forwarding command for boiler - " + boilerCommand);
            if (sprinklerCommand != "")
                System.out.println("Forwarding command for sprinkler - " + sprinklerCommand);
            System.out.println("Sending command request...");
            return cmd.send(token);
        }
        return 105;
    }
}
