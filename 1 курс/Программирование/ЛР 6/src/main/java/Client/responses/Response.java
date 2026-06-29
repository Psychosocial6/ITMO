package Client.responses;

import java.io.Serializable;

public class Response implements Serializable {
    protected String errorMessage;
    protected String resultMessage;

    public Response(String errorMessage, String resultMessage) {
        this.errorMessage = errorMessage;
        this.resultMessage = resultMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
