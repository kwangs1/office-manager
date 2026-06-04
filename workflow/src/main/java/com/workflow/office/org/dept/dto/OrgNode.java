package com.workflow.office.org.dept.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class OrgNode {
	private Integer id;
	private String name;
	private	Integer pid;
	private Integer depth;
	private Integer SortOr;
	private List<OrgNode> children = new ArrayList<>();
}
