package com.grellenort.feedbackservice.controller.dtocreator;

public interface DtoCreator<VO, DTO> {

	DTO create(final VO vo);

}
