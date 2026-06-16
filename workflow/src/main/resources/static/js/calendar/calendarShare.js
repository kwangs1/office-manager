const CalendarShare = {
    selectedDepts: [],
	selectedUsers: [],
	includeSubDepts: false,
	
    init: function() {
		$('#includeSubDepts').off('change').on('change', (e) => {
			this.includeSubDepts = e.target.checked;
		});
    },
	
    handleDeptSelection: function(data) {
		const node = data.node;
		const action = data.action;
		const inst = $('#jstree_demo_div').jstree(true);
		
		if (action === 'check_node') {
			this.addDeptToArray(node.id, node.text);
			if (this.includeSubDepts && node.children_d) {
			    node.children_d.forEach(childId => {
			       const childNode = inst.get_node(childId);
			       this.addDeptToArray(childNode.id, childNode.text);
			    });
			}
		} else if (action === 'uncheck_node') {
			this.removeDeptFromArray(node.id);
			if (this.includeSubDepts && node.children_d) {
			    node.children_d.forEach(childId => {
			       	this.removeDeptFromArray(childId);
			    });
			}
		}
		
		this.fetchUserListByDept(node.id);
    },

	fetchUserListByDept: async function(deptId) {
	    const $userContainer = $('#user_list_box');
	    const $ul = $('#user_list_ul');

	    $ul.empty();
	    $userContainer.show();

	    const res = await callApi(`/api/users/${deptId}`, 'GET');
	    if (Array.isArray(res.data)) {
	        res.data.forEach(user => {
	            const isChecked = this.selectedUsers.find(u => u.id === String(user.id)) ? 'checked' : '';
	            
	            const $li = $(`
	                <li class="user-item">
	                    <input type="checkbox" class="user-chk" data-nm="${user.name}" data-id="${user.id}" ${isChecked}/>
	                    <span>${user.name}</span>
	                    <span style="color: #888; font-size: 0.9em;">${user.posName}</span>
	                </li>
	            `);
	            $ul.append($li);
	        });

	        this.updateCheckAllStatus();
	    }
	},
	
	toggleUser: function(id, name, isChecked) {
		if (isChecked) {
			if (!this.selectedUsers.find(u => u.id === id)) {
				this.selectedUsers.push({id, name});
			}
		} else {
			this.selectedUsers = this.selectedUsers.filter(u => u.id !== id);
		}
		this.updateCheckAllStatus();
	},

	updateCheckAllStatus: function() {
		const total = $('.user-chk').length;
		const checked = $('.user-chk:checked').length;
		$('#checkAllUsers').prop('checked', total > 0 && total === checked);
	},

	addDeptToArray: function(id, name) {
	    if (!this.selectedDepts.find(d => d.id === id)) {
	        this.selectedDepts.push({ id, name });
	    }
	},

	removeDeptFromArray: function(id) {
		this.selectedDepts = this.selectedDepts.filter(d => d.id !== id);
	},
	
	sendSelected: function() {
		const $container = $('#dynamicShareList');
		const $noMsg = $('#noShareMsg');

		$container.empty();
		if ($noMsg.length) $noMsg.hide();
		
		this.selectedDepts.forEach(dept => {
		        const $deptLi = $('<li>', {
						        class: 'share-dept-item',
						        css: {
						            marginBottom: '8px', paddingBottom: '8px', borderBottom: '1px dashed #eee',
						            display: 'flex', alignItems: 'center', gap: '10px'
						        }
						     })
							 .attr('data-id', dept.id)
							 .attr('data-type', 'dept');
				$deptLi.html(`
				     <strong style="min-width:80px;">${dept.name}</strong>
				     <label><input type="checkbox" class="dept-read" checked value="read"/> 읽기</label>
				     <label><input type="checkbox" class="dept-write" value="write"/> 작성</label>
				     <label><input type="checkbox" class="dept-modify" value="modify"/> 수정</label>

				     <button type="button" 
				           onclick="this.parentElement.remove()" 
				           style="margin-left: auto; padding: 2px 6px; font-size: 11px; line-height: 1; background: #f1f5f9; color: #64748b; border: 1px solid #e2e8f0; border-radius: 4px; cursor: pointer; transition: all 0.2s;"
				           onmouseover="this.style.background='#fee2e2'; this.style.color='#ef4444'; this.style.borderColor='#fca5a5'"
				           onmouseout="this.style.background='#f1f5f9'; this.style.color='#64748b'; this.style.borderColor='#e2e8f0'">
				       ×
					  </button>
				`);				
		    $container.append($deptLi);
		});
			
		this.selectedUsers.forEach(user => {
			const $li = $('<li>', {
			            class: 'share-user-item',
			            css: {
			                marginBottom: '8px', paddingBottom: '8px', borderBottom: '1px dashed #eee',
			                display: 'flex', alignItems: 'center', gap: '10px'
			            }
			        })
					.attr('data-id', user.id)
					.attr('data-type', 'user');

			$li.html(`
			    <strong style="min-width:80px;">${user.name}</strong>
			    <label><input type="checkbox" class="user-read" checked value="read"/> 읽기</label>
			    <label><input type="checkbox" class="user-write" value="write"/> 작성</label>
			    <label><input type="checkbox" class="user-modify" value="modify"/> 수정</label>

			    <button type="button" 
			            onclick="this.parentElement.remove()" 
			            style="margin-left: auto; padding: 2px 6px; font-size: 11px; line-height: 1; background: #f1f5f9; color: #64748b; border: 1px solid #e2e8f0; border-radius: 4px; cursor: pointer; transition: all 0.2s;"
			            onmouseover="this.style.background='#fee2e2'; this.style.color='#ef4444'; this.style.borderColor='#fca5a5'"
			            onmouseout="this.style.background='#f1f5f9'; this.style.color='#64748b'; this.style.borderColor='#e2e8f0'">
			        ×
			    </button>
			`);

			$container.append($li);
		});
		closeDeptModal();		
	}
	
};