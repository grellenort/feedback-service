package com.grellenort.feedbackservice.controller.vomapper;

public interface Dto2VoMapper<DTO, VO> {
    VO map(final DTO dto);
}
