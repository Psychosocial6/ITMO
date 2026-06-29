package Client.requests;

public class HelpRequest extends Request {

    public HelpRequest(Object[] args) {
        super("help", args, 0, false);
    }

    public HelpRequest() {
        super("help", 0, false);
    }
}
