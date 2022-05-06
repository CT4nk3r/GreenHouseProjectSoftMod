public class GreenHouse {
    public String ghId;
    public String description;
    public int tempMinimum;
    public int tempOptimal;
    public int humidity_min;
    public int volume;

    public String getGhId() {
        return ghId;
    }

    public String getDescription() {
        return description;
    }

    public int getTempMinimum() {
        return tempMinimum;
    }

    public int getTempOptimal() {
        return tempOptimal;
    }

    public int getHumidity_min() {
        return humidity_min;
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

    public void setTempMinimum(int tempMinimum) {
        this.tempMinimum = tempMinimum;
    }

    public void setTempOptimal(int tempOptimal) {
        this.tempOptimal = tempOptimal;
    }

    public void setHumidity_min(int humidity_min) {
        this.humidity_min = humidity_min;
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
        res += ("temperature_min: " + getTempMinimum()+"\n");
        res += ("temperature_opt: " + getTempOptimal()+"\n");
        res += ("humidity_min: " + getHumidity_min()+"\n");
        res += ("volume: " + getVolume()+"\n");
        return res;
    }
}
