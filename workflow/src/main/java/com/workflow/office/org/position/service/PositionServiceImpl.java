package com.workflow.office.org.position.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workflow.office.global.response.dto.DataNotFoundException;
import com.workflow.office.org.position.domain.Position;
import com.workflow.office.org.position.dto.PositionDTO;
import com.workflow.office.org.position.mapper.PositionMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService{

	private final PositionMapper positionMapper;
	
	@Override
	public List<PositionDTO.Response> list(){
		List<Position> posList = positionMapper.findAll();
		
		if (posList == null) return new ArrayList<>();
		return posList.stream()
				.map(PositionDTO.Response::new)
				.toList();
	}
	
	@Override
	@Transactional
	public PositionDTO.Response register(PositionDTO.CreateRequest requestDto) {
		Position vo = requestDto.toEntity();
		vo.validateCreate();
		
		if (positionMapper.register(vo) <= 0) {
			throw new IllegalStateException("common.err.server_error");
		}
		return new PositionDTO.Response(vo);
	}
	
	@Override
	@Transactional
	public PositionDTO.Response update(Integer id, PositionDTO.CreateRequest updateDto) {
		Position oldPos = (id != null) ? positionMapper.findById(id) : null;
		if (oldPos == null) {
			throw new DataNotFoundException("position.not.found");
		}
		oldPos.changePosition(updateDto.getName(), updateDto.getSecLevel());
		positionMapper.update(oldPos);
		
		return new PositionDTO.Response(oldPos);
	}
	
	@Override
	@Transactional
	public PositionDTO.Response delete(Integer id) {
		Position vo = (id != null) ? positionMapper.findById(id) : null;
        if (vo == null) {
            throw new DataNotFoundException("position.not.found");
        }
        
        if (positionMapper.delete(id) <= 0) {
            throw new IllegalStateException("common.err.server_error");
        }
		return new PositionDTO.Response(vo);
	}
}
