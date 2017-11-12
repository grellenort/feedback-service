package com.grellenort.feedbackservice.model;

import com.grellenort.feedbackservice.model.cat.FeedbackType;
import com.grellenort.feedbackservice.model.entity.FeedbackEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

// This class is for test purpose of init in memory repository
// I did not want to add it to @PostConstruct in Dao
@Component
public class StartupRepoInitializer {

	@Autowired
	private FeedbackDao feedbackDao;

	@EventListener(ContextRefreshedEvent.class)
	public void initDb() {
		feedbackDao.insert(new FeedbackEntity("Stark", "Je to dobre.", FeedbackType.POSITIVE));
		feedbackDao.insert(new FeedbackEntity("Lanister", "Drink and know things.", FeedbackType.POSITIVE));
		feedbackDao.insert(new FeedbackEntity("Hound", "I am flamed.", FeedbackType.POSITIVE));
		feedbackDao.insert(new FeedbackEntity("The Mountain", "Like a mellon.", FeedbackType.POSITIVE));
		feedbackDao.insert(new FeedbackEntity("Frey", "Nice wedding day.", FeedbackType.NEGATIVE));
		feedbackDao.insert(new FeedbackEntity("Stransky", "I do not know.", FeedbackType.NEUTRAL));
	}
}
