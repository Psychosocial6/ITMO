package Client.managers;

import Client.requests.*;
import java.util.HashMap;

public class RequestManager {

    private HashMap<String, Request> requests = new HashMap<>();

    public RequestManager() {
        requests.put("clear", new ClearRequest());
        requests.put("execute_script", new ExecuteRequest());
        requests.put("filter_contains_name", new FilterContainsNameRequest());
        requests.put("filter_starts_with_name", new FilterStartsWithNameRequest());
        requests.put("help", new HelpRequest());
        requests.put("info", new InfoRequest());
        requests.put("insert", new InsertRequest());
        requests.put("print_field_ascending_distance_travelled", new PrintFieldAscendingDistanceTravelledRequest());
        requests.put("remove_key", new RemoveKeyRequest());
        requests.put("remove_lower", new RemoveLowerRequest());
        requests.put("replace_if_greater", new ReplaceIfGreaterRequest());
        requests.put("replace_if_lower", new ReplaceIfLowerRequest());
        requests.put("show", new ShowRequest());
    }

    public Request getRequest(String key) {
        return requests.get(key);
    }

    public HashMap<String, Request> getRequests() {
        return requests;
    }

}
