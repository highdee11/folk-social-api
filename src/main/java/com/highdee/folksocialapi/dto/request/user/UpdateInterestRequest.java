package com.highdee.folksocialapi.dto.request.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

public class UpdateInterestRequest {

    @NotEmpty(message = "No interest was selected")
    private List<String> interests = new ArrayList<>();

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }
}
