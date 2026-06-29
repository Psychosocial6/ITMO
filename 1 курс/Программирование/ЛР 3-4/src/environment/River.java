package environment;

import boats.Boat;
import enums.FlowDirection;

import java.util.ArrayList;

public class River {

    private ArrayList<Boat> boats;
    private double flowVelocity;
    private final FlowDirection FLOW_DIRECTION;
    private final double WIDTH;

    public River(FlowDirection flowDirection, double width, double flowVelocity, ArrayList<Boat> boats) {
        this.FLOW_DIRECTION = flowDirection;
        this.WIDTH = width;
        this.flowVelocity = flowVelocity;
        this.boats = boats;
    }

    public void addBoat(Boat boat) {
        boats.add(boat);
    }

    public ArrayList<Boat> getBoats() {
        return boats;
    }

    public void setBoats(ArrayList<Boat> boats) {
        this.boats = boats;
    }

    public double getFlowVelocity() {
        return flowVelocity;
    }

    public void setFlowVelocity(double flowVelocity) {
        this.flowVelocity = flowVelocity;
    }

    public FlowDirection getflowDirection() {
        return FLOW_DIRECTION;
    }

    public double getWidth() {
        return WIDTH;
    }
}
