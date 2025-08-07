package Client.requests;

public class ShowRequest extends Request {
    public ShowRequest(Object[] args) {
        super("show", args, 0, false);
    }

    public ShowRequest() {
        super("show", 0, false);
    }
}
