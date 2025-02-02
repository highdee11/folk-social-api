package com.highdee.folksocialapi.dto.response.post;

import com.highdee.folksocialapi.enums.PostStatisticTypes;
import com.highdee.folksocialapi.models.post.PostStatistic;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
public class PostStatisticsResponse implements Serializable {

    private final String type;
    private final Map<String, Object> data;

    public PostStatisticsResponse(PostStatistic statistic) {
        this.type = statistic.getType();
        this.data = statistic.getDataAsMap();
    }

    public PostStatisticsResponse(String type, Map<String, Object> data){
        this.type = type;
        this.data = data;
    }

    public static PostStatisticsResponse EmptyPostStatisticsResponse(PostStatisticTypes type){
        return new PostStatisticsResponse(type.name(), new HashMap<>(){});
    }
}
