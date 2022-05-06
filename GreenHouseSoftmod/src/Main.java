import javax.sound.midi.ControllerEventListener;
import java.util.List;
//Bősze Máté, Braghini Benjamin Matthew, Bognár Balázs, Gyenti Kristóf
//Szoftmod beadandó program
public class Main {
    public static void main(String[] args){
        Loader loader = new Loader("greenhouse.json");
        List<GreenHouse> greenHousesList = loader.loadGreenHouse().getGreenHouseList();
        Monitor monitor = new Monitor();

    }
}
