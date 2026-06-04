package com.workflow.office.org.dept.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workflow.office.global.response.dto.DataNotFoundException;
import com.workflow.office.org.dept.domain.Department;
import com.workflow.office.org.dept.dto.DepartmentDTO;
import com.workflow.office.org.dept.dto.OrgNode;
import com.workflow.office.org.dept.mapper.DeptMapper;
import com.workflow.office.org.deptHistory.dto.DeptHistoryDTO;
import com.workflow.office.org.deptHistory.mapper.DeptHistoryMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeptServiceImpl implements DeptService{

	private final DeptMapper deptMapper;
	private final DeptHistoryMapper historyMapper;
	
	@Override
	public List<OrgNode> getCompleteTree(){
		List<Department> deptList = deptMapper.selectDepartmentList();
		
		Map<Integer, OrgNode> nodeMap = new HashMap<>();
		List<OrgNode> allNodes = new ArrayList<>();
		for (Department vo : deptList) {
			OrgNode node = new OrgNode();
			node.setId(vo.getId());
			node.setName(vo.getName());
			node.setPid(vo.getPid());
			node.setDepth(vo.getDepth());
			node.setSortOr(vo.getSortOr());
			
			nodeMap.put(node.getId(), node);
			allNodes.add(node);
		}
		
		List<OrgNode> rootNodes = new ArrayList<>();
		for (OrgNode node : allNodes) {
			if (node.getPid() == null || node.getPid() == 0) {
				rootNodes.add(node);
			}else {
				OrgNode parent = nodeMap.get(node.getPid());
				if (parent != null) {
					parent.getChildren().add(node);
				}
			}
		}
		return rootNodes;
	}
	
	@Override
	public DepartmentDTO.Response selectOne(Integer id) {
		Department getDetail = (id != null) ? deptMapper.findById(id) : null;
		if (getDetail == null) {
			throw new DataNotFoundException("dept.not.found");
		}
		return new DepartmentDTO.Response(getDetail);
	}
	
	@Override
	@Transactional
	public DepartmentDTO.Response register(DepartmentDTO.CreateRequest request) {
		if (request.getName() == null) {
            throw new IllegalArgumentException("common.err.type_mismatch");
        }
		
		Integer calculatedDepth = 1;
		Integer finalPid = null;
		
		if (request.getPid() != null && request.getPid() > 0) {
			Department parent = deptMapper.findById(request.getPid());
			if (parent == null) {
				throw new IllegalArgumentException("parent.dept.not.found");
			}
			calculatedDepth = parent.getDepth() + 1;
			finalPid = request.getPid();
		} else {
			calculatedDepth = 1;
	        finalPid = null;
		}
		
		int nextSort = deptMapper.getNextSortOrder(finalPid);
		Department dept = Department.builder()
					.name(request.getName())
					.pid(finalPid)
					.depth(calculatedDepth)
					.sortOr(nextSort)
					.status("Y")
					.build();
	    
		dept.validateCreate();
		
		if (deptMapper.register(dept) <= 0) {
			throw new IllegalStateException("common.err.server_error");
		}
		return new DepartmentDTO.Response(dept);
	}
	
	@Override
	@Transactional
	public DepartmentDTO.Response update(Integer id, DepartmentDTO.UpdateRequest updateDto) {
		Department oldDept = (id != null) ? deptMapper.findById(id) : null;
		if (oldDept == null) {
			throw new DataNotFoundException("dept.not.found");
		}
		
		String oldName = oldDept.getName();
		String newName = updateDto.getName();
		
		if (!oldName.equals(newName)) {
			DeptHistoryDTO.CreateRequest historyDto = DeptHistoryDTO.CreateRequest.builder()
					.deptId(id)
					.oldName(oldName)
					.newName(newName)
					.updateReason(updateDto.getUpdateReason())
					.build();
			
			historyMapper.insertHistory(historyDto.toEntiy());
		}
		oldDept.updateDepartment(
				updateDto.getName(), 
				updateDto.getSortOr(),
				updateDto.getPid(),
				updateDto.getStatus()
		);
		deptMapper.update(oldDept);
		
		Department updatedDept = deptMapper.findById(id);
		return new DepartmentDTO.Response(updatedDept);
	}
	
	@Override
	@Transactional
	public DepartmentDTO.Response delete(Integer id) {
		Department dept = (id != null) ? deptMapper.findById(id) : null;
		if (dept == null) {
			throw new DataNotFoundException("dept.not.found");
		}
		
		if (deptMapper.delete(dept) <= 0) {
			throw new IllegalStateException("common.err.server_error");
		}		
		
        return new DepartmentDTO.Response(dept);
	}
}
