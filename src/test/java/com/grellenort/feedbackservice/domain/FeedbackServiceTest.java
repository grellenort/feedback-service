package com.grellenort.feedbackservice.domain;

import com.grellenort.feedbackservice.domain.vo.FeedbackVo;
import com.grellenort.feedbackservice.domain.vo.req.FeedbackCreateReqVo;
import com.grellenort.feedbackservice.error.FeedbackRuntimeException;
import com.grellenort.feedbackservice.model.FeedbackDao;
import com.grellenort.feedbackservice.model.cat.FeedbackType;
import com.grellenort.feedbackservice.model.entity.FeedbackEntity;
import com.grellenort.feedbackservice.testutils.GeneratorUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class FeedbackServiceTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@InjectMocks
	private FeedbackService feedbackService; //Class under test.

	@Mock
	private FeedbackDao feedbackDao;

	@Test
	public void create_nullName_error() {
		thrown.expect(FeedbackRuntimeException.class);
		thrown.expectMessage("Name attribute is mandatory");
		feedbackService.create(new FeedbackCreateReqVo(null, "neco", FeedbackType.POSITIVE));
	}

	@Test
	public void create_nameTooLong_error() {
		thrown.expect(FeedbackRuntimeException.class);
		thrown.expectMessage("Name attribute is allowed only to have max: 50 characters.");
		feedbackService.create(new FeedbackCreateReqVo(GeneratorUtils.generateLongString(70), "neco", FeedbackType.POSITIVE));
	}

	@Test
	public void create_nullDescription_error() {
		thrown.expect(FeedbackRuntimeException.class);
		thrown.expectMessage("Description attribute is mandatory");
		feedbackService.create(new FeedbackCreateReqVo("David", null, FeedbackType.POSITIVE));
	}

	@Test
	public void create_descriptionTooLong_error() {
		thrown.expect(FeedbackRuntimeException.class);
		thrown.expectMessage("Description attribute is allowed only to have max: 500 characters.");
		feedbackService.create(new FeedbackCreateReqVo("David", GeneratorUtils.generateLongString(700), FeedbackType.POSITIVE));
	}

	@Test
	public void create_nullFeedbackType_error() {
		thrown.expect(FeedbackRuntimeException.class);
		thrown.expectMessage("Type attribute can not be null.");
		feedbackService.create(new FeedbackCreateReqVo("David", "neco", null));
	}

	@Test
	public void findBy_nameNull_success() {
		Mockito.when(feedbackDao.findBy(null)).thenReturn(Optional.empty());
		final Optional<FeedbackVo> feedbackVo = feedbackService.findBy(null);
		Assert.assertFalse(feedbackVo.isPresent());
	}

	@Test
	public void findBy_nameFilled_success() {
		final String expectedName = "David";
		final FeedbackEntity expectedEntity
				= new FeedbackEntity(5, expectedName, "Positivni feedback", FeedbackType.POSITIVE, LocalDateTime.now());
		Mockito.when(feedbackDao.findBy(expectedName)).thenReturn(Optional.of(expectedEntity));
		final FeedbackVo feedbackVo = feedbackService.findBy(expectedName)
				.orElseThrow(() -> new AssertionError("No value object returned from service"));

		Assert.assertEquals(expectedEntity.getId(), feedbackVo.getId());
		Assert.assertEquals(expectedEntity.getCreatedAt(), feedbackVo.getCreatedAt());
		Assert.assertEquals(expectedEntity.getFeedbackType(), feedbackVo.getFeedbackType());
		Assert.assertEquals(expectedEntity.getDescription(), feedbackVo.getDescription());
		Assert.assertEquals(expectedEntity.getName(), feedbackVo.getName());
	}

	@Test
	public void findAll_success() {
		final Integer expectedId = 0;
		final FeedbackEntity feedbackEntityFirst
				= new FeedbackEntity(expectedId, "Stransky", "Good review.", FeedbackType.POSITIVE, LocalDateTime.now());

		final FeedbackEntity feedbackEntitySecond
				= new FeedbackEntity(1, "Petr", "Bad review", FeedbackType.NEGATIVE, LocalDateTime.now());

		final List<FeedbackEntity> daoResult = Arrays.asList(feedbackEntityFirst, feedbackEntitySecond);
		Mockito.when(feedbackDao.findAll()).thenReturn(daoResult);

		final List<FeedbackVo> result = feedbackService.findAll();

		Assert.assertEquals(daoResult.size(), result.size());

		final FeedbackVo firstVo = result.get(0);

		Assert.assertEquals(feedbackEntityFirst.getName(), firstVo.getName());
		Assert.assertEquals(feedbackEntityFirst.getDescription(), firstVo.getDescription());
		Assert.assertEquals(feedbackEntityFirst.getFeedbackType(), firstVo.getFeedbackType());
		Assert.assertEquals(expectedId, feedbackEntityFirst.getId());
		Assert.assertEquals(feedbackEntityFirst.getCreatedAt(), firstVo.getCreatedAt());

		// Can continue with asserts on second item but I do not.
	}


}