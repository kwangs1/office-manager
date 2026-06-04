package com.workflow.office.org.position.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.workflow.office.org.position.domain.Position;

@Mapper
public interface PositionMapper {

	List<Position> findAll();
	
	int register(Position vo);
	
	void update(Position vo);
	
	int delete(Integer id);
	
	Position findById(Integer id);
}
