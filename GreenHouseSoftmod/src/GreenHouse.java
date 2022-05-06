public class GreenHouse {
    public String ghId;
    public String description;
    public int temperature_min;
    public int temperature_opt;
    public int humidity_min;
    public int volume;

    public String getGhId() {
        return ghId;
    }

    public String getDescription() {
        return description;
    }

    public int getHumidity_min() {
        return humidity_min;
    }

    public int getTemperature_min() {
        return temperature_min;
    }

    public int getTemperature_opt() {
        return temperature_opt;
    }

    public int getVolume() {
        return volume;
    }

    public void setGhId(String ghId) {
        this.ghId = ghId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHumidity_min(int humidity_min) {
        this.humidity_min = humidity_min;
    }

    public void setTemperature_min(int temperature_min) {
        this.temperature_min = temperature_min;
    }

    public void setTemperature_opt(int temperature_opt) {
        this.temperature_opt = temperature_opt;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public String toString()
    {
        String res = "";
        res += ("ghId: " + getGhId()+"\n");
        res += ("description: " + getDescription()+"\n");
        res += ("temperature_min: " + getTemperature_min()+"\n");
        res += ("temperature_opt: " + getTemperature_opt()+"\n");
        res += ("humidity_min: " + getHumidity_min()+"\n");
        res += ("volume: " + getVolume()+"\n");
        return res;
    }
}
