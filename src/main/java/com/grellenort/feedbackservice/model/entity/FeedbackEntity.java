package com.grellenort.feedbackservice.model.entity;

import com.grellenort.feedbackservice.model.cat.FeedbackType;

import java.time.LocalDateTime;

public class FeedbackEntity {

	private final Integer id;
	private final String name;
	private final String description;
	private final FeedbackType feedbackType;
	private final LocalDateTime createdAt;


	public FeedbackEntity(String name, String description, FeedbackType feedbackType) {
		this(null, name, description, feedbackType, null);
	}

	public FeedbackEntity(Integer id, String name, String description, FeedbackType feedbackType, LocalDateTime createdAt) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.feedbackType = feedbackType;
		this.createdAt = createdAt;
	}


	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public FeedbackType getFeedbackType() {
		return feedbackType;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
