package com.grellenort.feedbackservice.controller;

import com.grellenort.feedbackservice.controller.dto.FeedbackDto;
import com.grellenort.feedbackservice.controller.dto.req.FeedbackCreateReqDto;
import com.grellenort.feedbackservice.controller.dtocreator.FeedbackDtoCreator;
import com.grellenort.feedbackservice.controller.vomapper.FeedBackCreateVoMapper;
import com.grellenort.feedbackservice.domain.FeedbackService;
import com.grellenort.feedbackservice.domain.vo.FeedbackVo;
import com.grellenort.feedbackservice.error.ErrorCode;
import com.grellenort.feedbackservice.error.FeedbackRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/category")
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;

	@Autowired
	private FeedBackCreateVoMapper feedBackCreateVoMapper;
	@Autowired
	private FeedbackDtoCreator feedbackDtoCreator;

	@PostMapping(
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FeedbackDto> create(@RequestBody final FeedbackCreateReqDto feedbackCreateReqDto) {
		final FeedbackVo createdVo = feedbackService.create(feedBackCreateVoMapper.map(feedbackCreateReqDto));

		final FeedbackDto feedbackDto = feedbackDtoCreator.create(createdVo);
		return new ResponseEntity<>(feedbackDto, HttpStatus.CREATED);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FeedbackDto> filterBy(@RequestParam final String name) {

		return feedbackService.findBy(name)
				.map(vo -> feedbackDtoCreator.create(vo))
				.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
				.orElseThrow(() -> new FeedbackRuntimeException(ErrorCode.NOT_FOUND));
	}

	@GetMapping(
			path = "/find-all",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<FeedbackDto> findAll() {
		return feedbackService.findAll().stream()
				.map(feedbackDtoCreator::create)
				.collect(Collectors.toList());
	}
}