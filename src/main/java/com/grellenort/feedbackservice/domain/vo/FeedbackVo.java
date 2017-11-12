package com.grellenort.feedbackservice.domain.vo;

import com.grellenort.feedbackservice.model.cat.FeedbackType;
import com.grellenort.feedbackservice.model.entity.FeedbackEntity;

import java.time.LocalDateTime;

public class FeedbackVo {

	private final Integer id;
	private final String name;
	private final String description;
	private final FeedbackType feedbackType;
	private final LocalDateTime createdAt;

	public FeedbackVo(Integer id, String name, String description, FeedbackType feedbackType, LocalDateTime createdAt) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.feedbackType = feedbackType;
		this.createdAt = createdAt;
	}

	public FeedbackVo(final FeedbackEntity feedbackEntity) {
		this(feedbackEntity.getId(),
				feedbackEntity.getName(),
				feedbackEntity.getDescription(),
				feedbackEntity.getFeedbackType(),
				feedbackEntity.getCreatedAt()
		);
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
