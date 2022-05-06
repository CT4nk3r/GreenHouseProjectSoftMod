public class Greenhouse {
    public String ghId;
    public String description;
    public int temperature_min;

    public int temperature_opt;
    public int humidity_min;
    public int volume;

    public String getGhId() {
        return ghId;
    }

    public void setGhId(String ghId) {
        this.ghId = ghId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTemperature_min() {
        return temperature_min;
    }

    public void setTemperature_min(int temperature_min) {
        this.temperature_min = temperature_min;
    }

    public int getTemperature_opt() {
        return temperature_opt;
    }

    public void setTemperature_opt(int temperature_opt) {
        this.temperature_opt = temperature_opt;
    }

    public int getHumidity_min() {
        return humidity_min;
    }

    public void setHumidity_min(int humidity_min) {
        this.humidity_min = humidity_min;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }


    public void print() {
        System.out.println("ghId: " + this.ghId);
        System.out.println("Description: " + this.description);
        System.out.println("Temperature_min: " + this.temperature_min);
        System.out.println("Temperature_opt: " + this.temperature_opt);
        System.out.println("Humidity_min: " + this.humidity_min);
        System.out.println("Volume: " + this.volume);
    }

    @Override
    public String toString() {
        String res = ("ghId: " + this.ghId + "\n");
        res += ("Description: " + this.description + "\n");
        res += ("Temperature_min: " + this.temperature_min + "\n");
        res += ("Temperature_opt: " + this.temperature_opt + "\n");
        res += ("Humidity_min: " + this.humidity_min + "\n");
        res += ("Volume: " + this.volume + "\n");
        return res;
    }
}
