package com.grellenort.feedbackservice.domain;

import com.grellenort.feedbackservice.domain.vo.FeedbackVo;
import com.grellenort.feedbackservice.domain.vo.req.FeedbackCreateReqVo;
import com.grellenort.feedbackservice.model.FeedbackDao;
import com.grellenort.feedbackservice.model.entity.FeedbackEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

	@Autowired
	private FeedbackDao feedbackDao;

	public FeedbackVo create(final FeedbackCreateReqVo reqVo){

		final FeedbackEntity createEntity = new FeedbackEntity(reqVo.getName(),
				reqVo.getDescription(),
				reqVo.getType());

		final FeedbackEntity persisted = feedbackDao.insert(createEntity);

		return new FeedbackVo(persisted);
	}

	public Optional<FeedbackVo> findBy(final String name) {
		return feedbackDao.findBy(name)
				.map(FeedbackVo::new);
	}

	public List<FeedbackVo> findAll(){
		return feedbackDao.findAll().stream()
				.map(FeedbackVo::new)
				.collect(Collectors.toList());
	}


}
