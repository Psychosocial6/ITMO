package Client.requests;


public class FilterContainsNameRequest extends Request {
    public FilterContainsNameRequest(Object[] args) {
        super("filter_contains_name", args, 1, false);
    }

    public FilterContainsNameRequest() {
        super("filter_contains_name", 1, false);
    }
}
