package com.workflow.office.org.deptHistory.service;

import java.util.List;

import com.workflow.office.org.deptHistory.dto.DeptHistoryDTO;

public interface DeptHistoryService {

	List<DeptHistoryDTO.Response> getDeptHistories(Integer deptId);
}
