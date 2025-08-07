package people;

import attributes.Spyglass;
import boats.Boat;
import environment.River;
import java.util.ArrayList;

public class StoryTeller extends Human {

    private Spyglass spyglass;

    public StoryTeller() {
        super("Безымянный");
    }

    public StoryTeller(String name) {
        super(name);
    }

    public void useSpyglass(River river) {
        ArrayList<Boat> boats = spyglass.lookForBoats(river);
        ArrayList<Human> people = spyglass.lookForPeople(river);
        if (boats.isEmpty()) {
            System.out.printf("%s не видит через подзорную трубу плавсредств на реке\n", name);
        }
        if (people.isEmpty()) {
            System.out.printf("%s не видит через подзорную трубу людей на реке\n", name);
        }
        for (Boat boat : boats) {
            System.out.printf("%s видит через подзорную трубу %s на реке\n", name, boat);
        }
        for (Human human : people) {
            System.out.printf("%s видит через подзорную трубу человека %s\n", name, human);
        }
    }

    public void destroyBoat(Boat boat) {
        if (boat.getyCoordinate() == 0) {
            boat.setHp(0);
            System.out.println(String.format("%s сломал %s", name, boat));
        }
    }

    public void setSpyglass(Spyglass spyglass) {
        this.spyglass = spyglass;
    }
}
