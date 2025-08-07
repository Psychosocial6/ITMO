package Client.requests;

public class FilterStartsWithNameRequest extends Request {
    public FilterStartsWithNameRequest(Object[] args) {
        super("filter_starts_with_name", args, 1, false);
    }

    public FilterStartsWithNameRequest() {
        super("filter_starts_with_name", 1, false);
    }
}
