package com.grellenort.feedbackservice.controller.dto.req;

import com.grellenort.feedbackservice.model.cat.FeedbackType;

public class FeedbackCreateReqDto {

    private String name;
    private String content;
    private FeedbackType feedbackType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public FeedbackType getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(FeedbackType feedbackType) {
        this.feedbackType = feedbackType;
    }
}
