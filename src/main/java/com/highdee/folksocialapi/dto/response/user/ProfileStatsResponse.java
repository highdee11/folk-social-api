package com.highdee.folksocialapi.dto.response.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProfileStatsResponse implements Serializable {
    private int following = 0;
    private int followers = 0;
}
