package Client.requests;

import java.io.Serializable;
import java.util.Arrays;

public abstract class Request implements Serializable {
    protected String commandToken;
    protected Object[] args;
    protected int simpleArgumentsRequired;
    protected boolean vehicleRequired;

    public Request(String commandToken, Object[] args, int simpleArgumentsRequired, boolean vehicleRequired) {
        this.commandToken = commandToken;
        this.args = args;
        this.simpleArgumentsRequired = simpleArgumentsRequired;
        this.vehicleRequired = vehicleRequired;
    }

    public Request(String commandToken, int simpleArgumentsRequired, boolean vehicleRequired) {
        this.commandToken = commandToken;
        this.simpleArgumentsRequired = simpleArgumentsRequired;
        this.vehicleRequired = vehicleRequired;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String getCommandToken() {
        return commandToken;
    }

    public void setCommandToken(String commandToken) {
        this.commandToken = commandToken;
    }

    public boolean requiresVehicle() {
        return vehicleRequired;
    }

    public int getSimpleArgumentsRequired() {
        return simpleArgumentsRequired;
    }

    public void setSimpleArgumentsRequired(int simpleArgumentsRequired) {
        this.simpleArgumentsRequired = simpleArgumentsRequired;
    }

    @Override
    public String toString() {
        return "Request{" +
                "commandToken='" + commandToken + '\'' +
                ", args=" + Arrays.toString(args) +
                ", simpleArgumentsRequired=" + simpleArgumentsRequired +
                ", vehicleRequired=" + vehicleRequired +
                '}';
    }
}
