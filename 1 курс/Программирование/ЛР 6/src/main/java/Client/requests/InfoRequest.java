package Client.requests;

public class InfoRequest extends Request {
    public InfoRequest(Object[] args) {
        super("info", args, 0, false);
    }

    public InfoRequest() {
        super("info", 0, false);
    }
}
