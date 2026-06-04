package com.workflow.office.org.deptHistory.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.workflow.office.org.deptHistory.domain.DeptHistory;
import com.workflow.office.org.deptHistory.dto.DeptHistoryDTO;
import com.workflow.office.org.deptHistory.mapper.DeptHistoryMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeptHistoryServiceImpl implements DeptHistoryService{

	private final DeptHistoryMapper deptHistoryMapper;
	
	public List<DeptHistoryDTO.Response> getDeptHistories(Integer deptId){
		if (deptId == null) {
			throw new RuntimeException("common.err.type_mismatch");
		}
		List<DeptHistory> deptHistories = deptHistoryMapper.getDeptHistories(deptId);
		
		if (deptHistories == null) return new ArrayList<>();
		
		return deptHistories.stream()
				.map(DeptHistoryDTO.Response::new)
				.toList();
	}
}
