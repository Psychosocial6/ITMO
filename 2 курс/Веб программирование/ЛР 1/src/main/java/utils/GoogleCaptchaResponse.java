package utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleCaptchaResponse {
    @JsonProperty("success")
    private boolean response;

    public GoogleCaptchaResponse(boolean response) {
        this.response = response;
    }

    public GoogleCaptchaResponse() {
    }

    public boolean getResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }
}
