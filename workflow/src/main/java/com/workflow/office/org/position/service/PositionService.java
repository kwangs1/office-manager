package com.workflow.office.org.position.service;

import java.util.List;

import com.workflow.office.org.position.dto.PositionDTO;

public interface PositionService {

	List<PositionDTO.Response> list();
	
	PositionDTO.Response register(PositionDTO.CreateRequest requestDto);
	
	PositionDTO.Response update(Integer id, PositionDTO.CreateRequest updateDto);
	
	PositionDTO.Response delete(Integer id);
}
