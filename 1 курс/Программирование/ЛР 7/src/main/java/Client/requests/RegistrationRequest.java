package Client.requests;

import Common.requests.Request;

public class RegistrationRequest extends Request {
    public RegistrationRequest() {
        super("register", 2, false);
    }
}
