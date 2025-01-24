package com.highdee.folksocialapi.dto.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class UserPreferenceResponse implements Serializable {
    public UserPreferenceResponse(boolean hasInterest) {
        this.hasInterest = hasInterest;
    }

    @JsonProperty("has_interest")
    private boolean hasInterest;

    public boolean isHasInterest() {
        return hasInterest;
    }

    public void setHasInterest(boolean hasInterest) {
        this.hasInterest = hasInterest;
    }
}
