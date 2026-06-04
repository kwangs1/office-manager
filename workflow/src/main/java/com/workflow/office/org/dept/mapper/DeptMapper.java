package com.workflow.office.org.dept.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.workflow.office.org.dept.domain.Department;

@Mapper
public interface DeptMapper {
	
	List<Department> selectDepartmentList();
	
	Department findById(Integer id);
	
	int register(Department vo);
	
	int getNextSortOrder(Integer pid);
	
	void update(Department vo);
	
	int delete(Department vo);
}
