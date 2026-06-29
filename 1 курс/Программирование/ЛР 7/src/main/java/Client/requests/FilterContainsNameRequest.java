package Client.requests;


import Common.requests.Request;

public class FilterContainsNameRequest extends Request {
    public FilterContainsNameRequest(Object[] args, String login, String password) {
        super("filter_contains_name", args, 1, false, login, password);
    }

    public FilterContainsNameRequest() {
        super("filter_contains_name", 1, false);
    }
}
