package Client.requests;

import Common.requests.Request;

public class FilterStartsWithNameRequest extends Request {
    public FilterStartsWithNameRequest(Object[] args, String login, String password) {
        super("filter_starts_with_name", args, 1, false, login, password);
    }

    public FilterStartsWithNameRequest() {
        super("filter_starts_with_name", 1, false);
    }
}
