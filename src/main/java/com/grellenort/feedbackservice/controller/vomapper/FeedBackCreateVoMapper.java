package com.grellenort.feedbackservice.controller.vomapper;

import com.grellenort.feedbackservice.controller.dto.req.FeedbackCreateReqDto;
import com.grellenort.feedbackservice.domain.vo.req.FeedbackCreateReqVo;
import org.springframework.stereotype.Component;

@Component
public class FeedBackCreateVoMapper implements Dto2VoMapper<FeedbackCreateReqDto, FeedbackCreateReqVo> {
	@Override
	public FeedbackCreateReqVo map(FeedbackCreateReqDto feedbackCreateReqDto) {
		return new FeedbackCreateReqVo(feedbackCreateReqDto.getName(),
				feedbackCreateReqDto.getContent(),
				feedbackCreateReqDto.getFeedbackType());
	}
}
