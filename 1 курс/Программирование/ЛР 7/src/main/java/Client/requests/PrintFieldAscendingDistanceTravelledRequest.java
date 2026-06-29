package Client.requests;

import Common.requests.Request;

public class PrintFieldAscendingDistanceTravelledRequest extends Request {
    public PrintFieldAscendingDistanceTravelledRequest(Object[] args, String login, String password) {
        super("print_field_ascending_distance_travelled", args, 0, false, login, password);
    }

    public PrintFieldAscendingDistanceTravelledRequest() {
        super("print_field_ascending_distance_travelled", 0, false);
    }
}
