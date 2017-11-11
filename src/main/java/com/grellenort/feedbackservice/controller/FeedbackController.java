package com.grellenort.feedbackservice.controller;

import com.grellenort.feedbackservice.controller.dto.FeedbackDto;
import com.grellenort.feedbackservice.controller.dto.req.FeedbackCreateReqDto;
import com.grellenort.feedbackservice.domain.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "/api/category")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public FeedbackDto create(@RequestBody final FeedbackCreateReqDto feedbackCreateReqDto) {
        return null;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FeedbackDto filterBy(@RequestParam final String name) {
        return null;
    }

    @GetMapping(path = "/find-all")
    public List<FeedbackDto> findAll() {
        return Collections.emptyList();
    }
}
