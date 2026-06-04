package com.workflow.office.org.dept.service;

import java.util.List;

import com.workflow.office.org.dept.dto.DepartmentDTO;
import com.workflow.office.org.dept.dto.OrgNode;

public interface DeptService {

	List<OrgNode> getCompleteTree();
	
	DepartmentDTO.Response selectOne(Integer id);
	
	DepartmentDTO.Response register(DepartmentDTO.CreateRequest requestDto);
	
	DepartmentDTO.Response update(Integer id, DepartmentDTO.UpdateRequest updateDto);
	
	DepartmentDTO.Response delete(Integer id);
}
