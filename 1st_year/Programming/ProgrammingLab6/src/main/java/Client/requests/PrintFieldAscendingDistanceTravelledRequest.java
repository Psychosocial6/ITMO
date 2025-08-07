package Client.requests;

public class PrintFieldAscendingDistanceTravelledRequest extends Request {
    public PrintFieldAscendingDistanceTravelledRequest(Object[] args) {
        super("print_field_ascending_distance_travelled", args, 0, false);
    }

    public PrintFieldAscendingDistanceTravelledRequest() {
        super("print_field_ascending_distance_travelled", 0, false);
    }
}
