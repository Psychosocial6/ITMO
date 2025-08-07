package Client.requests;

public class InsertRequest extends Request {
    public InsertRequest(Object[] args) {
        super("insert", args, 1, true);
    }

    public InsertRequest() {
        super("insert", 1, true);
    }
}
