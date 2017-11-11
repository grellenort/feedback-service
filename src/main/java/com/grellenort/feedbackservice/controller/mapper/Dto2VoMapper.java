package com.grellenort.feedbackservice.controller.mapper;

public interface Dto2VoMapper<DTO, VO> {
    VO map(final DTO dto);
}
