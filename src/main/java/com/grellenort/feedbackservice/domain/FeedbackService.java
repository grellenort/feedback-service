package com.grellenort.feedbackservice.domain;

import com.grellenort.feedbackservice.domain.vo.FeedbackVo;
import com.grellenort.feedbackservice.domain.vo.req.FeedbackCreateReqVo;
import com.grellenort.feedbackservice.error.ErrorCode;
import com.grellenort.feedbackservice.error.FeedbackRuntimeException;
import com.grellenort.feedbackservice.model.FeedbackDao;
import com.grellenort.feedbackservice.model.entity.FeedbackEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

	private static final int NAME_MAX_LEN = 50;
	private static final int DESCRIPTION_MAX_LEN = 500;

	@Autowired
	private FeedbackDao feedbackDao;

	public FeedbackVo create(final FeedbackCreateReqVo reqVo) {
		validate(reqVo);
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

	public List<FeedbackVo> findAll() {
		return feedbackDao.findAll().stream()
				.map(FeedbackVo::new)
				.collect(Collectors.toList());
	}

	private void validate(final FeedbackCreateReqVo reqVo) {

		final String name = reqVo.getName();

		if (StringUtils.isEmpty(name)) {
			throw new FeedbackRuntimeException("Name attribute is mandatory", ErrorCode.MISSING_MANDATORY_ATTRIBUTE);
		}

		if (name.length() > NAME_MAX_LEN) {
			throw new FeedbackRuntimeException("Name attribute is allowed only to have max: " + NAME_MAX_LEN + " characters.",
					ErrorCode.INPUT_IS_TOO_LONG);
		}

		final String description = reqVo.getDescription();

		if (StringUtils.isEmpty(description)) {
			throw new FeedbackRuntimeException("Description attribute is mandatory", ErrorCode.MISSING_MANDATORY_ATTRIBUTE);
		}

		if (description.length() > DESCRIPTION_MAX_LEN) {
			throw new FeedbackRuntimeException("Description attribute is allowed only to have max: " + DESCRIPTION_MAX_LEN + " characters.",
					ErrorCode.INPUT_IS_TOO_LONG);
		}

		if (reqVo.getType() == null) {
			throw new FeedbackRuntimeException("Type attribute can not be null.", ErrorCode.MISSING_MANDATORY_ATTRIBUTE);
		}
	}


}
