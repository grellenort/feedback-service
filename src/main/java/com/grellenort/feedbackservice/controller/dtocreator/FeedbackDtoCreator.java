package com.grellenort.feedbackservice.controller.dtocreator;

import com.grellenort.feedbackservice.controller.dto.FeedbackDto;
import com.grellenort.feedbackservice.domain.vo.FeedbackVo;
import org.springframework.stereotype.Component;

@Component
public class FeedbackDtoCreator implements DtoCreator<FeedbackVo, FeedbackDto>{

	@Override
	public FeedbackDto create(FeedbackVo feedbackVo) {
		return new FeedbackDto(feedbackVo.getName(),
				feedbackVo.getDescription(),
				feedbackVo.getFeedbackType(),
				feedbackVo.getId(),
				feedbackVo.getCreatedAt());
	}
}
