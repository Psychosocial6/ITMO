package functioning;
import enums.FlowDirection;

import static java.lang.Math.*;

public class CoordinatesUtil {

    public static double[] newCoordinates(double time, double direction, FlowDirection flowDirection, double flowVelocity, double width, double power, double x, double y) {
        double deltaX;
        double deltaY;
        direction *= (PI / 180);

        deltaX = cos(direction) * power * time;
        if (flowDirection == FlowDirection.RIGHT) {
            deltaX += flowVelocity * time;
        }
        else {
            deltaX -= flowVelocity * time;
        }

        deltaY = sin(direction) * power * time;

        x += deltaX;

        if (y + deltaY >= width) {
            y = width;
        }
        else if (y + deltaY <= 0) {
            y = 0;
        }
        else {
            y += deltaY;
        }
        double[] xy = {x, y};
        return xy;
    }

}
