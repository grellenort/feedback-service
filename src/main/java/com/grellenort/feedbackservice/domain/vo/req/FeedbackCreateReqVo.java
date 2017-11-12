package com.grellenort.feedbackservice.domain.vo.req;

import com.grellenort.feedbackservice.model.cat.FeedbackType;

public class FeedbackCreateReqVo {
    private final String name;
    private final String description;
    private final FeedbackType type;

    public FeedbackCreateReqVo(String name, String description, FeedbackType type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public FeedbackType getType() {
        return type;
    }
}
