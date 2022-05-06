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

    public void print()
    {
        System.out.println("ghId: " + getGhId());
        System.out.println("description: " + getDescription());
        System.out.println("temperature_min: " + getTempMinimum());
        System.out.println("temperature_opt: " + getTempOptimal());
        System.out.println("humidity_min: " + getHumidity_min());
        System.out.println("volume: " + getVolume());
    }
}
