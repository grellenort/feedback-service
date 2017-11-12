package com.grellenort.feedbackservice.model;

import com.grellenort.feedbackservice.model.entity.FeedbackEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
@Scope("singleton")//is default scope but for purpose of demonstration
// that repo should be only one persisting instance.
public class FeedbackDao {

	private final AtomicInteger idCounter = new AtomicInteger(0);

	// Map Name->Entity
	private final Map<String, FeedbackEntity> repository = new HashMap<>();

	private void uniqueIndexSimulation(final FeedbackEntity feedbackEntity) {
		final boolean uniqueCorrupted = exists(feedbackEntity);
		if (uniqueCorrupted) {
			throw new RuntimeException("neco");// TODO refactor to other type.
		}
	}

	public boolean exists(final FeedbackEntity feedbackEntity) {
		return repository.containsKey(feedbackEntity.getName());
	}

	public FeedbackEntity insert(final FeedbackEntity feedbackEntity) {

		uniqueIndexSimulation(feedbackEntity);

		final int id = idCounter.getAndIncrement();

		// Because generation of id is in competency of repo, need to recreate entity with id generated.
		final FeedbackEntity forPersist = new FeedbackEntity(id,
				feedbackEntity.getName(),
				feedbackEntity.getDescription(),
				feedbackEntity.getFeedbackType(),
				LocalDateTime.now()
		);

		repository.put(forPersist.getName(), forPersist);

		return forPersist;
	}

	public Optional<FeedbackEntity> findBy(final String name) {
		return Optional.ofNullable(repository.get(name));
	}

	public List<FeedbackEntity> findAll() {
		return repository.entrySet().stream()
				.map(Map.Entry::getValue)
				.collect(Collectors.toList());
	}


}
