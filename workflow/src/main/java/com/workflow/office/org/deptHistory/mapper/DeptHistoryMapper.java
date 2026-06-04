package com.workflow.office.org.deptHistory.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.workflow.office.org.deptHistory.domain.DeptHistory;

@Mapper
public interface DeptHistoryMapper {
	int insertHistory(DeptHistory vo);
	
	List<DeptHistory> getDeptHistories(Integer deptId);
}
